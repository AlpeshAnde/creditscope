package com.alpesh.creditscope.service;

import com.alpesh.creditscope.entity.ExistingLoan;
import com.alpesh.creditscope.repository.ExistingLoanRepository;
import org.springframework.stereotype.Service;

@Service
public class ExistingLoanService {

    private final ExistingLoanRepository existingLoanRepository;

    public ExistingLoanService(ExistingLoanRepository existingLoanRepository) {
        this.existingLoanRepository = existingLoanRepository;
    }

    // Create an existing loan
    public ExistingLoan createExistingLoan(ExistingLoan existingLoan) {
        return existingLoanRepository.save(existingLoan);
    }

    // Get existing loan by id
    public ExistingLoan getExistingLoan(Long id) {
        return existingLoanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Existing Loan not found with id: " + id));
    }

    // Update existing loan
    public ExistingLoan updateExistingLoan(Long id, ExistingLoan updatedExistingLoan) {
        ExistingLoan existingLoan = existingLoanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Existing Loan not found with id: " + id));

        existingLoan.setApplicant(updatedExistingLoan.getApplicant());
        existingLoan.setDefaulted(updatedExistingLoan.isDefaulted());
        existingLoan.setOutstandingAmount(updatedExistingLoan.getOutstandingAmount());
        existingLoan.setLender(updatedExistingLoan.getLender());
        return existingLoanRepository.save(existingLoan);
    }

    // Delete existing loan
    public void deleteExistingLoan(Long id) {
        if (!existingLoanRepository.existsById(id)) {
            throw new RuntimeException("Existing Loan not found with id: " + id);
        }
        existingLoanRepository.deleteById(id);
    }
}