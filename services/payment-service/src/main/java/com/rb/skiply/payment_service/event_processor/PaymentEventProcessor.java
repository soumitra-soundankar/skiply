package com.rb.skiply.payment_service.event_processor;

import com.rb.skiply.payment_service.entity.Payment;
import com.rb.skiply.payment_service.entity.PaymentStatus;
import com.rb.skiply.payment_service.events.PaymentInitiationEvent;
import com.rb.skiply.payment_service.port.ReceiptClientAdapter;
import com.rb.skiply.payment_service.port.StudentClientAdapter;
import com.rb.skiply.payment_service.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

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
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        log.info("Calling student-service to update payment status");
        log.info("Calling receipt-generator to record receipt");
    }
}
