package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.dto.CreditScoreResultResponseDTO;
import com.alpesh.creditscope.entity.CreditScoreResult;
import com.alpesh.creditscope.entity.LoanApplication;
import com.alpesh.creditscope.repository.LoanApplicationRepository;
import com.alpesh.creditscope.service.CreditScoreResultService;
import com.alpesh.creditscope.service.CreditScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/creditScoreResult")
public class CreditScoreResultController {

    private final CreditScoreResultService creditScoreResultService;
    private final CreditScoreService creditScoreService;
    private final LoanApplicationRepository loanApplicationRepository;

    public CreditScoreResultController(CreditScoreResultService creditScoreResultService,
                                       CreditScoreService creditScoreService,
                                       LoanApplicationRepository loanApplicationRepository) {
        this.creditScoreResultService = creditScoreResultService;
        this.creditScoreService = creditScoreService;
        this.loanApplicationRepository = loanApplicationRepository;
    }

    // Runs the scoring engine for one loan application and returns the saved result as a DTO
    @PostMapping("/calculate/{loanApplicationId}")
    public CreditScoreResultResponseDTO calculate(@PathVariable Long loanApplicationId) {
        CreditScoreResult result = creditScoreService.calculateScore(loanApplicationId);
        return creditScoreResultService.getCreditScoreResult(result.getId());
    }

    @GetMapping("/{id}")
    public CreditScoreResultResponseDTO getCreditScoreResult(@PathVariable Long id) {
        return creditScoreResultService.getCreditScoreResult(id);
    }

    @GetMapping
    public List<CreditScoreResultResponseDTO> getAllCreditScoreResults() {
        return creditScoreResultService.getAllCreditScoreResults();
    }

    // Scores every loan application (re-running adds a new score row each time,
    // which is why v_latest_score exists)
    @PostMapping("/calculate-all")
    public Map<String, Object> calculateAll() {
        int scored = 0;
        for (LoanApplication app : loanApplicationRepository.findAll()) {
            creditScoreService.calculateScore(app.getId());
            scored++;
        }
        return Map.of("scoredApplications", scored);
    }
}