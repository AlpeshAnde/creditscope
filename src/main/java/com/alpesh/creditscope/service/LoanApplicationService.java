package com.alpesh.creditscope.service;

import com.alpesh.creditscope.entity.LoanApplication;
import com.alpesh.creditscope.repository.LoanApplicationRepository;
import org.springframework.stereotype.Service;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
    }

    // Create loan application
    public LoanApplication createLoanApplication(LoanApplication loanApplication) {
        return loanApplicationRepository.save(loanApplication);
    }

    // Get loan application by id
    public LoanApplication getLoanApplicationById(Long id) {
        return loanApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan Application not found with id: " + id));
    }

    // Update loan application
    public LoanApplication updateLoanApplication(Long id, LoanApplication updatedLoanApplication) {
        LoanApplication existingLoanApplication = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan Application not found with id: " + id));

        existingLoanApplication.setLoanAmount(updatedLoanApplication.getLoanAmount());
        existingLoanApplication.setLoanType(updatedLoanApplication.getLoanType());
        existingLoanApplication.setTenureMonths(updatedLoanApplication.getTenureMonths());
        existingLoanApplication.setAppliedAt(updatedLoanApplication.getAppliedAt());
        return loanApplicationRepository.save(existingLoanApplication);
    }

    // Delete loan application
    public void deleteLoanApplication(Long id) {
        if (!loanApplicationRepository.existsById(id)) {
            throw new RuntimeException("Loan Application not found with id: " + id);
        }
        loanApplicationRepository.deleteById(id);
    }
}