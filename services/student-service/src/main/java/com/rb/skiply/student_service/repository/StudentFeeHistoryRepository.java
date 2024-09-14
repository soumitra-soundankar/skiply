package com.rb.skiply.student_service.repository;

import com.rb.skiply.student_service.entity.StudentFeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFeeHistoryRepository extends JpaRepository<StudentFeeHistory, Integer> {

    StudentFeeHistory findByStudentId(final String studentId);

    @Query(value = "SELECT e.* FROM student_fee_history e WHERE to_char(academic_year,'YYYY') =:year and student_id = :studentId", nativeQuery = true)
    StudentFeeHistory findByStudentId(final String studentId, final Integer year);

}
