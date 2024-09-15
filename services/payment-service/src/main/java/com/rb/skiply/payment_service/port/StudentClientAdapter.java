package com.rb.skiply.payment_service.port;

import com.rb.skiply.payment_service.exception.CommunicationException;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentStatusRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@AllArgsConstructor
public class StudentClientAdapter {

    private final RestClient restClient;

    private static String STUDENT_SERVICE_BASE_URL = "http://localhost:8081/student/";

    public void updateStatusToStudentService(final String studentId, final StudentFeePaymentStatusRequest studentFeePaymentStatusRequest ) throws CommunicationException {

        ResponseEntity<Void> feePaymentResponseResponseEntity = restClient.put()
                .uri(STUDENT_SERVICE_BASE_URL+ studentId + "/fee:payment-status")
                .body(studentFeePaymentStatusRequest)
                .retrieve()
                .toEntity(Void.class);

        if (!feePaymentResponseResponseEntity.getStatusCode().equals(HttpStatus.OK))
            throw new CommunicationException("Unable to send data to Receipt Generator");
    }
}
