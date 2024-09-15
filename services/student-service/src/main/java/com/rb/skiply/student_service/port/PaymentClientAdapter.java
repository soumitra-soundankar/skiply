package com.rb.skiply.student_service.port;

import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PaymentClientAdapter {

    private RestClient paymentRestClient;

    private static final String FEE_SERVICE_BASE_URL="http://localhost:8082/payment/";

    public void registerPayment(final StudentFeePaymentRequest studentFeePaymentRequest) throws Exception {
        throw new Exception("Payment service not integrated");
    }
}
