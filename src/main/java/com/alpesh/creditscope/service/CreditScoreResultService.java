package com.alpesh.creditscope.service;

import com.alpesh.creditscope.entity.CreditScoreResult;
import com.alpesh.creditscope.repository.CreditScoreResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditScoreResultService {

    private final CreditScoreResultRepository creditScoreResultRepository;

    public CreditScoreResultService(CreditScoreResultRepository creditScoreResultRepository) {
        this.creditScoreResultRepository = creditScoreResultRepository;
    }

    // Create credit score result
    public CreditScoreResult createCreditScoreResult(CreditScoreResult savecreditScoreResult) {
        return creditScoreResultRepository.save(savecreditScoreResult);
    }

    // Get credit score result by id
    public CreditScoreResult getCreditScoreResult(Long id) {
        return creditScoreResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit Score Result not found with id: " + id));
    }

    // Update credit score result
    public CreditScoreResult updateCreditScoreResult(Long id, CreditScoreResult updatedCreditScoreResult) {
        CreditScoreResult existing = creditScoreResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit Score Result not found with id: " + id));

        existing.setApplication(updatedCreditScoreResult.getApplication());
        existing.setTotalScore(updatedCreditScoreResult.getTotalScore());
        existing.setComputedAt(updatedCreditScoreResult.getComputedAt());
        existing.setRiskBand(updatedCreditScoreResult.getRiskBand());
        return creditScoreResultRepository.save(existing);
    }

    // Delete credit score result
    public void deleteCreditScoreResult(Long id) {
        if (!creditScoreResultRepository.existsById(id)) {
            throw new RuntimeException("Credit Score Result not found with id: " + id);
        }
        creditScoreResultRepository.deleteById(id);
    }
    //Get all Credit Score
    public List<CreditScoreResult> getAllCreditScoreResults() {
        return creditScoreResultRepository.findAll();
    }
}