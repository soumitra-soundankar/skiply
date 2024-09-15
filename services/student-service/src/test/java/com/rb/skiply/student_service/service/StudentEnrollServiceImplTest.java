package com.rb.skiply.student_service.service;

import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentEnrollServiceImplTest extends StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentEnrollServiceImpl service;

/*    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this); //without this you will get NPE
    }*/

    @Test
    void enrollStudent() {

        when(studentRepository.save(any())).thenReturn(createStudent());
        Student studentSaved = service.enrollStudent(createStudentRequest());

        assertEquals(studentSaved.getStudentId(), "KG1-019");
        assertEquals(studentSaved.getGrade(), "KG1");

    }

}