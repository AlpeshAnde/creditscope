package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.entity.ScoreFactor;
import com.alpesh.creditscope.service.ScoreFactorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scoreFactor")
public class ScoreFactorController {
    private final ScoreFactorService scoreFactorService;

    public ScoreFactorController(ScoreFactorService scoreFactorService) {
        this.scoreFactorService = scoreFactorService;
    }
    //Getting All Score Factors
    @GetMapping
    public List<ScoreFactor> getAllScoreFactors() {
        return scoreFactorService.getAllScoreFactors();
    }

    //Getting Score Factor by id
    @GetMapping("/{id}")
    public ScoreFactor getScoreFactorById(@PathVariable Long id) {
        return scoreFactorService.getScoreFactorById(id);
    }
}
