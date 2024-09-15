package co.rb.skiply.receipt_generator.controller;

import co.rb.skiply.receipt_generator.service.ReceiptService;
import com.rb.skiply.receipt_generator.openapi.api.ReceiptApi;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ReceiptGenerationController implements ReceiptApi {

    private final ReceiptService receiptService;

    @Override
    public ResponseEntity<PaymentReceiptResponse> getReceipt(final String paymentReference) {
        return new ResponseEntity<>(receiptService.getReceiptByPaymentReference(paymentReference), HttpStatus.OK);
    }
}
