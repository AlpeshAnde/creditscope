package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.entity.ExistingLoan;
import com.alpesh.creditscope.service.ExistingLoanService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/existingLoan")
public class ExistingLoanController {
    private final ExistingLoanService existingLoanService;
    public ExistingLoanController(ExistingLoanService existingLoanService) {
        this.existingLoanService = existingLoanService;
    }
    //Assigning The Loan
    @PostMapping
    public ExistingLoan createExistingLoan(@RequestBody ExistingLoan existingLoan) {
        return existingLoanService.createExistingLoan(existingLoan);
    }
    //getting Loan by id
    @GetMapping("/{id}")
    public ExistingLoan getExistingLoan(@PathVariable Long id) {
        return existingLoanService.getExistingLoan(id);
    }
    //Update Existing Loan
    @PutMapping("/{id}")
    public ExistingLoan updateExistingLoan(@PathVariable Long id,@RequestBody ExistingLoan existingLoan) {
        return existingLoanService.updateExistingLoan(id,existingLoan);
    }
    //Remove Application Or Delete
    @DeleteMapping("/{id}")
    public void deleteExistingLoan(@PathVariable Long id) {
        existingLoanService.deleteExistingLoan(id);
    }

}
