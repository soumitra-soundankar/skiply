package com.rb.skiply.student_service.service;

import com.rb.skiply.student_fee.openapi.model.StudentFeeDetails;
import com.rb.skiply.student_service.exception.StudentNotFound;

public interface StudentFeeService {

    StudentFeeDetails getStudentFees(final String studentId) throws StudentNotFound;

}
