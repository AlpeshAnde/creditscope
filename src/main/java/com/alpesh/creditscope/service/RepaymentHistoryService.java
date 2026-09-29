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

    // Create
    public RepaymentHistory createRepaymentHistory(RepaymentHistory repaymentHistory) {
        return repaymentHistoryRepository.save(repaymentHistory);
    }

    // Delete
    public void deleteRepaymentHistory(Long id) {
        repaymentHistoryRepository.deleteById(id);
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

    // Update
    public RepaymentHistory updateRepaymentHistory(RepaymentHistory repaymentHistory) {
        return repaymentHistoryRepository.save(repaymentHistory);
    }
}