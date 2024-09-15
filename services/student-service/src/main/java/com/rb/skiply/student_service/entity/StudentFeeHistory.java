package com.rb.skiply.student_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StudentFeeHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String studentId;

    @OneToMany
    private List<StudentFee> fees;

    private LocalDate academicYear;

}
