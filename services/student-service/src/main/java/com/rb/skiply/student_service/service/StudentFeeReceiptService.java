package com.rb.skiply.student_service.service;

import com.rb.skiply.student_fee.openapi.model.StudentFeeReceipt;

public interface StudentFeeReceiptService {

    StudentFeeReceipt getStudentReceiptByStudentIdAndPaymentRef(final String studentId, final String paymentReference);
}
