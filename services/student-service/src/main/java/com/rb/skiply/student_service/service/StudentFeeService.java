package com.rb.skiply.student_service.service;

import com.rb.skiply.student_fee.openapi.model.StudentFeeDetails;

public interface StudentFeeService {

    StudentFeeDetails getStudentFees(final String studentId);

}
