package co.rb.skiply.receipt_generator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ReceiptFee {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String feeType;

    private BigDecimal feeAmount;

    @ManyToOne
    private Receipt receipt;
}
