package com.rb.skiply.student_service.port;

import com.rb.skiply.fee_service.openapi.model.FeeDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@AllArgsConstructor
public class FeeClientAdapter {

    private final RestClient restClient;

    private static final String FEE_SERVICE_BASE_URL="http://localhost:8081/fee/";

    public FeeDetails getFeesByGrade(final String grade) {
        return restClient.get()
                        .uri(FEE_SERVICE_BASE_URL + grade)
                        .retrieve()
                        .body(FeeDetails.class);

    }

}
