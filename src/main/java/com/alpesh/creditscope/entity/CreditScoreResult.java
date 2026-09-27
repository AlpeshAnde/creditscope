package com.alpesh.creditscope.entity;

import com.alpesh.creditscope.enums.RiskBand;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class CreditScoreResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private LoanApplication application;

    private Double totalScore;

    @Enumerated(EnumType.STRING)
    private RiskBand riskBand;

    private LocalDateTime computedAt;
}