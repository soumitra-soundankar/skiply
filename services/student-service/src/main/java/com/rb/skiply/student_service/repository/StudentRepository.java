package com.rb.skiply.student_service.repository;

import com.rb.skiply.student_service.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByStudentId(final String studentId);

}
