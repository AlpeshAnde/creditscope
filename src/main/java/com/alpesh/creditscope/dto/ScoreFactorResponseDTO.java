package com.alpesh.creditscope.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreFactorResponseDTO {
    private Long id;
    private Long applicationId;
    private String factorName;
    private Double rawValue;
    private Double weight;
    private Double contribution;
}