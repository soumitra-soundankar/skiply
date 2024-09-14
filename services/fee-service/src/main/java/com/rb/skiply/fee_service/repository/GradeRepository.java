package com.rb.skiply.fee_service.repository;

import com.rb.skiply.fee_service.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {

    Grade findByGradeName(final String gradeName);
}
