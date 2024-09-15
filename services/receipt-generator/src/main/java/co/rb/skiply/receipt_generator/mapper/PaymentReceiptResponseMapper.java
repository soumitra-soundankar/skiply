package co.rb.skiply.receipt_generator.mapper;

import co.rb.skiply.receipt_generator.entity.Receipt;
import co.rb.skiply.receipt_generator.entity.ReceiptFee;
import com.rb.skiply.receipt_generator.openapi.model.FeePayment;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentReceiptResponseMapper {

    public PaymentReceiptResponse toPaymentReceiptResponse(final Receipt receipt) {
        return new PaymentReceiptResponse()
                .paymentReference(receipt.getPaymentReference())
                .cardNumber(receipt.getCardNumber())
                .studentId(receipt.getStudentId())
                .cardType(receipt.getCardType())
                .totalAmount(receipt.getTotalAmount())
                .feeDetails(toFeePayment(receipt.getFees()));
    }

    private List<FeePayment> toFeePayment(List<ReceiptFee> fees) {
        return fees.stream().map( receiptFee -> new FeePayment()
                        .feeAmount(receiptFee.getFeeAmount())
                        .feeType(receiptFee.getFeeType()))
                .toList();
    }
}
