package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.dto.CreditScoreResultResponseDTO;
import com.alpesh.creditscope.entity.CreditScoreResult;
import com.alpesh.creditscope.service.CreditScoreResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditScoreResult")
public class CreditScoreResultController {
    private final CreditScoreResultService creditScoreResultService;

    public CreditScoreResultController(CreditScoreResultService creditScoreResultService) {
        this.creditScoreResultService = creditScoreResultService;
    }

    @PostMapping
    public CreditScoreResultResponseDTO createCreditScoreResult(@RequestBody CreditScoreResult creditScoreResult) {
        return creditScoreResultService.createCreditScoreResult(creditScoreResult);
    }

    @PutMapping("/{id}")
    public CreditScoreResultResponseDTO updateCreditScoreResult(@PathVariable Long id, @RequestBody CreditScoreResult creditScoreResult) {
        return creditScoreResultService.updateCreditScoreResult(id, creditScoreResult);
    }

    @DeleteMapping("/{id}")
    public void deleteCreditScoreResult(@PathVariable Long id) {
        creditScoreResultService.deleteCreditScoreResult(id);
    }

    @GetMapping("/{id}")
    public CreditScoreResultResponseDTO getCreditScoreResult(@PathVariable Long id) {
        return creditScoreResultService.getCreditScoreResult(id);
    }

    @GetMapping
    public List<CreditScoreResultResponseDTO> getAllCreditScoreResults() {
        return creditScoreResultService.getAllCreditScoreResults();
    }
}