package com.rb.skiply.fee_service.controller;


import com.rb.skiply.fee_service.openapi.api.FeeApi;
import com.rb.skiply.fee_service.openapi.model.FeeDetails;
import com.rb.skiply.fee_service.service.FeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class FeeController implements FeeApi {

    private final FeeService feeService;

    @Override
    public ResponseEntity<FeeDetails> getFeesByGrade(final String grade) {
        try {
            return new ResponseEntity<>(
                    feeService.feesByGrade(grade), HttpStatus.OK);
        } catch(Exception e) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
