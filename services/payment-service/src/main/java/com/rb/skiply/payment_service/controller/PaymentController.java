package com.rb.skiply.payment_service.controller;


import com.rb.skiply.payment_service.openapi.api.PaymentApi;
import com.rb.skiply.payment_service.openapi.model.StudentFeePaymentRequest;
import com.rb.skiply.payment_service.openapi.model.StudentFeePaymentResponse;
import com.rb.skiply.payment_service.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PaymentController implements PaymentApi {

    private final PaymentService paymentService;

    @Override
    public ResponseEntity<StudentFeePaymentResponse> getPaymentStatus(final String paymentReference) {
        try{
            return new ResponseEntity<>(paymentService.getPaymentStatus(paymentReference) ,HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<StudentFeePaymentResponse> registerPayment(final StudentFeePaymentRequest studentFeePaymentRequest) {
        try {
            return new ResponseEntity<>(paymentService.registerPayment(studentFeePaymentRequest), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
