package com.rb.skiply.student_service.service;

import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.openapi.model.StudentRequest;

public abstract class StudentServiceTest {

    public static Student createStudent() {

        return Student.builder()
                .id(1)
                .studentId("KG1-019")
                .studentName("SS")
                .grade("KG1")
                .mobileNumber("1234567")
                .schoolName("TJC-Highschool")
                .build();
    }

    public static StudentRequest createStudentRequest() {
        return new StudentRequest()
                .studentId("KG1-019")
                .studentName("SS")
                .grade(StudentRequest.GradeEnum.KG1)
                .mobileNumber("1234567")
                .schoolName("TJC-Highschool");
    }


}
