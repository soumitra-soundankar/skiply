package com.rb.skiply.student_service.entity;

public enum FeePaymentStatus {

    PENDING("Pending"),
    INITIATED("Initiated"),
    SUBMITTED_TO_HOST("SubmittedToHost"),
    WAITING_FROM_HOST("WaitingFromHost"),
    SUCCESS("Success"),
    FAILED("Failed");

    FeePaymentStatus(String status) {
    }
}
