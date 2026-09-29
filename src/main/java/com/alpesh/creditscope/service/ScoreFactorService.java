package com.alpesh.creditscope.service;

import com.alpesh.creditscope.entity.ScoreFactor;
import com.alpesh.creditscope.repository.ScoreFactorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreFactorService {

    private final ScoreFactorRepository scoreFactorRepository;

    public ScoreFactorService(ScoreFactorRepository scoreFactorRepository) {
        this.scoreFactorRepository = scoreFactorRepository;
    }

    // Create
    public ScoreFactor createScoreFactor(ScoreFactor scoreFactor) {
        return scoreFactorRepository.save(scoreFactor);
    }

    // Get All
    public List<ScoreFactor> getAllScoreFactors() {
        return scoreFactorRepository.findAll();
    }

    // Get By Id
    public ScoreFactor getScoreFactorById(Long id) {
        return scoreFactorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ScoreFactor not found with id: " + id));
    }

    // No update — score factors are calculated outputs tied to a specific
    // credit score run and shouldn't be edited after the fact
    // No hard delete — treat as immutable history; consider soft delete later if needed
}