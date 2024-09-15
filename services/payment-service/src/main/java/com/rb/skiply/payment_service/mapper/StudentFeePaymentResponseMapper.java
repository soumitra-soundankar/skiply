package com.rb.skiply.payment_service.mapper;

import com.rb.skiply.payment_service.entity.Payment;
import com.rb.skiply.payment_service.openapi.model.StudentFeePaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class StudentFeePaymentResponseMapper {

    public StudentFeePaymentResponse toStudentFeePaymentResponse(final Payment payment) {
        return new StudentFeePaymentResponse()
                .paymentReference(payment.getPaymentReference())
                .studentId(payment.getStudentId())
                .paymentStatus(payment.getFeePaymentStatus().getValue());
    }

}
