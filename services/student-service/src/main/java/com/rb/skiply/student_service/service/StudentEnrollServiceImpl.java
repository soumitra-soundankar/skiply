package com.rb.skiply.student_service.service;

import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.openapi.model.StudentRequest;
import com.rb.skiply.student_service.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentEnrollServiceImpl implements StudentEnrollService {

    private final StudentRepository studentRepository;

    @Override
    public Student enrollStudent(StudentRequest studentRequest) {
        final Student student = convertStudentRequest(studentRequest);
        final Student studentSaved = studentRepository.save(student);
        return studentSaved;
    }

    private Student convertStudentRequest(StudentRequest studentRequest) {
        return Student.builder()
                .studentId(studentRequest.getStudentId())
                .grade(studentRequest.getGrade().getValue())
                .mobileNumber(studentRequest.getMobileNumber())
                .studentName(studentRequest.getStudentName())
                .schoolName(studentRequest.getSchoolName())
                .build();
    }


    @Override
    public Student updateStudent(StudentRequest studentRequest) {
        return null;
    }
}
