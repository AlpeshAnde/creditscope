package com.alpesh.creditscope.entity;

import com.alpesh.creditscope.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;
    private Double monthlyIncome;


}
