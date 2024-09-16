package com.rb.skiply.student_service.service;

import com.rb.skiply.fee_service.openapi.model.Fee;
import com.rb.skiply.fee_service.openapi.model.FeeDetails;
import com.rb.skiply.student_service.entity.Student;
import com.rb.skiply.student_service.entity.StudentFee;
import com.rb.skiply.student_service.entity.StudentFeeHistory;
import com.rb.skiply.student_service.openapi.model.StudentRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class StudentServiceTest {

    public static Student createStudent() {

        return Student.builder()
                .id(1)
                .studentId("KG1-019")
                .studentName("SS")
                .grade("KG1")
                .mobileNumber("1234567")
                .schoolName("TJC-Highschool")
                .build();
    }

    public static StudentRequest createStudentRequest() {
        return new StudentRequest()
                .studentId("KG1-019")
                .studentName("SS")
                .grade(StudentRequest.GradeEnum.KG1)
                .mobileNumber("1234567")
                .schoolName("TJC-Highschool");
    }

    public static FeeDetails createFeeDetails() {
        return new FeeDetails().addDataItem(new Fee().feeAmount(BigDecimal.valueOf(100)).feeType("Tuition"))
                .addDataItem(new Fee().feeAmount(BigDecimal.valueOf(20)).feeType("Uniform"));
    }

    public static List<StudentFee> studentFeeList() {

        List<StudentFee> studentFeeList = new ArrayList<>();
        studentFeeList.add(StudentFee.builder().feeType("Tuition").feeAmount(BigDecimal.valueOf(100)).build());
        return studentFeeList;
    }

    public static StudentFeeHistory studentFeeHistory() {
        return StudentFeeHistory.builder()
                .id(1)
                .studentId("KG1-019")
                .fees(studentFeeList())
                .build();
    }


}
