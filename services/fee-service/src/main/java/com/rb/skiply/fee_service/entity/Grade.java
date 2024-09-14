package com.rb.skiply.fee_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Grade {

    @Id
    private Integer id;

    private String grade;

    @OneToMany(mappedBy ="grade")
    private List<Fee> fees;

}
