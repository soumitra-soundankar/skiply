package com.rb.skiply.student_service.controller;

import com.rb.skiply.student_fee.openapi.api.StudentApi;
import com.rb.skiply.student_fee.openapi.model.StudentFeeDetails;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentRequest;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentStatusRequest;
import com.rb.skiply.student_service.exception.FeeTypesNotFound;
import com.rb.skiply.student_service.exception.StudentNotFound;
import com.rb.skiply.student_service.service.StudentFeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class StudentFeeController implements StudentApi {

    private final StudentFeeService studentFeeService;

    @Override
    public ResponseEntity<Void> feePaymentStatus(String studentId, StudentFeePaymentStatusRequest studentFeePaymentStatusRequest) {
        return null;
    }

    @Override
    public ResponseEntity<StudentFeeDetails> getFeesByStudentId(final String studentId) {
        try {
            return ResponseEntity.ok(studentFeeService.getStudentFees(studentId));
        } catch (StudentNotFound e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<StudentFeeDetails> initiateFeePayment(String studentId, StudentFeePaymentRequest studentFeePaymentRequest) {
        try {
            return ResponseEntity.ok(studentFeeService.initiatePayment(studentId, studentFeePaymentRequest));
        } catch (StudentNotFound | FeeTypesNotFound e) {
           return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
