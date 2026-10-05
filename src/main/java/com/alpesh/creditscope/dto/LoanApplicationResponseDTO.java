package com.alpesh.creditscope.dto;

import com.alpesh.creditscope.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationResponseDTO {
    private Long id;
    private Long applicantId;
    private Double loanAmount;
    private String loanType;
    private Integer tenureMonths;
    private LocalDateTime appliedAt;
    private LoanStatus status;
}