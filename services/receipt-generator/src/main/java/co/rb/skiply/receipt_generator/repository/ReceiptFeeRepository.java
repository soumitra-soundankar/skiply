package co.rb.skiply.receipt_generator.repository;

import co.rb.skiply.receipt_generator.entity.ReceiptFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptFeeRepository extends JpaRepository<ReceiptFee, Integer> {
}
