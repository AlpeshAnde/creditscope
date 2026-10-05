package com.alpesh.creditscope.service;

import com.alpesh.creditscope.dto.CreditScoreResultResponseDTO;
import com.alpesh.creditscope.entity.CreditScoreResult;
import com.alpesh.creditscope.repository.CreditScoreResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditScoreResultService {

    private final CreditScoreResultRepository creditScoreResultRepository;

    public CreditScoreResultService(CreditScoreResultRepository creditScoreResultRepository) {
        this.creditScoreResultRepository = creditScoreResultRepository;
    }

    private CreditScoreResultResponseDTO toDTO(CreditScoreResult result) {
        return new CreditScoreResultResponseDTO(
                result.getId(),
                result.getApplication() != null ? result.getApplication().getId() : null,
                result.getTotalScore(),
                result.getRiskBand(),
                result.getComputedAt()
        );
    }

    public CreditScoreResultResponseDTO createCreditScoreResult(CreditScoreResult creditScoreResult) {
        CreditScoreResult saved = creditScoreResultRepository.save(creditScoreResult);
        return toDTO(saved);
    }

    public CreditScoreResultResponseDTO getCreditScoreResult(Long id) {
        CreditScoreResult result = creditScoreResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit Score Result not found with id: " + id));
        return toDTO(result);
    }

    public List<CreditScoreResultResponseDTO> getAllCreditScoreResults() {
        return creditScoreResultRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CreditScoreResultResponseDTO updateCreditScoreResult(Long id, CreditScoreResult updatedCreditScoreResult) {
        CreditScoreResult existing = creditScoreResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit Score Result not found with id: " + id));

        existing.setApplication(updatedCreditScoreResult.getApplication());
        existing.setTotalScore(updatedCreditScoreResult.getTotalScore());
        existing.setComputedAt(updatedCreditScoreResult.getComputedAt());
        existing.setRiskBand(updatedCreditScoreResult.getRiskBand());
        CreditScoreResult saved = creditScoreResultRepository.save(existing);
        return toDTO(saved);
    }

    public void deleteCreditScoreResult(Long id) {
        if (!creditScoreResultRepository.existsById(id)) {
            throw new RuntimeException("Credit Score Result not found with id: " + id);
        }
        creditScoreResultRepository.deleteById(id);
    }
}