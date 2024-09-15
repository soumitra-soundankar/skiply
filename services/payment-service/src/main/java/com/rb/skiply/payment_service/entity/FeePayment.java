package com.rb.skiply.payment_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class FeePayment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String feeType;

    private BigDecimal feeAmount;

    @ManyToOne
    private Payment payment;
}
