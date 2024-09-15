package com.rb.skiply.student_service.mapper;

import com.rb.skiply.fee_service.openapi.model.Fee;
import com.rb.skiply.student_service.entity.FeePaymentStatus;
import com.rb.skiply.student_service.entity.StudentFee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentFeeHistoryMapper {


    public List<StudentFee> toStudentFees(List<Fee> fees) {

        return fees.stream()
                .map(fee -> {
                    StudentFee studentFee = new StudentFee();
                    studentFee.setFeeAmount(fee.getFeeAmount());
                    studentFee.setFeeType(fee.getFeeType());
                    studentFee.setFeePaymentStatus(FeePaymentStatus.PENDING);
                    return studentFee;
                })
                .toList();
    }

}
