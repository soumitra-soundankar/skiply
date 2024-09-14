package com.rb.skiply.student_service.service;

import com.rb.skiply.fee_service.openapi.model.FeeDetails;
import com.rb.skiply.student_fee.openapi.model.StudentFeeDetails;
import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.entity.StudentFee;
import com.rb.skiply.student_service.entity.StudentFeeHistory;
import com.rb.skiply.student_service.mapper.FeeMapper;
import com.rb.skiply.student_service.mapper.StudentFeeHistoryMapper;
import com.rb.skiply.student_service.port.FeeClientAdapter;
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

    private final StudentFeeRepository studentFeeRepository;

    private final StudentRepository studentRepository;

    private final FeeClientAdapter feeClientAdapter;

    private final StudentFeeHistoryMapper mapper;

    private final FeeMapper feeMapper;


    @Override
    public StudentFeeDetails getStudentFees(final String studentId) {
        final StudentFeeDetails studentFeeDetails = new StudentFeeDetails();
        final Optional<Student> student = Optional.of(studentRepository.findByStudentId(studentId));
        final Optional<StudentFeeHistory> feeHistory = Optional.of(studentFeeRepository.findByStudentId(studentId));

        if(feeHistory.isPresent()) {
            final StudentFeeHistory studentFeeHistory = feeHistory.get();
            prepareStudentFeeDetails(studentFeeDetails, student.get(), studentFeeHistory);
            return studentFeeDetails;
        }

        final FeeDetails feeDetails = feeClientAdapter.getFeesByGrade(student.get().getGrade());
        final StudentFeeHistory studentFeeHistory = mapper.toStudentFeeHistory(feeDetails.getData());
        studentFeeHistory.setStudentId(studentId);
        final StudentFeeHistory studentFeeHistorySaved = studentFeeRepository.save(studentFeeHistory);
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
