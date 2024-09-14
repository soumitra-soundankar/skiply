package com.rb.skiply.fee_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Fee {

    @Id
    private Integer id;

    private String feeType;

    private BigDecimal feeAmount;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

}
