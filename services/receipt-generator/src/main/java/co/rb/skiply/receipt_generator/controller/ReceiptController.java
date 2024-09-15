package co.rb.skiply.receipt_generator.controller;

import co.rb.skiply.receipt_generator.service.ReceiptService;
import com.rb.skiply.receipt_generator.openapi.api.ReceiptrecordApi;

import com.rb.skiply.receipt_generator.openapi.model.FeePaymentResponse;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ReceiptController implements ReceiptrecordApi {

    private final ReceiptService receiptService;

    @Override
    public ResponseEntity<FeePaymentResponse> storeReceiptData(@Valid PaymentReceiptRequest paymentReceiptRequest) {
        return new ResponseEntity<>(receiptService.storeReceipt(paymentReceiptRequest), HttpStatus.OK);
    }
}
