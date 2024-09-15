package com.rb.skiply.fee_service.controller;


import com.rb.skiply.fee_service.openapi.api.FeeApi;
import com.rb.skiply.fee_service.openapi.model.FeeDetails;
import com.rb.skiply.fee_service.service.FeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FeeController implements FeeApi {

    private final FeeService feeService;

    @Override
    public ResponseEntity<FeeDetails> getFeesByGrade(final String grade) {
        return ResponseEntity.ok(
                feeService.feesByGrade(grade));
    }
}
