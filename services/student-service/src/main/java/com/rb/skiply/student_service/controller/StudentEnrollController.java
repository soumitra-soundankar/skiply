package com.rb.skiply.student_service.controller;

import com.rb.skiply.student_service.openapi.api.StudentApi;
import com.rb.skiply.student_service.openapi.model.StudentRequest;
import com.rb.skiply.student_service.service.StudentEnrollService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StudentEnrollController implements StudentApi {

    private final StudentEnrollService studentEnrollService;
    @Override
    public ResponseEntity<Void> enrollStudent(StudentRequest studentRequest) {
        studentEnrollService.enrollStudent(studentRequest);
        return ResponseEntity.ok().build();
    }
}
