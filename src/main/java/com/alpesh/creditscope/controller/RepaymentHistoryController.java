package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.dto.RepaymentHistoryResponseDTO;
import com.alpesh.creditscope.entity.RepaymentHistory;
import com.alpesh.creditscope.service.RepaymentHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repaymentHistory")
public class RepaymentHistoryController {
    private final RepaymentHistoryService repaymentHistoryService;

    public RepaymentHistoryController(RepaymentHistoryService repaymentHistoryService) {
        this.repaymentHistoryService = repaymentHistoryService;
    }

    @GetMapping
    public List<RepaymentHistoryResponseDTO> getAllRepaymentHistories() {
        return repaymentHistoryService.getAllRepaymentHistories();
    }

    @GetMapping("/{id}")
    public RepaymentHistoryResponseDTO getRepaymentHistoryById(@PathVariable Long id) {
        return repaymentHistoryService.getRepaymentHistoryById(id);
    }

    @PostMapping
    public RepaymentHistoryResponseDTO createRepaymentHistory(@RequestBody RepaymentHistory repaymentHistory) {
        return repaymentHistoryService.createRepaymentHistory(repaymentHistory);
    }
}