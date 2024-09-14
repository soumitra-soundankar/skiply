package com.rb.skiply.fee_service.repository;

import com.rb.skiply.fee_service.entity.Fee;
import com.rb.skiply.fee_service.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Integer> {

    List<Fee> findByGrade(final Grade grade);
}
