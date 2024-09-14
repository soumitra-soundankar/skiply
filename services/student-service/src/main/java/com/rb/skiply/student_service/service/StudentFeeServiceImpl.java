package com.rb.skiply.student_service.service;

import com.rb.skiply.fee_service.openapi.model.FeeDetails;
import com.rb.skiply.student_fee.openapi.model.StudentFeeDetails;
import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.entity.StudentFee;
import com.rb.skiply.student_service.entity.StudentFeeHistory;
import com.rb.skiply.student_service.mapper.FeeMapper;
import com.rb.skiply.student_service.mapper.StudentFeeHistoryMapper;
import com.rb.skiply.student_service.port.FeeClientAdapter;
import com.rb.skiply.student_service.repository.StudentFeeHistoryRepository;
import com.rb.skiply.student_service.repository.StudentFeeRepository;
import com.rb.skiply.student_service.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentFeeServiceImpl implements  StudentFeeService{

    private final StudentFeeHistoryRepository studentFeeHistoryRepository;

    private final StudentRepository studentRepository;

    private final StudentFeeRepository studentFeeRepository;

    private final FeeClientAdapter feeClientAdapter;

    private final StudentFeeHistoryMapper mapper;

    private final FeeMapper feeMapper;


    @Override
    public StudentFeeDetails getStudentFees(final String studentId) {
        final StudentFeeDetails studentFeeDetails = new StudentFeeDetails();
        final StudentFeeHistory studentFeeHistory = new StudentFeeHistory();
        final Optional<Student> student = Optional.of(studentRepository.findByStudentId(studentId));
        final StudentFeeHistory feeHistory = studentFeeHistoryRepository.findByStudentId(studentId);

        if(feeHistory != null) {
            prepareStudentFeeDetails(studentFeeDetails, student.get(), feeHistory);
            return studentFeeDetails;
        }

        final FeeDetails feeDetails = feeClientAdapter.getFeesByGrade(student.get().getGrade());
        List<StudentFee> studentFeeList = studentFeeRepository.saveAll(mapper.toStudentFees(feeDetails.getData()));
        studentFeeHistory.setFees(studentFeeList);
        studentFeeHistory.setStudentId(studentId);
        final StudentFeeHistory studentFeeHistorySaved = studentFeeHistoryRepository.save(studentFeeHistory);
        prepareStudentFeeDetails(studentFeeDetails, student.get(), studentFeeHistorySaved);
        return studentFeeDetails;
    }


    private void prepareStudentFeeDetails(final StudentFeeDetails studentFeeDetails, final Student student, final StudentFeeHistory studentFeeHistorySaved) {
        studentFeeDetails.setStudentName(student.getStudentName());
        studentFeeDetails.setStudentId(student.getStudentId());
        studentFeeDetails.setFees(feeMapper.toStudentFeeDetails(studentFeeHistorySaved.getFees()));
        studentFeeDetails.setTotalPendingAmount(calculateTotalPendingAmount(studentFeeHistorySaved.getFees()));
    }

    private BigDecimal calculateTotalPendingAmount(List<StudentFee> fees) {
        return fees.stream().map(StudentFee::getFeeAmount).reduce(BigDecimal::add).get();
    }
}
