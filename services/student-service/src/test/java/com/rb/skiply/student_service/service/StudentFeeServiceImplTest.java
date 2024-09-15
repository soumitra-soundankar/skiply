package com.rb.skiply.student_service.service;

import com.rb.skiply.student_service.entity.StudentFeeHistory;
import com.rb.skiply.student_service.mapper.FeeMapper;
import com.rb.skiply.student_service.mapper.StudentFeeDetailsMapper;
import com.rb.skiply.student_service.mapper.StudentFeeHistoryMapper;
import com.rb.skiply.student_service.port.FeeClientAdapter;
import com.rb.skiply.student_service.port.PaymentClientAdapter;
import com.rb.skiply.student_service.repository.StudentFeeHistoryRepository;
import com.rb.skiply.student_service.repository.StudentFeeRepository;
import com.rb.skiply.student_service.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class StudentFeeServiceImplTest extends StudentServiceTest {

    @Mock
    private StudentFeeHistoryRepository studentFeeHistoryRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentFeeRepository studentFeeRepository;

    @Mock
    private FeeClientAdapter feeClientAdapter;

    @Mock
    private PaymentClientAdapter paymentClientAdapter;

    @Mock
    private StudentFeeHistoryMapper mapper;

    @Mock
    private FeeMapper feeMapper;

    @Mock
    private StudentFeeDetailsMapper studentFeeDetailsMapper;

    @InjectMocks
    private StudentFeeServiceImpl service;

    @Test
    void getStudentFees() {

        when(studentRepository.findByStudentId(anyString())).thenReturn(createStudent());
        when(studentFeeHistoryRepository.findByStudentId(anyString())).thenReturn(createStudentFeeHistory());
    }

    private StudentFeeHistory createStudentFeeHistory() {

        return StudentFeeHistory.builder()
                .studentId("KG1-019")
                .academicYear(LocalDate.now())
                .id(1)
                .build();
    }

    @Test
    void initiatePayment() {
    }

    @Test
    void updateFeePaymentStatus() {
    }
}