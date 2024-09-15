package com.rb.skiply.payment_service.service;

import com.rb.skiply.payment_service.openapi.model.StudentFeePaymentRequest;
import com.rb.skiply.payment_service.openapi.model.StudentFeePaymentResponse;

public interface PaymentService {

    StudentFeePaymentResponse registerPayment(final StudentFeePaymentRequest paymentRequest);

    StudentFeePaymentResponse getPaymentStatus(final String paymentReference);
}
