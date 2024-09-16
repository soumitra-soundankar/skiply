package com.rb.skiply.student_service.service;

import com.rb.skiply.student_fee.openapi.model.StudentFeeDetails;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentResponse;
import com.rb.skiply.student_service.exception.FeeTypesNotFound;
import com.rb.skiply.student_service.exception.StudentFeeHistoryNotFound;
import com.rb.skiply.student_service.exception.StudentNotFound;
import com.rb.skiply.student_service.mapper.FeeMapper;
import com.rb.skiply.student_service.mapper.StudentFeeDetailsMapper;
import com.rb.skiply.student_service.mapper.StudentFeeHistoryMapper;
import com.rb.skiply.student_service.ext.FeeClientAdapter;
import com.rb.skiply.student_service.ext.PaymentClientAdapter;
import com.rb.skiply.student_service.repository.StudentFeeHistoryRepository;
import com.rb.skiply.student_service.repository.StudentFeeRepository;
import com.rb.skiply.student_service.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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


    private StudentFeeHistoryMapper mapper = new StudentFeeHistoryMapper();

    private FeeMapper feeMapper = new FeeMapper();

    private StudentFeeDetailsMapper studentFeeDetailsMapper = new StudentFeeDetailsMapper();

    @InjectMocks
    private StudentFeeServiceImpl service;

    @BeforeEach
    public void setup(){
        service = new StudentFeeServiceImpl(studentFeeHistoryRepository, studentRepository, studentFeeRepository, feeClientAdapter,paymentClientAdapter,mapper,feeMapper, studentFeeDetailsMapper);
    }

    @Test
    void getStudentFees_Happy_Path() throws StudentNotFound {

        when(studentRepository.findByStudentId(anyString())).thenReturn(createStudent());
        when(studentFeeHistoryRepository.findByStudentId(anyString(), any())).thenReturn(null);
        when(feeClientAdapter.getFeesByGrade(anyString())).thenReturn(createFeeDetails());
        when(studentFeeRepository.saveAll(any())).thenReturn(createStudentFeeList());
        when(studentFeeHistoryRepository.save(any())).thenReturn(createStudentFeeHistory());

        final StudentFeeDetails studentFeeDetails = service.getStudentFees("PLN1249");

        Assertions.assertNotNull(studentFeeDetails);
        Assertions.assertEquals(BigDecimal.valueOf(100), studentFeeDetails.getTotalPendingAmount());
        Assertions.assertEquals(1, studentFeeDetails.getFees().size());
    }

    @Test
    void getStudentFees_Happy_Path_Existing_Pending_Fees() throws StudentNotFound {

        when(studentRepository.findByStudentId(anyString())).thenReturn(createStudent());
        when(studentFeeHistoryRepository.findByStudentId(anyString(), any())).thenReturn(createStudentFeeHistory());

        final StudentFeeDetails studentFeeDetails = service.getStudentFees("PLN1249");

        Assertions.assertNotNull(studentFeeDetails);
        Assertions.assertEquals(BigDecimal.valueOf(100), studentFeeDetails.getTotalPendingAmount());
        Assertions.assertEquals(1, studentFeeDetails.getFees().size());
    }

    @Test
    void getStudentFees_student_not_found() {

        when(studentRepository.findByStudentId(anyString())).thenReturn(null);
        var exception = assertThrows(StudentNotFound.class, () -> service.getStudentFees("PLN1248"));
        Assertions.assertEquals(exception.getMessage(), "Student with id PLN1248 not found.");

    }


    @Test
    void initiatePayment_Happy_Path() throws FeeTypesNotFound, StudentNotFound {
        when(studentRepository.findByStudentId(anyString())).thenReturn(createStudent());
        when(studentFeeHistoryRepository.findByStudentId(anyString(), any())).thenReturn(createStudentFeeHistory());
        when(studentFeeHistoryRepository.save(any())).thenReturn(createStudentFeeHistory());
        StudentFeePaymentResponse studentFeePaymentResponse = service.initiatePayment(STUDENT_ID, createStudentFeePaymentRequest());
        Assertions.assertNotNull(studentFeePaymentResponse);
    }

    @Test
    void initiatePayment_No_matching_Fees() throws FeeTypesNotFound, StudentNotFound {
        when(studentRepository.findByStudentId(anyString())).thenReturn(createStudent());
        when(studentFeeHistoryRepository.findByStudentId(anyString(), any())).thenReturn(createStudentFeeHistory());
        var exception = assertThrows(FeeTypesNotFound.class, () -> service.initiatePayment(STUDENT_ID, createStudentFeePaymentRequestNoMatchingFees()));
        Assertions.assertEquals("No matching feeTypes found from request", exception.getMessage());
    }

    @Test
    void initiatePayment_student_not_found() {
        when(studentRepository.findByStudentId(anyString())).thenReturn(null);
        var exception = assertThrows(StudentNotFound.class, () -> service.initiatePayment("PLN1248", createStudentFeePaymentRequest()));
        Assertions.assertEquals(exception.getMessage(), "Student with id PLN1248 not found.");
    }

    @Test
    void updateFeePaymentStatus_Happy_Path() throws StudentFeeHistoryNotFound {
        when(studentFeeHistoryRepository.findByStudentId(anyString(), any())).thenReturn(createStudentFeeHistoryWithSubmitToHostStatus());
        when(studentFeeHistoryRepository.save(any())).thenReturn(createStudentFeeHistory());
        StudentFeeDetails studentFeeDetails = service.updateFeePaymentStatus(STUDENT_ID, createStudentFeePaymentStatusRequest());
        Assertions.assertNotNull(studentFeeDetails);
    }

    @Test
    void updateFeePaymentStatus_Student_History_Not_Found() {
        when(studentFeeHistoryRepository.findByStudentId(anyString(), any())).thenReturn(null);
        var exception = assertThrows(StudentFeeHistoryNotFound.class, () -> service.updateFeePaymentStatus(STUDENT_ID, createStudentFeePaymentStatusRequest()));
        Assertions.assertEquals("Student History not found for "+ STUDENT_ID, exception.getMessage());
    }
}