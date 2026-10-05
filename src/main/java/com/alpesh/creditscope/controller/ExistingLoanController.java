package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.dto.ExistingLoanResponseDTO;
import com.alpesh.creditscope.entity.ExistingLoan;
import com.alpesh.creditscope.service.ExistingLoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/existingLoan")
public class ExistingLoanController {
    private final ExistingLoanService existingLoanService;

    public ExistingLoanController(ExistingLoanService existingLoanService) {
        this.existingLoanService = existingLoanService;
    }

    @PostMapping
    public ExistingLoanResponseDTO createExistingLoan(@RequestBody ExistingLoan existingLoan) {
        return existingLoanService.createExistingLoan(existingLoan);
    }

    @GetMapping("/{id}")
    public ExistingLoanResponseDTO getExistingLoan(@PathVariable Long id) {
        return existingLoanService.getExistingLoan(id);
    }

    @GetMapping
    public List<ExistingLoanResponseDTO> getAllExistingLoans() {
        return existingLoanService.getAllExistingLoans();
    }

    @PutMapping("/{id}")
    public ExistingLoanResponseDTO updateExistingLoan(@PathVariable Long id, @RequestBody ExistingLoan existingLoan) {
        return existingLoanService.updateExistingLoan(id, existingLoan);
    }

    @DeleteMapping("/{id}")
    public void deleteExistingLoan(@PathVariable Long id) {
        existingLoanService.deleteExistingLoan(id);
    }
}