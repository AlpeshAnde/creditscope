package com.alpesh.creditscope.service;

import com.alpesh.creditscope.dto.RepaymentHistoryResponseDTO;
import com.alpesh.creditscope.entity.RepaymentHistory;
import com.alpesh.creditscope.repository.RepaymentHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepaymentHistoryService {

    private final RepaymentHistoryRepository repaymentHistoryRepository;

    public RepaymentHistoryService(RepaymentHistoryRepository repaymentHistoryRepository) {
        this.repaymentHistoryRepository = repaymentHistoryRepository;
    }

    private RepaymentHistoryResponseDTO toDTO(RepaymentHistory history) {
        return new RepaymentHistoryResponseDTO(
                history.getId(),
                history.getApplicant() != null ? history.getApplicant().getId() : null,
                history.getLoanRef(),
                history.getDueDate(),
                history.getPaidDate(),
                history.getStatus()
        );
    }

    public List<RepaymentHistoryResponseDTO> getAllRepaymentHistories() {
        return repaymentHistoryRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RepaymentHistoryResponseDTO getRepaymentHistoryById(Long id) {
        RepaymentHistory history = repaymentHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RepaymentHistory not found with id: " + id));
        return toDTO(history);
    }

    public RepaymentHistoryResponseDTO createRepaymentHistory(RepaymentHistory repaymentHistory) {
        RepaymentHistory saved = repaymentHistoryRepository.save(repaymentHistory);
        return toDTO(saved);
    }
}