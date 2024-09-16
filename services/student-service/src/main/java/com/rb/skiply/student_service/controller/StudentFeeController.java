package com.rb.skiply.student_service.controller;

import com.rb.skiply.student_fee.openapi.api.StudentApi;
import com.rb.skiply.student_fee.openapi.model.*;
import com.rb.skiply.student_service.exception.FeeTypesNotFoundException;
import com.rb.skiply.student_service.exception.StudentFeeHistoryNotFoundException;
import com.rb.skiply.student_service.exception.StudentNotFoundException;
import com.rb.skiply.student_service.service.StudentFeeReceiptService;
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

    private final StudentFeeReceiptService studentFeeReceiptService;
    @Override
    public ResponseEntity<Void> feePaymentStatus(String studentId, StudentFeePaymentStatusRequest studentFeePaymentStatusRequest) {

        try {
            studentFeeService.updateFeePaymentStatus(studentId, studentFeePaymentStatusRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (StudentFeeHistoryNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<StudentFeeDetails> getAllFeesByStudentIdForTheYear(String studentId) {
        //TODO: return fees with paymentReference
        return null;
    }

    @Override
    public ResponseEntity<StudentFeeDetails> getFeePaymentStatus(String studentId, String paymentReference) {
        //TODO: return fees payment by paymentReference
        return null;
    }

    @Override
    public ResponseEntity<StudentFeeReceipt> getFeeReceiptByStudentId(String studentId, String paymentReference) {
        try {
            return new ResponseEntity<>(studentFeeReceiptService.getStudentReceiptByStudentIdAndPaymentRef(
                    studentId,
                    paymentReference),
                    HttpStatus.OK);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ResponseEntity<StudentFeeDetails> getPendingFeesByStudentId(String studentId) {
        try {
            return ResponseEntity.ok(studentFeeService.getStudentFees(studentId));
        } catch (StudentNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<StudentFeePaymentResponse> initiateFeePayment(String studentId, StudentFeePaymentRequest studentFeePaymentRequest) {
        try {
            return ResponseEntity.ok(studentFeeService.initiatePayment(studentId, studentFeePaymentRequest));
        } catch (StudentNotFoundException | FeeTypesNotFoundException e) {
            log.error(e.getMessage());
           return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
