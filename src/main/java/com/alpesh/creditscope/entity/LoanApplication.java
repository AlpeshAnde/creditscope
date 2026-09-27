package com.alpesh.creditscope.entity;

import com.alpesh.creditscope.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;
    private Double loanAmount;
    private String loanType;
    private Integer tenureMonths;
    private LocalDateTime appliedAt;
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
}
