package co.rb.skiply.receipt_generator.mapper;

import co.rb.skiply.receipt_generator.entity.Receipt;
import co.rb.skiply.receipt_generator.entity.ReceiptFee;
import co.rb.skiply.receipt_generator.exception.ReceiptNotFoundException;
import com.rb.skiply.receipt_generator.openapi.model.FeePayment;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentReceiptResponseMapper {

    public PaymentReceiptResponse toPaymentReceiptResponse(final Receipt receipt) throws ReceiptNotFoundException {

        if(receipt == null)
            throw new ReceiptNotFoundException("Receipt not found");

        return new PaymentReceiptResponse()
                .paymentReference(receipt.getPaymentReference())
                .cardNumber(receipt.getCardNumber())
                .studentId(receipt.getStudentId())
                .cardType(receipt.getCardType())
                .totalAmount(receipt.getTotalAmount())
                .feeDetails(toFeePayment(receipt.getFees()))
                .paymentDateTime(receipt.getPaymentDateTime());
    }

    private List<FeePayment> toFeePayment(List<ReceiptFee> fees) {
        return fees.stream().map( receiptFee -> new FeePayment()
                        .feeAmount(receiptFee.getFeeAmount())
                        .feeType(receiptFee.getFeeType()))
                .toList();
    }
}
