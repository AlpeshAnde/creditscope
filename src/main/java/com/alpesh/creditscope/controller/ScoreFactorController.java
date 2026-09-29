package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.service.ScoreFactorService;

public class ScoreFactorController {
    private final ScoreFactorService scoreFactorService;

    public ScoreFactorController(ScoreFactorService scoreFactorService) {
        this.scoreFactorService = scoreFactorService;
    }

}
