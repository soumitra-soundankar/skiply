package com.rb.skiply.student_service.repository;

import com.rb.skiply.student_service.entity.StudentFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFeeRepository extends JpaRepository<StudentFee, Integer> {



}
