package com.rb.skiply.fee_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Grade {

    @Id
    private Integer id;

    private String gradeName;

    @ManyToMany(mappedBy = "grade")
    private List<Fee> fee;

}
