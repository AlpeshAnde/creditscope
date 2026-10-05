package com.alpesh.creditscope.service;

import com.alpesh.creditscope.entity.*;
import com.alpesh.creditscope.enums.EmploymentType;
import com.alpesh.creditscope.enums.RepaymentStatus;
import com.alpesh.creditscope.enums.RiskBand;
import com.alpesh.creditscope.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditScoreService {
    private final LoanApplicationRepository loanApplicationRepository;
    private final ExistingLoanRepository existingLoanRepository;
    private final RepaymentHistoryRepository repaymentHistoryRepository;
    private final ScoreFactorRepository scoreFactorRepository;
    private final CreditScoreResultRepository creditScoreResultRepository;

    public CreditScoreService(
            LoanApplicationRepository loanApplicationRepository,
            ExistingLoanRepository existingLoanRepository,
            RepaymentHistoryRepository repaymentHistoryRepository,
            ScoreFactorRepository scoreFactorRepository,
            CreditScoreResultRepository creditScoreResultRepository
    ) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.existingLoanRepository = existingLoanRepository;
        this.repaymentHistoryRepository = repaymentHistoryRepository;
        this.scoreFactorRepository = scoreFactorRepository;
        this.creditScoreResultRepository = creditScoreResultRepository;
    }

    public CreditScoreResult calculateScore(Long loanApplicationId) {
        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new RuntimeException("Loan Application not found with id: " + loanApplicationId));

        Applicant applicant = loanApplication.getApplicant();
        if (applicant == null) {
            throw new RuntimeException("Loan Application has no linked applicant");
        }

        List<ExistingLoan> existingLoans = existingLoanRepository.findByApplicantId(applicant.getId());
        List<RepaymentHistory> repaymentHistories = repaymentHistoryRepository.findByApplicantId(applicant.getId());

        double paymentHistoryRaw = calculatePaymentHistoryScore(repaymentHistories);
        double paymentHistoryContribution = paymentHistoryRaw * 0.35;
        saveFactor(loanApplication, "Payment History", paymentHistoryRaw, 0.35, paymentHistoryContribution);

        double debtToIncomeRaw = calculateDebtToIncomeScore(existingLoans, applicant.getMonthlyIncome());
        double debtToIncomeContribution = debtToIncomeRaw * 0.30;
        saveFactor(loanApplication, "Debt-to-Income Ratio", debtToIncomeRaw, 0.30, debtToIncomeContribution);

        double defaultsRaw = calculateDefaultsScore(existingLoans);
        double defaultsContribution = defaultsRaw * 0.20;
        saveFactor(loanApplication, "Existing Defaults", defaultsRaw, 0.20, defaultsContribution);

        double employmentRaw = calculateEmploymentScore(applicant.getEmploymentType());
        double employmentContribution = employmentRaw * 0.15;
        saveFactor(loanApplication, "Employment Stability", employmentRaw, 0.15, employmentContribution);

        double weightedTotal = paymentHistoryContribution + debtToIncomeContribution
                + defaultsContribution + employmentContribution;

        double totalScore = 300 + (weightedTotal / 100.0) * 550;

        RiskBand riskBand;
        if (totalScore >= 700) {
            riskBand = RiskBand.LOW;
        } else if (totalScore >= 550) {
            riskBand = RiskBand.MEDIUM;
        } else {
            riskBand = RiskBand.HIGH;
        }

        CreditScoreResult result = new CreditScoreResult();
        result.setApplication(loanApplication);
        result.setTotalScore(totalScore);
        result.setRiskBand(riskBand);
        result.setComputedAt(LocalDateTime.now());

        return creditScoreResultRepository.save(result);
    }

    private void saveFactor(LoanApplication application, String name, double rawValue, double weight, double contribution) {
        ScoreFactor factor = new ScoreFactor();
        factor.setApplication(application);
        factor.setFactorName(name);
        factor.setRawValue(rawValue);
        factor.setWeight(weight);
        factor.setContribution(contribution);
        scoreFactorRepository.save(factor);
    }

    private double calculatePaymentHistoryScore(List<RepaymentHistory> histories) {
        if (histories.isEmpty()) return 50;
        long onTimeCount = histories.stream()
                .filter(h -> h.getStatus() == RepaymentStatus.ON_TIME)
                .count();
        return (onTimeCount * 100.0) / histories.size();
    }

    private double calculateDebtToIncomeScore(List<ExistingLoan> loans, Double monthlyIncome) {
        if (monthlyIncome == null || monthlyIncome == 0) return 0;
        double totalOutstanding = loans.stream()
                .mapToDouble(l -> l.getOutstandingAmount() != null ? l.getOutstandingAmount() : 0)
                .sum();
        double ratio = totalOutstanding / (monthlyIncome * 12);
        double score = 100 - (ratio * 100);
        return Math.max(0, Math.min(100, score));
    }

    private double calculateDefaultsScore(List<ExistingLoan> loans) {
        if (loans.isEmpty()) return 100;
        boolean hasDefault = loans.stream().anyMatch(ExistingLoan::isDefaulted);
        return hasDefault ? 20 : 100;
    }

    private double calculateEmploymentScore(EmploymentType type) {
        if (type == null) return 50;
        return switch (type) {
            case SALARIED -> 100;
            case SELF_EMPLOYED -> 70;
            case UNEMPLOYED -> 20;
        };
    }
}