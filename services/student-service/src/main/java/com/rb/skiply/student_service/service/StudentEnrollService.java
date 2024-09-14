package com.rb.skiply.student_service.service;

import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.openapi.model.StudentRequest;


public interface StudentEnrollService {

    Student enrollStudent(final StudentRequest studentRequest);

    Student updateStudent(final StudentRequest studentRequest);

}
