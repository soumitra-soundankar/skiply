package com.rb.skiply.student_service.repository;

import com.rb.skiply.student_service.entity.StudentFeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFeeHistoryRepository extends JpaRepository<StudentFeeHistory, Integer> {

    StudentFeeHistory findByStudentId(final String studentId);

}
