package com.alpesh.creditscope.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ExistingLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    private String lender;
    private Double outstandingAmount;
    private Double emiAmount;
    private boolean isDefaulted;
}