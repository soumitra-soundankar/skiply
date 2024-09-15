package com.rb.skiply.student_service.controller;

import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.mapper.StudentResponseMapper;
import com.rb.skiply.student_service.openapi.api.StudentApi;
import com.rb.skiply.student_service.openapi.model.StudentRequest;
import com.rb.skiply.student_service.openapi.model.StudentResponse;
import com.rb.skiply.student_service.service.StudentEnrollService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class StudentEnrollController implements StudentApi {

    private final StudentEnrollService studentEnrollService;

    private final StudentResponseMapper studentResponseMapper;

    @Override
    public ResponseEntity<StudentResponse> enrollStudent(StudentRequest studentRequest) {
        Optional<Student> student = Optional.ofNullable(studentEnrollService.enrollStudent(studentRequest));
        return student.map(studentSaved -> new ResponseEntity<>(studentResponseMapper.toStudentResponse(studentSaved), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
