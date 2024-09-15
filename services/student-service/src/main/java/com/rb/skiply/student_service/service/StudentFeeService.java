package com.rb.skiply.student_service.service;

import com.rb.skiply.student_fee.openapi.model.StudentFeeDetails;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentRequest;
import com.rb.skiply.student_service.exception.FeeTypesNotFound;
import com.rb.skiply.student_service.exception.StudentNotFound;

public interface StudentFeeService {

    StudentFeeDetails getStudentFees(final String studentId) throws StudentNotFound;

    StudentFeeDetails initiatePayment(final String studentId, final StudentFeePaymentRequest studentFeePaymentRequest) throws StudentNotFound, FeeTypesNotFound;
}
