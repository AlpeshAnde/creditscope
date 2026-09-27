package com.alpesh.creditscope.repository;

import com.alpesh.creditscope.entity.RepaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepaymentHistoryRepository extends JpaRepository<RepaymentHistory, Long> {
}
