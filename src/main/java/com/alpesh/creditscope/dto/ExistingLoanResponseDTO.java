package com.alpesh.creditscope.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExistingLoanResponseDTO {
    private Long id;
    private Long applicantId;
    private String lender;
    private Double outstandingAmount;
    private Double emiAmount;
    private boolean defaulted;
}