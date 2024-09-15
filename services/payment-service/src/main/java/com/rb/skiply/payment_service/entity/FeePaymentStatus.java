package com.rb.skiply.payment_service.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FeePaymentStatus {

    PENDING("Pending"),
    INITIATED("Initiated"),
    SUBMITTED_TO_HOST("SubmittedToHost"),
    WAITING_FROM_HOST("WaitingFromHost"),
    SUCCESS("Success"),
    FAILED("Failed");

    private final String status;

    FeePaymentStatus(String status) {
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
