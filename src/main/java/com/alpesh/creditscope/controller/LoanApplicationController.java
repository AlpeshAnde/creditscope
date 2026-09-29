package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.entity.LoanApplication;
import com.alpesh.creditscope.service.LoanApplicationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loanApplication")
public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService;
    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }
   //create Loan Application
    @PostMapping
   public LoanApplication createLoanApplication(@RequestBody LoanApplication loanApplication) {
        return loanApplicationService.createLoanApplication(loanApplication);
    }
    //Get Loan Application By id
    @GetMapping("/{id}")
    public LoanApplication getLoanApplication(@PathVariable Long id) {
        return loanApplicationService.getLoanApplicationById(id);
    }
    //update Loan Application
    @PutMapping("/{id}")
    public LoanApplication updateLoanApplication(@PathVariable Long id,@RequestBody LoanApplication updatedLoanApplication) {
        return loanApplicationService.updateLoanApplication(id, updatedLoanApplication);
    }
    //Delete LoanApplication
    @DeleteMapping("/{id}")
    public void deleteLoanApplication(@PathVariable Long id) {
        loanApplicationService.deleteLoanApplication(id);
    }
}
