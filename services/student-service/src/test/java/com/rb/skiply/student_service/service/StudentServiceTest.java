package com.rb.skiply.student_service.service;

import com.rb.skiply.fee_service.openapi.model.Fee;
import com.rb.skiply.fee_service.openapi.model.FeeDetails;
import com.rb.skiply.student_fee.openapi.model.FeePayment;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentRequest;
import com.rb.skiply.student_fee.openapi.model.StudentFeePaymentStatusRequest;
import com.rb.skiply.student_service.entity.FeePaymentStatus;
import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.entity.StudentFee;
import com.rb.skiply.student_service.entity.StudentFeeHistory;
import com.rb.skiply.student_service.openapi.model.StudentRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class StudentServiceTest {

    public static final BigDecimal TOTAL_AMOUNT = BigDecimal.valueOf(100);
    public static final String STUDENT_ID = "KG1-019";
    public static final String FEE_TYPE = "Tuition";

    public static Student createStudent() {

        return Student.builder()
                .id(1)
                .studentId(STUDENT_ID)
                .studentName("SS")
                .grade("KG1")
                .mobileNumber("1234567")
                .schoolName("TJC-Highschool")
                .build();
    }

    public static StudentRequest createStudentRequest() {
        return new StudentRequest()
                .studentId(STUDENT_ID)
                .studentName("SS")
                .grade(StudentRequest.GradeEnum.KG1)
                .mobileNumber("1234567")
                .schoolName("TJC-Highschool");
    }

    public static FeeDetails createFeeDetails() {
        return new FeeDetails().addDataItem(new Fee().feeAmount(TOTAL_AMOUNT).feeType(FEE_TYPE))
                .addDataItem(new Fee().feeAmount(BigDecimal.valueOf(20)).feeType("Uniform"));
    }

    public static List<StudentFee> createStudentFeeList() {

        List<StudentFee> studentFeeList = new ArrayList<>();
        studentFeeList.add(StudentFee.builder().feeType(FEE_TYPE).feeAmount(TOTAL_AMOUNT).feePaymentStatus(FeePaymentStatus.PENDING).build());
        return studentFeeList;
    }

    public static StudentFeeHistory createStudentFeeHistoryWithSubmitToHostStatus() {
        return StudentFeeHistory.builder()
                .id(1)
                .studentId(STUDENT_ID)
                .fees(createStudentFeeListWithSubmitToHostStatus())
                .build();
    }

    public static List<StudentFee> createStudentFeeListWithSubmitToHostStatus() {

        List<StudentFee> studentFeeList = new ArrayList<>();
        studentFeeList.add(StudentFee.builder().feeType(FEE_TYPE).feeAmount(TOTAL_AMOUNT).feePaymentStatus(FeePaymentStatus.SUBMITTED_TO_HOST).build());
        return studentFeeList;
    }

    public static StudentFeeHistory createStudentFeeHistory() {
        return StudentFeeHistory.builder()
                .id(1)
                .studentId(STUDENT_ID)
                .fees(createStudentFeeList())
                .build();
    }

    public static StudentFeePaymentRequest createStudentFeePaymentRequest(){
        return new StudentFeePaymentRequest().feeDetails(createFeePayment())
                .cardNumber("1234-1234-1234-1234")
                .totalAmount(TOTAL_AMOUNT)
                .cardType("Mastercard");
    }

    private static List<FeePayment> createFeePayment() {

        List<FeePayment> feePayments = new ArrayList<>();
        feePayments.add(new FeePayment().feeType(FEE_TYPE).feeAmount(TOTAL_AMOUNT).customAmount(TOTAL_AMOUNT));
        return feePayments;
    }

    public static StudentFeePaymentRequest createStudentFeePaymentRequestNoMatchingFees(){
        return new StudentFeePaymentRequest().feeDetails(createFeePaymentNoMatchingFees())
                .cardNumber("1234-1234-1234-1234")
                .totalAmount(TOTAL_AMOUNT)
                .cardType("Mastercard");
    }

    private static List<FeePayment> createFeePaymentNoMatchingFees() {

        List<FeePayment> feePayments = new ArrayList<>();
        feePayments.add(new FeePayment().feeType("Trip").feeAmount(TOTAL_AMOUNT).customAmount(TOTAL_AMOUNT));
        return feePayments;
    }

    public static StudentFeePaymentStatusRequest createStudentFeePaymentStatusRequest() {
        return new StudentFeePaymentStatusRequest()
                .status(FeePaymentStatus.SUCCESS.getValue())
                .paymentReference("PAY-12345");

    }

}
