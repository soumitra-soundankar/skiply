package com.rb.skiply.payment_service.mapper;

import com.rb.skiply.payment_service.entity.FeePayment;
import com.rb.skiply.payment_service.entity.Payment;
import com.rb.skiply.payment_service.entity.FeePaymentStatus;
import com.rb.skiply.payment_service.openapi.model.StudentFeePaymentRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Component
public class PaymentMapper {

    public Payment initialPaymentRequestConverter(final StudentFeePaymentRequest paymentRequest) {

        return Payment.builder()
                .paymentReference(RandomStringUtils.randomAlphabetic(10))
                .amount(calculateTotalAmount(paymentRequest.getFeeDetails()))
                .cardType(paymentRequest.getCardType())
                .cardNumber(paymentRequest.getCardNumber())
                .feePaymentStatus(FeePaymentStatus.SUBMITTED_TO_HOST)
                .studentId(paymentRequest.getStudentId())
                .paymentList(createFeePaymentList(paymentRequest.getFeeDetails()))
                .paymentDateTime(OffsetDateTime.now())
                .build();
    }

    private BigDecimal calculateTotalAmount(List<com.rb.skiply.payment_service.openapi.model.FeePayment> feeDetails) {
        return feeDetails.stream().map(com.rb.skiply.payment_service.openapi.model.FeePayment::getFeeAmount).reduce(BigDecimal::add).get();
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
