package com.rb.skiply.payment_service.service;

import com.rb.skiply.payment_service.entity.FeePayment;
import com.rb.skiply.payment_service.entity.Payment;
import com.rb.skiply.payment_service.events.PaymentInitiationEvent;
import com.rb.skiply.payment_service.mapper.PaymentMapper;
import com.rb.skiply.payment_service.mapper.StudentFeePaymentResponseMapper;
import com.rb.skiply.payment_service.openapi.model.StudentFeePaymentRequest;
import com.rb.skiply.payment_service.openapi.model.StudentFeePaymentResponse;
import com.rb.skiply.payment_service.repository.FeePaymentRepository;
import com.rb.skiply.payment_service.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;

    private final PaymentRepository paymentRepository;

    private final FeePaymentRepository feePaymentRepository;

    private final StudentFeePaymentResponseMapper studentFeePaymentResponseMapper;

    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public StudentFeePaymentResponse registerPayment(final StudentFeePaymentRequest paymentRequest) {
        Payment payment = paymentMapper.initialPaymentRequestConverter(paymentRequest);
        Payment savedPayment = paymentRepository.save(payment);
        List<FeePayment> feePaymentList = payment.getPaymentList();
        feePaymentList.forEach(feePayment -> feePayment.setPayment(payment));
        feePaymentRepository.saveAll(feePaymentList);
        publisher.publishEvent(new PaymentInitiationEvent(payment));
        return studentFeePaymentResponseMapper.toStudentFeePaymentResponse(savedPayment);
    }

    @Override
    public StudentFeePaymentResponse getPaymentStatus(String paymentReference) {
        return null;
    }
}
