package com.rb.skiply.fee_service.service;

import com.rb.skiply.fee_service.entity.Fee;
import com.rb.skiply.fee_service.entity.Grade;
import com.rb.skiply.fee_service.mapper.FeeDetailsMapper;
import com.rb.skiply.fee_service.openapi.model.FeeDetails;
import com.rb.skiply.fee_service.repository.FeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FeeServiceImpl implements FeeService {

    private final GradeService gradeService;

    private final FeeRepository feeRepository;

    private final FeeDetailsMapper feeDetailsMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,readOnly = true)
    public FeeDetails feesByGrade(String gradeName) {
        final Grade grade = gradeService.findGradeByName(gradeName);
        List<Fee> feeList = feeRepository.findByGrade(grade);
        return feeDetailsMapper.toFeeDetails(feeList);
    }
}
