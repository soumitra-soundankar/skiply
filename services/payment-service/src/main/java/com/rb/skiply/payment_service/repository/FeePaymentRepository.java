package com.rb.skiply.payment_service.repository;

import com.rb.skiply.payment_service.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, Integer> {
}
