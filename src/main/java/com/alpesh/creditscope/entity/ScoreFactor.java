package com.alpesh.creditscope.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ScoreFactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private LoanApplication application;

    private String factorName;
    private Double rawValue;
    private Double weight;
    private Double contribution;
}