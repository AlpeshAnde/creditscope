package com.alpesh.creditscope.dto;

import com.alpesh.creditscope.enums.RepaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentHistoryResponseDTO {
    private Long id;
    private Long applicantId;
    private String loanRef;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private RepaymentStatus status;
}