package com.rb.skiply.student_service.service;

import com.rb.skiply.student_fee.openapi.model.StudentFeeReceipt;
import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.port.ReceiptClientAdapter;
import com.rb.skiply.student_service.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentFeeReceiptServiceImpl implements  StudentFeeReceiptService{

    private final StudentRepository studentRepository;

    private final ReceiptClientAdapter receiptClientAdapter;
    @Override
    public StudentFeeReceipt getStudentReceiptByStudentIdAndPaymentRef(String studentId, String paymentReference) {
        final StudentFeeReceipt studentFeeReceipt = receiptClientAdapter.getReceiptByPaymentRefernce(paymentReference);
        final Student student = studentRepository.findByStudentId(studentId);
        studentFeeReceipt.setStudentName(student.getStudentName());
        return studentFeeReceipt;
    }


}
