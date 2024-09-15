package co.rb.skiply.receipt_generator.mapper;

import co.rb.skiply.receipt_generator.entity.Receipt;
import com.rb.skiply.receipt_generator.openapi.model.FeePaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class FeePaymentResponseMapper {

    public FeePaymentResponse toFeePaymentResponse(final Receipt receipt) {
        return new FeePaymentResponse()
                .receiptNumber(receipt.getReceiptNumber())
                .studentId(receipt.getStudentId());
    }

}
