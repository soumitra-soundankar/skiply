package com.rb.skiply.student_service.service;

import com.rb.skiply.student_fee.openapi.model.StudentFeeDetails;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentRequest;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentResponse;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentStatusRequest;
import com.rb.skiply.student_service.exception.FeeTypesNotFoundException;
import com.rb.skiply.student_service.exception.StudentFeeHistoryNotFoundException;
import com.rb.skiply.student_service.exception.StudentNotFoundException;

public interface StudentFeeService {

    StudentFeeDetails getStudentFees(final String studentId) throws StudentNotFoundException;

    StudentFeePaymentResponse initiatePayment(final String studentId, final StudentFeePaymentRequest studentFeePaymentRequest) throws StudentNotFoundException, FeeTypesNotFoundException;

    StudentFeeDetails updateFeePaymentStatus(final String studentId, final StudentFeePaymentStatusRequest studentFeePaymentStatusRequest) throws StudentFeeHistoryNotFoundException;

}
