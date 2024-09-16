package com.rb.skiply.student_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentFee {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String feeType;

    private BigDecimal feeAmount;

    private BigDecimal amountPaid;

    @Enumerated(EnumType.STRING)
    private FeePaymentStatus feePaymentStatus;

    private String paymentReference;

}
