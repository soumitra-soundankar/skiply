package com.rb.skiply.student_service.service;

import com.rb.skiply.fee_service.openapi.model.FeeDetails;
import com.rb.skiply.student_fee.openapi.model.FeePayment;
import com.rb.skiply.student_fee.openapi.model.StudentFeeDetails;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentRequest;
import com.rb.skiply.student_service.entity.FeePaymentStatus;
import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.entity.StudentFee;
import com.rb.skiply.student_service.entity.StudentFeeHistory;
import com.rb.skiply.student_service.exception.FeeTypesNotFound;
import com.rb.skiply.student_service.exception.StudentNotFound;
import com.rb.skiply.student_service.mapper.FeeMapper;
import com.rb.skiply.student_service.mapper.StudentFeeHistoryMapper;
import com.rb.skiply.student_service.port.FeeClientAdapter;
import com.rb.skiply.student_service.port.PaymentClientAdapter;
import com.rb.skiply.student_service.repository.StudentFeeHistoryRepository;
import com.rb.skiply.student_service.repository.StudentFeeRepository;
import com.rb.skiply.student_service.repository.StudentRepository;
import jakarta.validation.constraints.NotNull;
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


    @Override
    public StudentFeeDetails getStudentFees(final String studentId) throws StudentNotFound {
        final StudentFeeDetails studentFeeDetails = new StudentFeeDetails();
        final StudentFeeHistory studentFeeHistory = new StudentFeeHistory();
        final Student student = studentRepository.findByStudentId(studentId);

        if(student == null) {
            throw new StudentNotFound("Student with id %s not found"); //TODO: User StringUtils.format
        }

        final StudentFeeHistory feeHistory = studentFeeHistoryRepository.findByStudentId(studentId, LocalDate.now().getYear());

        if(feeHistory == null) {
            log.info("Student {} fee record not found", studentId);
        }

        if(feeHistory != null) {
            prepareStudentFeeDetails(studentFeeDetails, student, feeHistory);
            return studentFeeDetails;
        }

        final FeeDetails feeDetails = feeClientAdapter.getFeesByGrade(student.getGrade());
        List<StudentFee> studentFeeList = studentFeeRepository.saveAll(mapper.toStudentFees(feeDetails.getData()));
        studentFeeHistory.setFees(studentFeeList);
        studentFeeHistory.setStudentId(studentId);
        studentFeeHistory.setAcademicYear(LocalDate.now());
        final StudentFeeHistory studentFeeHistorySaved = studentFeeHistoryRepository.save(studentFeeHistory);
        prepareStudentFeeDetails(studentFeeDetails, student, studentFeeHistorySaved);
        return studentFeeDetails;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public StudentFeeDetails initiatePayment(@NotNull  String studentId, @NotNull StudentFeePaymentRequest studentFeePaymentRequest) throws StudentNotFound, FeeTypesNotFound {
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
            prepareStudentFeeDetails(studentFeeDetails, student, studentFeeHistoryPaymentInitiated);

            paymentClientAdapter.registerPayment(studentFeePaymentRequest);
            final StudentFeeHistory studentFeeHistoryPaymentSubmitted = updateStudentFeeHistory(studentFeeHistory, FeePaymentStatus.SUBMITTED_TO_HOST, applicableStudentFees, savedStudentFee -> () -> studentFeePaymentRequest.getFeeDetails().stream()
                    .filter(
                            feePayment -> (savedStudentFee.getFeeType().equals(feePayment.getFeeType())))
                    .findFirst().get().getFeeAmount());
            prepareStudentFeeDetails(studentFeeDetails, student, studentFeeHistoryPaymentSubmitted);
        }
        catch(Exception e) {
            final StudentFeeHistory studentFeeHistoryPaymentPending = updateStudentFeeHistory(studentFeeHistory, FeePaymentStatus.PENDING, applicableStudentFees, savedStudentFee -> () -> BigDecimal.ZERO);
            prepareStudentFeeDetails(studentFeeDetails, student, studentFeeHistoryPaymentPending);
        }

        return studentFeeDetails;
    }

    private StudentFeeHistory updateStudentFeeHistory(StudentFeeHistory studentFeeHistory, FeePaymentStatus status, List<StudentFee> applicableStudentFees, Function<StudentFee, Supplier<BigDecimal>> AmountFunction) {
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

    private static void updatePaymentStatus(StudentFee studentFee, FeePaymentStatus paymentStatus, Supplier<BigDecimal> AmountToPaid) {
        studentFee.setFeePaymentStatus(paymentStatus);
        studentFee.setAmountPaid(AmountToPaid.get());
    }


    private void prepareStudentFeeDetails(final StudentFeeDetails studentFeeDetails, final Student student, final StudentFeeHistory studentFeeHistorySaved) {
        List<StudentFee> studentFees = studentFeeHistorySaved.getFees();
        studentFees = studentFees.stream().filter(
                studentFee -> studentFee.getAmountPaid() == null || studentFee.getFeeAmount().compareTo(studentFee.getAmountPaid()) != 0 )
                .toList();

        studentFeeDetails.setStudentName(student.getStudentName());
        studentFeeDetails.setStudentId(student.getStudentId());
        studentFeeDetails.setFees(feeMapper.toStudentFeeDetails(studentFees));
        studentFeeDetails.setTotalPendingAmount(calculateTotalPendingAmount(studentFeeHistorySaved.getFees()));
    }

    private BigDecimal calculateTotalPendingAmount(List<StudentFee> fees) {
        return fees.stream().map(StudentFee::getFeeAmount).reduce(BigDecimal::add).get();
    }
}
