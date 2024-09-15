package com.rb.skiply.payment_service.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentStatus {

    SUBMITTED_TO_HOST("SendToHost"),
    WAITING_FOR_HOST("WithHost"),
    SUCCESS("Success"),
    Failed("Failed");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getValue() {
        return status;
    }

    @Override
    public String toString() {
        return String.valueOf(status);
    }

}
