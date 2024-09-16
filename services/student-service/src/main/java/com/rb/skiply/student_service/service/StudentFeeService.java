package com.rb.skiply.student_service.service;

import com.rb.skiply.student_fee.openapi.model.StudentFeeDetails;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentRequest;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentResponse;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentStatusRequest;
import com.rb.skiply.student_service.exception.FeeTypesNotFound;
import com.rb.skiply.student_service.exception.StudentFeeHistoryNotFound;
import com.rb.skiply.student_service.exception.StudentNotFound;

public interface StudentFeeService {

    StudentFeeDetails getStudentFees(final String studentId) throws StudentNotFound;

    StudentFeePaymentResponse initiatePayment(final String studentId, final StudentFeePaymentRequest studentFeePaymentRequest) throws StudentNotFound, FeeTypesNotFound;

    StudentFeeDetails updateFeePaymentStatus(final String studentId, final StudentFeePaymentStatusRequest studentFeePaymentStatusRequest) throws StudentFeeHistoryNotFound;

}
