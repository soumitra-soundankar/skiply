package com.rb.skiply.student_service.ext;

import com.rb.skiply.payment_service.openapi.model.StudentFeePaymentResponse;
import com.rb.skiply.student_service.exception.PaymentNotRegisteredException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
@AllArgsConstructor
public class PaymentClientAdapter {

    private final RestClient paymentRestClient;

    private static final String FEE_SERVICE_BASE_URL="http://localhost:8082/payment";

    public StudentFeePaymentResponse registerPayment(final com.rb.skiply.payment_service.openapi.model.StudentFeePaymentRequest studentFeePaymentRequest) throws Exception {
        log.info("Calling payment-service");
        ResponseEntity<StudentFeePaymentResponse> feePaymentResponseResponseEntity =  paymentRestClient.post()
                .uri(FEE_SERVICE_BASE_URL)
                .body(studentFeePaymentRequest)
                .retrieve()
                .toEntity(StudentFeePaymentResponse.class);

        if(!feePaymentResponseResponseEntity.getStatusCode().equals(HttpStatus.OK))
            throw new PaymentNotRegisteredException("Failed to register the payment with payment-service");

        log.info("Payment successfully submitted to payment-service");
        return  feePaymentResponseResponseEntity.getBody();
    }
}
