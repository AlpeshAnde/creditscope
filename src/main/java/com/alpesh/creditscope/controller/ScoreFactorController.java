package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.dto.ScoreFactorResponseDTO;
import com.alpesh.creditscope.service.ScoreFactorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scoreFactor")
public class ScoreFactorController {
    private final ScoreFactorService scoreFactorService;

    public ScoreFactorController(ScoreFactorService scoreFactorService) {
        this.scoreFactorService = scoreFactorService;
    }

    @GetMapping
    public List<ScoreFactorResponseDTO> getAllScoreFactors() {
        return scoreFactorService.getAllScoreFactors();
    }

    @GetMapping("/{id}")
    public ScoreFactorResponseDTO getScoreFactorById(@PathVariable Long id) {
        return scoreFactorService.getScoreFactorById(id);
    }
}