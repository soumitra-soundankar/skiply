package com.rb.skiply.student_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentFeeHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String studentId;

    @OneToMany
    private List<StudentFee> fees;

    private LocalDate academicYear;

}
