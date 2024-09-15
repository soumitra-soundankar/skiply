package com.rb.skiply.student_service.mapper;

import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.openapi.model.StudentResponse;
import org.springframework.stereotype.Component;

@Component
public class StudentResponseMapper {

    public StudentResponse toStudentResponse(Student student) {

        return new StudentResponse().studentId(student.getStudentId())
                .studentName(student.getStudentName())
                .grade(student.getGrade());
    }
}
