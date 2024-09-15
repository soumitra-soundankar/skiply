package com.rb.skiply.student_service.entity;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;

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

    public FeePaymentStatus getStatus(String value) {
        return FeePaymentStatus.valueOf(value);
    }

    @Override
    public String toString() {
        return String.valueOf(status);
    }

    public static Optional<FeePaymentStatus> fromText(String text) {
        return Arrays.stream(values())
                .filter(status -> status.status.equalsIgnoreCase(text))
                .findFirst();
    }
}
