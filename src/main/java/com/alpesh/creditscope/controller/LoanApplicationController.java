package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.dto.LoanApplicationResponseDTO;
import com.alpesh.creditscope.entity.LoanApplication;
import com.alpesh.creditscope.service.LoanApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loanApplication")
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping
    public LoanApplicationResponseDTO createLoanApplication(@RequestBody LoanApplication loanApplication) {
        return loanApplicationService.createLoanApplication(loanApplication);
    }

    @GetMapping("/{id}")
    public LoanApplicationResponseDTO getLoanApplication(@PathVariable Long id) {
        return loanApplicationService.getLoanApplicationById(id);
    }

    @GetMapping
    public List<LoanApplicationResponseDTO> getAllLoanApplications() {
        return loanApplicationService.getAllLoanApplications();
    }

    @PutMapping("/{id}")
    public LoanApplicationResponseDTO updateLoanApplication(@PathVariable Long id, @RequestBody LoanApplication updatedLoanApplication) {
        return loanApplicationService.updateLoanApplication(id, updatedLoanApplication);
    }

    @DeleteMapping("/{id}")
    public void deleteLoanApplication(@PathVariable Long id) {
        loanApplicationService.deleteLoanApplication(id);
    }
}