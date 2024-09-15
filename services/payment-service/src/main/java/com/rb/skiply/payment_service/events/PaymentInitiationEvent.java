package com.rb.skiply.payment_service.events;

import com.rb.skiply.payment_service.entity.Payment;

public record PaymentInitiationEvent(Payment payment) {
}
