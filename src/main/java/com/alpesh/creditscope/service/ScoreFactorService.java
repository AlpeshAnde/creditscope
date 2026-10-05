package com.alpesh.creditscope.service;

import com.alpesh.creditscope.dto.ScoreFactorResponseDTO;
import com.alpesh.creditscope.entity.ScoreFactor;
import com.alpesh.creditscope.repository.ScoreFactorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreFactorService {

    private final ScoreFactorRepository scoreFactorRepository;

    public ScoreFactorService(ScoreFactorRepository scoreFactorRepository) {
        this.scoreFactorRepository = scoreFactorRepository;
    }

    private ScoreFactorResponseDTO toDTO(ScoreFactor factor) {
        return new ScoreFactorResponseDTO(
                factor.getId(),
                factor.getApplication() != null ? factor.getApplication().getId() : null,
                factor.getFactorName(),
                factor.getRawValue(),
                factor.getWeight(),
                factor.getContribution()
        );
    }

    public List<ScoreFactorResponseDTO> getAllScoreFactors() {
        return scoreFactorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ScoreFactorResponseDTO getScoreFactorById(Long id) {
        ScoreFactor factor = scoreFactorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ScoreFactor not found with id: " + id));
        return toDTO(factor);
    }
}