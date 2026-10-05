package com.alpesh.creditscope.dto;

import com.alpesh.creditscope.enums.RiskBand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditScoreResultResponseDTO {
    private Long id;
    private Long applicationId;
    private Double totalScore;
    private RiskBand riskBand;
    private LocalDateTime computedAt;
}