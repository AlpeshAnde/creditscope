package com.alpesh.creditscope.service;

import com.alpesh.creditscope.dto.LoanApplicationResponseDTO;
import com.alpesh.creditscope.entity.LoanApplication;
import com.alpesh.creditscope.repository.LoanApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
    }

    private LoanApplicationResponseDTO toDTO(LoanApplication loan) {
        return new LoanApplicationResponseDTO(
                loan.getId(),
                loan.getApplicant() != null ? loan.getApplicant().getId() : null,
                loan.getLoanAmount(),
                loan.getLoanType(),
                loan.getTenureMonths(),
                loan.getAppliedAt(),
                loan.getStatus()
        );
    }

    public LoanApplicationResponseDTO createLoanApplication(LoanApplication loanApplication) {
        LoanApplication saved = loanApplicationRepository.save(loanApplication);
        return toDTO(saved);
    }

    public LoanApplicationResponseDTO getLoanApplicationById(Long id) {
        LoanApplication loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan Application not found with id: " + id));
        return toDTO(loan);
    }

    public List<LoanApplicationResponseDTO> getAllLoanApplications() {
        return loanApplicationRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public LoanApplicationResponseDTO updateLoanApplication(Long id, LoanApplication updatedLoanApplication) {
        LoanApplication existingLoanApplication = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan Application not found with id: " + id));

        existingLoanApplication.setLoanAmount(updatedLoanApplication.getLoanAmount());
        existingLoanApplication.setLoanType(updatedLoanApplication.getLoanType());
        existingLoanApplication.setTenureMonths(updatedLoanApplication.getTenureMonths());
        existingLoanApplication.setAppliedAt(updatedLoanApplication.getAppliedAt());
        LoanApplication saved = loanApplicationRepository.save(existingLoanApplication);
        return toDTO(saved);
    }

    public void deleteLoanApplication(Long id) {
        if (!loanApplicationRepository.existsById(id)) {
            throw new RuntimeException("Loan Application not found with id: " + id);
        }
        loanApplicationRepository.deleteById(id);
    }
}