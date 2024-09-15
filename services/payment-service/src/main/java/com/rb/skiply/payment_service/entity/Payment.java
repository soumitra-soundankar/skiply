package com.rb.skiply.payment_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String paymentReference;

    private String studentId;

    private BigDecimal amount;

    private String cardNumber;

    private String cardType;

    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<FeePayment> paymentList;

}
