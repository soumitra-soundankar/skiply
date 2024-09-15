package com.rb.skiply.fee_service.service;

import com.rb.skiply.fee_service.entity.Grade;
import com.rb.skiply.fee_service.repository.GradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Grade findGradeByName(String grade) {
        return gradeRepository.findByGradeName(grade);
    }
}
