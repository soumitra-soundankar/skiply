package com.rb.skiply.payment_service.port;

import com.rb.skiply.payment_service.exception.CommunicationException;
import com.rb.skiply.receipt_generator.openapi.model.FeePaymentResponse;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@AllArgsConstructor
public class ReceiptClientAdapter {

    private final RestClient restClient;

    private static String RECEIPT_GENERATOR_BASE_URL = "http://localhost:8083/receipt";
    public FeePaymentResponse publishDataToReceiptGenerator(final PaymentReceiptRequest paymentReceiptRequest ) throws CommunicationException {

        ResponseEntity<FeePaymentResponse> feePaymentResponseResponseEntity =  restClient.post()
                .uri(RECEIPT_GENERATOR_BASE_URL + ":record")
                .body(paymentReceiptRequest)
                .retrieve()
                .toEntity(FeePaymentResponse.class);

        if(feePaymentResponseResponseEntity.getStatusCode().equals(HttpStatus.OK))
            return feePaymentResponseResponseEntity.getBody();

        throw new CommunicationException("Unable to send data to Receipt Generator");
    }
}
