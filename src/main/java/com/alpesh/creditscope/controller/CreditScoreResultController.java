package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.entity.CreditScoreResult;
import com.alpesh.creditscope.service.CreditScoreResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditScoreResult")
public class CreditScoreResultController {
    private final CreditScoreResultService creditScoreResultService;

    public CreditScoreResultController(CreditScoreResultService creditScoreResultService) {
        this.creditScoreResultService = creditScoreResultService;
    }
    //Save Credit score
    @PostMapping
    public CreditScoreResult createCreditScoreResult(@RequestBody CreditScoreResult creditScoreResult) {
        return creditScoreResultService.createCreditScoreResult(creditScoreResult);
    }
    //update all Credit Score Results
    @PutMapping("/{id}")
    public CreditScoreResult updateCreditScoreResult(@PathVariable Long id, @RequestBody CreditScoreResult creditScoreResult) {
        return creditScoreResultService.updateCreditScoreResult(id, creditScoreResult);
    }
    //delete The Credit Score Result
    @DeleteMapping("/{id}")
    public void deleteCreditScoreResult(@PathVariable Long id) {
        creditScoreResultService.deleteCreditScoreResult(id);
    }
    //getting credit score by id
    @GetMapping("/{id}")
    public CreditScoreResult getCreditScoreResult(@PathVariable Long id) {
        return creditScoreResultService.getCreditScoreResult(id);
    }
    //Get all Credit Score
    @GetMapping
    public List<CreditScoreResult> getAllCreditScoreResults() {
        return creditScoreResultService.getAllCreditScoreResults();
    }
}
