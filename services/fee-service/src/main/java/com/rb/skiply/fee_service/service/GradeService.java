package com.rb.skiply.fee_service.service;

import com.rb.skiply.fee_service.entity.Grade;

public interface GradeService {

    Grade findGradeByName(final String grade);

}
