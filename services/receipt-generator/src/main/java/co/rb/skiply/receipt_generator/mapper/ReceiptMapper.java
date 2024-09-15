package co.rb.skiply.receipt_generator.mapper;

import co.rb.skiply.receipt_generator.entity.Receipt;
import co.rb.skiply.receipt_generator.entity.ReceiptFee;
import com.rb.skiply.receipt_generator.openapi.model.FeePayment;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReceiptMapper {

    public Receipt toReceipt(PaymentReceiptRequest receiptRequest) {
        return Receipt.builder()
                .receiptNumber(RandomStringUtils.randomAlphabetic(8))
                .studentId(receiptRequest.getStudentId())
                .cardNumber(receiptRequest.getCardNumber())
                .paymentReference(receiptRequest.getPaymentReference())
                .cardType(receiptRequest.getCardType())
                .totalAmount(receiptRequest.getTotalAmount())
                .fees(convertToReceiptFee(receiptRequest.getFeeDetails()))
                .paymentDateTime(receiptRequest.getPaymentDateTime())
                .build();
    }

    private List<ReceiptFee> convertToReceiptFee(List<FeePayment> feeDetails) {
        return feeDetails.stream()
                .map(feePayment -> ReceiptFee.builder()
                        .feeAmount(feePayment.getFeeAmount())
                        .feeType(feePayment.getFeeType())
                        .build())
                .toList();
    }

}
