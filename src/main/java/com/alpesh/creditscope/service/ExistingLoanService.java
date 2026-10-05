package com.alpesh.creditscope.service;

import com.alpesh.creditscope.dto.ExistingLoanResponseDTO;
import com.alpesh.creditscope.entity.ExistingLoan;
import com.alpesh.creditscope.repository.ExistingLoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExistingLoanService {

    private final ExistingLoanRepository existingLoanRepository;

    public ExistingLoanService(ExistingLoanRepository existingLoanRepository) {
        this.existingLoanRepository = existingLoanRepository;
    }

    private ExistingLoanResponseDTO toDTO(ExistingLoan loan) {
        return new ExistingLoanResponseDTO(
                loan.getId(),
                loan.getApplicant() != null ? loan.getApplicant().getId() : null,
                loan.getLender(),
                loan.getOutstandingAmount(),
                loan.getEmiAmount(),
                loan.isDefaulted()
        );
    }

    public ExistingLoanResponseDTO createExistingLoan(ExistingLoan existingLoan) {
        ExistingLoan saved = existingLoanRepository.save(existingLoan);
        return toDTO(saved);
    }

    public ExistingLoanResponseDTO getExistingLoan(Long id) {
        ExistingLoan loan = existingLoanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Existing Loan not found with id: " + id));
        return toDTO(loan);
    }

    public List<ExistingLoanResponseDTO> getAllExistingLoans() {
        return existingLoanRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ExistingLoanResponseDTO updateExistingLoan(Long id, ExistingLoan updatedExistingLoan) {
        ExistingLoan existingLoan = existingLoanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Existing Loan not found with id: " + id));

        existingLoan.setApplicant(updatedExistingLoan.getApplicant());
        existingLoan.setDefaulted(updatedExistingLoan.isDefaulted());
        existingLoan.setOutstandingAmount(updatedExistingLoan.getOutstandingAmount());
        existingLoan.setLender(updatedExistingLoan.getLender());
        existingLoan.setEmiAmount(updatedExistingLoan.getEmiAmount());
        ExistingLoan saved = existingLoanRepository.save(existingLoan);
        return toDTO(saved);
    }

    public void deleteExistingLoan(Long id) {
        if (!existingLoanRepository.existsById(id)) {
            throw new RuntimeException("Existing Loan not found with id: " + id);
        }
        existingLoanRepository.deleteById(id);
    }
}