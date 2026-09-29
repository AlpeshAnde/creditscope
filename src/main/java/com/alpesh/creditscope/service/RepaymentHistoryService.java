package com.alpesh.creditscope.service;

import com.alpesh.creditscope.entity.RepaymentHistory;
import com.alpesh.creditscope.repository.RepaymentHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepaymentHistoryService {

    private final RepaymentHistoryRepository repaymentHistoryRepository;

    public RepaymentHistoryService(RepaymentHistoryRepository repaymentHistoryRepository) {
        this.repaymentHistoryRepository = repaymentHistoryRepository;
    }
    // Get All
    public List<RepaymentHistory> getAllRepaymentHistories() {
        return repaymentHistoryRepository.findAll();
    }

    // Get By Id
    public RepaymentHistory getRepaymentHistoryById(Long id) {
        return repaymentHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RepaymentHistory not found with id: " + id));
    }
    //Crate repayment History
    public RepaymentHistory createRepaymentHistory(RepaymentHistory repaymentHistory) {
        return repaymentHistoryRepository.save(repaymentHistory);
    }


}