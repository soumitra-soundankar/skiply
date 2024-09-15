package com.rb.skiply.payment_service.repository;

import com.rb.skiply.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findByPaymentReference(final String paymentReference);

}
