package com.rb.skiply.student_service.mapper;

import com.rb.skiply.fee_service.openapi.model.Fee;
import com.rb.skiply.student_service.entity.StudentFee;
import com.rb.skiply.student_service.entity.StudentFeeHistory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentFeeHistoryMapper {

    public StudentFeeHistory toStudentFeeHistory(List<Fee> fees) {
        StudentFeeHistory studentFeeHistory = new StudentFeeHistory();
        studentFeeHistory.setFees(toStudentFees(fees));
        return studentFeeHistory;
    }


    private List<StudentFee> toStudentFees(List<Fee> fees) {

        return fees.stream()
                .map(fee -> {
                    StudentFee studentFee = new StudentFee();
                    studentFee.setFeeAmount(fee.getFeeAmount());
                    studentFee.setFeeType(fee.getFeeType());
                    return studentFee;
                })
                .toList();
    }

}
