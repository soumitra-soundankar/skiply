package com.rb.skiply.fee_service.service;

import com.rb.skiply.fee_service.entity.Grade;
import com.rb.skiply.fee_service.repository.GradeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Grade findGradeByName(String grade) {
        return gradeRepository.findByGradeName(grade);
    }
}
