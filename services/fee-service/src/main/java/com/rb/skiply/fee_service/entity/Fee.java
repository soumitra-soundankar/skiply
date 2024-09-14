package com.rb.skiply.fee_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fee {

    @Id
    private Integer id;

    private String feeType;

    private BigDecimal feeAmount;

    @ManyToMany
    @JoinTable(
            name = "fee_grade",
            joinColumns = @JoinColumn(name = "fee_id"),
            inverseJoinColumns = @JoinColumn(name = "grade_id"))
    private List<Grade> grade;

}
