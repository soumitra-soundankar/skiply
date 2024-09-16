package com.rb.skiply.payment_service.event_processor;

import com.rb.skiply.payment_service.entity.Payment;
import com.rb.skiply.payment_service.entity.FeePaymentStatus;
import com.rb.skiply.payment_service.events.PaymentInitiationEvent;
import com.rb.skiply.payment_service.exception.CommunicationException;
import com.rb.skiply.payment_service.ext.ReceiptClientAdapter;
import com.rb.skiply.payment_service.ext.StudentClientAdapter;
import com.rb.skiply.payment_service.repository.PaymentRepository;
import com.rb.skiply.receipt_generator.openapi.model.FeePayment;
import com.rb.skiply.receipt_generator.openapi.model.PaymentReceiptRequest;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentStatusRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PaymentEventProcessor {

    private final StudentClientAdapter studentClientAdapter;

    private final ReceiptClientAdapter receiptClientAdapter;

    private final PaymentRepository paymentRepository;


    @ApplicationModuleListener
    void on(PaymentInitiationEvent paymentInitiationEvent) throws InterruptedException {
        log.info(paymentInitiationEvent.payment().toString());
        log.info("Calling payment provider");
        Thread.sleep(5_000);
        log.info("Submitting payment to payment provider");
        Thread.sleep(5_000);
        log.info("Confirming payment status");
        Thread.sleep(10_000);
        Payment payment = paymentInitiationEvent.payment();
        payment.setFeePaymentStatus(FeePaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        try {
            log.info("Calling student-service to update payment status");
            studentClientAdapter.updateStatusToStudentService(payment.getStudentId(), createStudentPaymentStatusRequest(payment));
            log.info("Calling receipt-generator to record receipt");
            receiptClientAdapter.publishDataToReceiptGenerator(createPaymentReceipt(payment));
        } catch (CommunicationException e) {
            throw new RuntimeException(e);
        }
    }

    private StudentFeePaymentStatusRequest createStudentPaymentStatusRequest(Payment payment) {
        return new StudentFeePaymentStatusRequest()
                .paymentReference(payment.getPaymentReference())
                .status(payment.getFeePaymentStatus().getValue());
    }

    private PaymentReceiptRequest createPaymentReceipt(Payment payment) {
        return new PaymentReceiptRequest()
                .paymentReference(payment.getPaymentReference())
                .cardNumber(payment.getCardNumber())
                .cardType(payment.getCardType())
                .studentId(payment.getStudentId())
                .totalAmount(payment.getAmount())
                .paymentDateTime(payment.getPaymentDateTime())
                .feeDetails(createPaymentFee(payment.getPaymentList()));
    }

    private List<FeePayment> createPaymentFee(List<com.rb.skiply.payment_service.entity.FeePayment> paymentList) {
        return paymentList.stream()
                .map(feePayment -> new FeePayment().feeType(feePayment.getFeeType()).feeAmount(feePayment.getFeeAmount()))
                .toList();
    }
}
