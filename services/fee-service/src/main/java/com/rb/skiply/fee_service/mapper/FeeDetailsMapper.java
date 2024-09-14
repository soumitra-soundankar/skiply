package com.rb.skiply.fee_service.mapper;

import com.rb.skiply.fee_service.entity.Fee;
import com.rb.skiply.fee_service.openapi.model.FeeDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeeDetailsMapper {

    public FeeDetails toFeeDetails(List<Fee> feeList) {
        FeeDetails feeDetails = new FeeDetails();
        feeList.forEach(fee -> feeDetails.addDataItem(toApiFee(fee)));
        return feeDetails;
    }

    private com.rb.skiply.fee_service.openapi.model.Fee toApiFee(Fee fee) {
        com.rb.skiply.fee_service.openapi.model.Fee apiFee = new com.rb.skiply.fee_service.openapi.model.Fee();
        apiFee.feeAmount(fee.getFeeAmount());
        apiFee.feeType(fee.getFeeType());
        return apiFee;
    }
}
