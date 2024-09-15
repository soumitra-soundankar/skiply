package co.rb.skiply.receipt_generator.service;

import co.rb.skiply.receipt_generator.exception.ReceiptNotFoundException;
import com.rb.skiply.receipt_generator.openapi.model.FeePaymentResponse;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptRequest;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptResponse;

public interface ReceiptService {

    PaymentReceiptResponse getReceiptByPaymentReference(final String paymentReference) throws ReceiptNotFoundException;

    FeePaymentResponse storeReceipt(final PaymentReceiptRequest paymentReceiptRequest);
}
