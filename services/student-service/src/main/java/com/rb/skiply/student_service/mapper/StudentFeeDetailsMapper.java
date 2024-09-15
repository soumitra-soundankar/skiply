package com.rb.skiply.student_service.mapper;

import com.rb.skiply.student_fee.openapi.model.StudentFeeDetails;
import com.rb.skiply.student_service.entity.StudentFeeHistory;
import org.springframework.stereotype.Component;

@Component
public class StudentFeeDetailsMapper {

    public StudentFeeDetails toStudentFeeDetails(final StudentFeeHistory studentFeeHistory) {

        return new StudentFeeDetails()
                .studentId(studentFeeHistory.getStudentId());
    }

}
