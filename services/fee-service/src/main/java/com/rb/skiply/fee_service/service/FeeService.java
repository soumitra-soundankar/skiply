package com.rb.skiply.fee_service.service;

import com.rb.skiply.fee_service.openapi.model.FeeDetails;

public interface FeeService {

    FeeDetails feesByGrade(final String grade);

}
