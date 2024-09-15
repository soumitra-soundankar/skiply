package com.rb.skiply.student_service.port;

import com.rb.skiply.student_fee.openapi.model.StudentFeeReceipt;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@AllArgsConstructor
@Slf4j
public class ReceiptClientAdapter {

    private final RestClient restClient;

    private static final String RECEIPT_GENERATOR_BASE_URL="http://localhost:8083/receipt/";


    public StudentFeeReceipt getReceiptByPaymentRefernce(final String paymentReference) {
        return restClient.get()
                .uri(RECEIPT_GENERATOR_BASE_URL + paymentReference)
                .retrieve()
                .body(StudentFeeReceipt.class);

    }

}
