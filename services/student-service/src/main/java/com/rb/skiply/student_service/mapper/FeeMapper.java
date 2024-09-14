package com.rb.skiply.student_service.mapper;

import com.rb.skiply.student_fee.openapi.model.Fee;
import com.rb.skiply.student_service.entity.StudentFee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeeMapper {

    public List<Fee> toStudentFeeDetails(final List<StudentFee> feeList) {
        return feeList.stream()
                .map(fee -> new Fee().feeType(fee.getFeeType()).feeAmount(fee.getFeeAmount()))
                .toList();
    }
}
