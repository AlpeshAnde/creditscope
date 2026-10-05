package com.alpesh.creditscope.repository;

import com.alpesh.creditscope.entity.RepaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepaymentHistoryRepository extends JpaRepository<RepaymentHistory, Long> {
    List<RepaymentHistory> findByApplicantId(Long applicantId);
}
