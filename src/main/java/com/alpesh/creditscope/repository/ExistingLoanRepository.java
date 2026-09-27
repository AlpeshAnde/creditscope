package com.alpesh.creditscope.repository;

import com.alpesh.creditscope.entity.ExistingLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExistingLoanRepository extends JpaRepository<ExistingLoan, Long> {
}
