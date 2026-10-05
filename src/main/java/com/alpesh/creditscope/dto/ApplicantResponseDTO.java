package com.alpesh.creditscope.dto;

import com.alpesh.creditscope.enums.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private LocalDate dateOfBirth;
    private EmploymentType employmentType;
    private Double monthlyIncome;
}