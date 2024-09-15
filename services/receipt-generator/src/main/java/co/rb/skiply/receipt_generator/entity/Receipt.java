package co.rb.skiply.receipt_generator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Receipt {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String receiptNumber;

    private String studentId;

    private String paymentReference;

    private String cardNumber;

    private String cardType;

    private BigDecimal totalAmount;

    private OffsetDateTime paymentDateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receipt")
    private List<ReceiptFee> fees;

}
