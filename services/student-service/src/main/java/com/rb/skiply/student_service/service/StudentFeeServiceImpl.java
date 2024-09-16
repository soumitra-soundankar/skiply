package com.rb.skiply.student_service.service;

import com.rb.skiply.fee_service.openapi.model.FeeDetails;
import com.rb.skiply.student_fee.openapi.model.*;
import com.rb.skiply.student_service.entity.FeePaymentStatus;
import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.entity.StudentFee;
import com.rb.skiply.student_service.entity.StudentFeeHistory;
import com.rb.skiply.student_service.exception.FeeTypesNotFound;
import com.rb.skiply.student_service.exception.StudentNotFound;
import com.rb.skiply.student_service.mapper.FeeMapper;
import com.rb.skiply.student_service.mapper.StudentFeeDetailsMapper;
import com.rb.skiply.student_service.mapper.StudentFeeHistoryMapper;
import com.rb.skiply.student_service.port.FeeClientAdapter;
import com.rb.skiply.student_service.port.PaymentClientAdapter;
import com.rb.skiply.student_service.repository.StudentFeeHistoryRepository;
import com.rb.skiply.student_service.repository.StudentFeeRepository;
import com.rb.skiply.student_service.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class StudentFeeServiceImpl implements  StudentFeeService {

    private final StudentFeeHistoryRepository  studentFeeHistoryRepository;

    private final StudentRepository studentRepository;

    private final StudentFeeRepository studentFeeRepository;

    private final FeeClientAdapter feeClientAdapter;

    private final PaymentClientAdapter paymentClientAdapter;

    private final StudentFeeHistoryMapper mapper;

    private final FeeMapper feeMapper;

    private final StudentFeeDetailsMapper studentFeeDetailsMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public StudentFeeDetails getStudentFees(final String studentId) throws StudentNotFound {
        final StudentFeeDetails studentFeeDetails = new StudentFeeDetails();
        final StudentFeeHistory studentFeeHistory = new StudentFeeHistory();
        final Student student = studentRepository.findByStudentId(studentId);

        if(student == null) {
            throw new StudentNotFound(String.format("Student with id %s not found.", studentId));
        }

        final StudentFeeHistory feeHistory = studentFeeHistoryRepository.findByStudentId(studentId, LocalDate.now().getYear());

        if(feeHistory == null) {
            log.info("Student {} fee record not found, may be first time on the platform.", studentId);
        }

        if(feeHistory != null) {
            pendingStudentFeeDetails(studentFeeDetails, student, feeHistory);
            return studentFeeDetails;
        }

        final FeeDetails feeDetails = feeClientAdapter.getFeesByGrade(student.getGrade());
        List<StudentFee> studentFeeList = studentFeeRepository.saveAll(mapper.toStudentFees(feeDetails.getData()));
        studentFeeHistory.setFees(studentFeeList);
        studentFeeHistory.setStudentId(studentId);
        studentFeeHistory.setAcademicYear(LocalDate.now());
        final StudentFeeHistory studentFeeHistorySaved = studentFeeHistoryRepository.save(studentFeeHistory);
        pendingStudentFeeDetails(studentFeeDetails, student, studentFeeHistorySaved);
        return studentFeeDetails;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public StudentFeePaymentResponse initiatePayment(final String studentId, final StudentFeePaymentRequest studentFeePaymentRequest) throws StudentNotFound, FeeTypesNotFound {
        final Student student = studentRepository.findByStudentId(studentId);
        final StudentFeeDetails studentFeeDetails = new StudentFeeDetails();

        if(student == null) {
            throw new StudentNotFound("Student ID not found");
        }
        final StudentFeeHistory studentFeeHistory = studentFeeHistoryRepository.findByStudentId(studentId, LocalDate.now().getYear());

        Set<String> requestFeeTypes =
                studentFeePaymentRequest.getFeeDetails().stream()
                        .map(FeePayment::getFeeType)
                        .collect(Collectors.toSet());

        List<StudentFee> applicableStudentFees = studentFeeHistory.getFees()
                .stream()
                .filter(studentFee -> requestFeeTypes.contains(studentFee.getFeeType()))
                .toList();

        if(applicableStudentFees.isEmpty()) {
            throw new FeeTypesNotFound("No matching feeTypes found from request");
        }

        try {
            final StudentFeeHistory studentFeeHistoryPaymentInitiated = updateStudentFeeHistory(studentFeeHistory, FeePaymentStatus.INITIATED, applicableStudentFees, savedStudentFee -> () -> studentFeePaymentRequest.getFeeDetails().stream()
                    .filter(
                            feePayment -> (savedStudentFee.getFeeType().equals(feePayment.getFeeType())))
                    .findFirst().get().getFeeAmount());
            pendingStudentFeeDetails(studentFeeDetails, student, studentFeeHistoryPaymentInitiated);

            final StudentFeePaymentResponse studentFeePaymentResponse = convertPaymentServicePaymentResponse(paymentClientAdapter.registerPayment(createRegisterPaymentRequest(student, studentFeePaymentRequest)));
            final StudentFeeHistory studentFeeHistoryPaymentSubmitted = updateStudentFeeHistory(studentFeeHistory, FeePaymentStatus.fromText(studentFeePaymentResponse.getPaymentStatus()).get(), applicableStudentFees, savedStudentFee -> () -> studentFeePaymentRequest.getFeeDetails().stream()
                    .filter(
                            feePayment -> (savedStudentFee.getFeeType().equals(feePayment.getFeeType())))
                    .findFirst().get().getFeeAmount());
            pendingStudentFeeDetails(studentFeeDetails, student, studentFeeHistoryPaymentSubmitted);

            return studentFeePaymentResponse;
        }
        catch(Exception e) {
            final StudentFeeHistory studentFeeHistoryPaymentPending = updateStudentFeeHistory(studentFeeHistory, FeePaymentStatus.PENDING, applicableStudentFees, savedStudentFee -> () -> BigDecimal.ZERO);
            pendingStudentFeeDetails(studentFeeDetails, student, studentFeeHistoryPaymentPending);
        }

        return new StudentFeePaymentResponse();
    }

    private StudentFeePaymentResponse convertPaymentServicePaymentResponse(final com.rb.skiply.payment_service.openapi.model.StudentFeePaymentResponse studentFeePaymentResponse) {
        return new StudentFeePaymentResponse()
                .paymentReference(studentFeePaymentResponse.getPaymentReference())
                .studentId(studentFeePaymentResponse.getStudentId())
                .paymentStatus(studentFeePaymentResponse.getPaymentStatus());
    }

    private com.rb.skiply.payment_service.openapi.model.StudentFeePaymentRequest createRegisterPaymentRequest(final Student student, final StudentFeePaymentRequest studentFeePaymentRequest) {
        return new com.rb.skiply.payment_service.openapi.model.StudentFeePaymentRequest()
                .studentId(student.getStudentId())
                .cardNumber(studentFeePaymentRequest.getCardNumber())
                .cardType(studentFeePaymentRequest.getCardType())
                .totalAmount(studentFeePaymentRequest.getTotalAmount())
                .feeDetails(createFeePayment(studentFeePaymentRequest.getFeeDetails()));
    }

    private List<com.rb.skiply.payment_service.openapi.model.FeePayment> createFeePayment(final List<FeePayment> feeDetails) {
        return feeDetails.stream()
                .map(feePayment -> new com.rb.skiply.payment_service.openapi.model.FeePayment()
                        .feeAmount(feePayment.getFeeAmount())
                        .feeType(feePayment.getFeeType()))
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public StudentFeeDetails updateFeePaymentStatus(final String studentId, final StudentFeePaymentStatusRequest studentFeePaymentStatusRequest) {

        final StudentFeeHistory feeHistory = studentFeeHistoryRepository.findByStudentId(studentId, LocalDate.now().getYear());

        feeHistory.getFees().forEach(
                studentFee -> {
                    if(FeePaymentStatus.SUBMITTED_TO_HOST.compareTo(studentFee.getFeePaymentStatus()) == 0) {
                        studentFee.setFeePaymentStatus( FeePaymentStatus.fromText(studentFeePaymentStatusRequest.getStatus()).get());
                        studentFee.setPaymentReference(studentFeePaymentStatusRequest.getPaymentReference());
                    }
                }
        );
        final StudentFeeHistory feeHistoryUpdated = studentFeeHistoryRepository.save(feeHistory);

        return studentFeeDetailsMapper.toStudentFeeDetails(feeHistoryUpdated);
    }

    private StudentFeeHistory updateStudentFeeHistory(final StudentFeeHistory studentFeeHistory, final FeePaymentStatus status, final List<StudentFee> applicableStudentFees, Function<StudentFee, Supplier<BigDecimal>> AmountFunction) {
        studentFeeHistory.getFees()
                .forEach(studentFee -> {
                    if(applicableStudentFees.contains(studentFee))
                    {
                        updatePaymentStatus(studentFee, status, AmountFunction.apply(studentFee));
                    }
                });

        final StudentFeeHistory studentFeeHistorySaved = studentFeeHistoryRepository.save(studentFeeHistory);
        return studentFeeHistorySaved;
    }

    private static void updatePaymentStatus(final StudentFee studentFee, final FeePaymentStatus paymentStatus, Supplier<BigDecimal> AmountToPaid) {
        studentFee.setFeePaymentStatus(paymentStatus);
        studentFee.setAmountPaid(AmountToPaid.get());
    }


    private void pendingStudentFeeDetails(final StudentFeeDetails studentFeeDetails, final Student student, final StudentFeeHistory studentFeeHistorySaved) {
        List<StudentFee> studentFees = studentFeeHistorySaved.getFees();
        studentFees = studentFees.stream().filter(
                studentFee -> studentFee.getAmountPaid() == null || studentFee.getFeeAmount().compareTo(studentFee.getAmountPaid()) != 0 )
                .toList();

        studentFeeDetails.setStudentName(student.getStudentName());
        studentFeeDetails.setStudentId(student.getStudentId());
        studentFeeDetails.setFees(feeMapper.toStudentFeeDetails(studentFees));
        studentFeeDetails.setTotalPendingAmount(calculateTotalPendingAmount(studentFeeHistorySaved.getFees()));
    }

    private BigDecimal calculateTotalPendingAmount(final List<StudentFee> fees) {
        return fees.stream().map(StudentFee::getFeeAmount).reduce(BigDecimal::add).get();
    }
}
