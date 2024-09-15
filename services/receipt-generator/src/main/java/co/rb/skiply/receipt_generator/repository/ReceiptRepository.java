package co.rb.skiply.receipt_generator.repository;

import co.rb.skiply.receipt_generator.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

    Receipt findByPaymentReference(final String paymentReference);

}
