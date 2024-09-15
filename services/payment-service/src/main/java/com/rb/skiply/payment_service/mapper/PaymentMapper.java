package com.rb.skiply.payment_service.mapper;

import com.rb.skiply.payment_service.entity.FeePayment;
import com.rb.skiply.payment_service.entity.Payment;
import com.rb.skiply.payment_service.entity.PaymentStatus;
import com.rb.skiply.payment_service.openapi.model.StudentFeePaymentRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentMapper {

    public Payment initialPaymentRequestConverter(final StudentFeePaymentRequest paymentRequest) {

        return Payment.builder()
                .paymentReference(RandomStringUtils.randomAlphabetic(10))
                .amount(paymentRequest.getTotalAmount())
                .cardType(paymentRequest.getCardType())
                .cardNumber(paymentRequest.getCardNumber())
                .paymentStatus(PaymentStatus.SUBMITTED_TO_HOST)
                .studentId(paymentRequest.getStudentId())
                .paymentList(createFeePaymentList(paymentRequest.getFeeDetails()))
                .build();
    }

    private List<FeePayment> createFeePaymentList(List<com.rb.skiply.payment_service.openapi.model.FeePayment> feeDetails) {
        return
                feeDetails.stream()
                        .map(feePayment -> FeePayment.builder()
                                .feeAmount(feePayment.getFeeAmount())
                                .feeType(feePayment.getFeeType())
                                .build())
                        .toList();
    }

}
