package com.rb.skiply.student_service.controller;

import com.rb.skiply.student_fee.openapi.api.StudentApi;
import com.rb.skiply.student_fee.openapi.model.StudentFeeDetails;
import com.rb.skiply.student_service.exception.StudentNotFound;
import com.rb.skiply.student_service.service.StudentFeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StudentFeeController implements StudentApi {

    private final StudentFeeService studentFeeService;

    @Override
    public ResponseEntity<StudentFeeDetails> getFeesByStudentId(final String studentId) {
        try {
            return ResponseEntity.ok(studentFeeService.getStudentFees(studentId));
        } catch (StudentNotFound e) {
            throw new RuntimeException(e);
        }
    }
}
