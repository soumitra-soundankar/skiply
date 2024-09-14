package com.rb.skiply.student_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String studentName;

    private String studentId;

    private String grade;

    private String mobileNumber;

    private String schoolName;

}
