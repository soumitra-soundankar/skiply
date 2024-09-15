package co.rb.skiply.receipt_generator.service;

import co.rb.skiply.receipt_generator.entity.Receipt;
import co.rb.skiply.receipt_generator.entity.ReceiptFee;
import co.rb.skiply.receipt_generator.exception.ReceiptNotFoundException;
import co.rb.skiply.receipt_generator.mapper.FeePaymentResponseMapper;
import co.rb.skiply.receipt_generator.mapper.PaymentReceiptResponseMapper;
import co.rb.skiply.receipt_generator.mapper.ReceiptMapper;
import co.rb.skiply.receipt_generator.repository.ReceiptFeeRepository;
import co.rb.skiply.receipt_generator.repository.ReceiptRepository;
import com.rb.skiply.receipt_generator.openapi.model.FeePaymentResponse;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptRequest;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    private final ReceiptFeeRepository receiptFeeRepository;

    private final ReceiptMapper receiptMapper;

    private final FeePaymentResponseMapper feePaymentResponseMapper;

    private PaymentReceiptResponseMapper paymentReceiptResponseMapper;

    @Override
    public PaymentReceiptResponse getReceiptByPaymentReference(final String paymentReference) throws ReceiptNotFoundException {
        Receipt receipt = receiptRepository.findByPaymentReference(paymentReference);
        return paymentReceiptResponseMapper.toPaymentReceiptResponse(receipt);
    }

    @Override
    public FeePaymentResponse storeReceipt(final PaymentReceiptRequest paymentReceiptRequest) {
        Receipt receipt = receiptMapper.toReceipt(paymentReceiptRequest);
        Receipt receiptSaved = receiptRepository.save(receipt);
        List<ReceiptFee> receiptFeeList = receipt.getFees();
        receiptFeeList.forEach(receiptFee -> receiptFee.setReceipt(receiptSaved));
        receiptFeeRepository.saveAll(receiptFeeList);
        return feePaymentResponseMapper.toFeePaymentResponse(receipt);
    }
}
