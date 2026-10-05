package com.alpesh.creditscope.repository;

import com.alpesh.creditscope.entity.ExistingLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExistingLoanRepository extends JpaRepository<ExistingLoan, Long> {

    List<ExistingLoan> findByApplicantId(Long applicantId);
}
