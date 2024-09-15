package co.rb.skiply.receipt_generator.service;

import com.rb.skiply.receipt_generator.openapi.model.FeePaymentResponse;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptRequest;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptResponse;

public interface ReceiptService {

    PaymentReceiptResponse getReceiptByPaymentReference(final String paymentReference);

    FeePaymentResponse storeReceipt(final PaymentReceiptRequest paymentReceiptRequest);
}
