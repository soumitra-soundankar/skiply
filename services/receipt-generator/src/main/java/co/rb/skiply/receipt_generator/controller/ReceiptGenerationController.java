package co.rb.skiply.receipt_generator.controller;

import co.rb.skiply.receipt_generator.exception.ReceiptNotFoundException;
import co.rb.skiply.receipt_generator.service.ReceiptService;
import com.rb.skiply.receipt_generator.openapi.api.ReceiptApi;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ReceiptGenerationController implements ReceiptApi {

    private final ReceiptService receiptService;

    @Override
    public ResponseEntity<PaymentReceiptResponse> getReceipt(final String paymentReference) {
        try {
            return new ResponseEntity<>(receiptService.getReceiptByPaymentReference(paymentReference), HttpStatus.OK);
        } catch (ReceiptNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
