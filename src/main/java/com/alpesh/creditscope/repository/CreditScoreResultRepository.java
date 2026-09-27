package com.alpesh.creditscope.repository;

import com.alpesh.creditscope.entity.CreditScoreResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditScoreResultRepository extends JpaRepository<CreditScoreResult, Long> {
}
