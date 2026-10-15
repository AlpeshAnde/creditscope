package com.alpesh.creditscope.service;

import com.alpesh.creditscope.entity.*;
import com.alpesh.creditscope.enums.EmploymentType;
import com.alpesh.creditscope.enums.RepaymentStatus;
import com.alpesh.creditscope.enums.RiskBand;
import com.alpesh.creditscope.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditScoreServiceTest {

    @Mock private LoanApplicationRepository loanApplicationRepository;
    @Mock private ExistingLoanRepository existingLoanRepository;
    @Mock private RepaymentHistoryRepository repaymentHistoryRepository;
    @Mock private ScoreFactorRepository scoreFactorRepository;
    @Mock private CreditScoreResultRepository creditScoreResultRepository;

    @InjectMocks private CreditScoreService service;

    private Applicant applicant;
    private LoanApplication application;

    @BeforeEach
    void setUp() {
        applicant = new Applicant();
        applicant.setId(1L);

        application = new LoanApplication();
        application.setId(10L);
        application.setApplicant(applicant);
    }

    // Stubs the repositories so the service sees the given history and loans.
    private void givenApplicantData(List<RepaymentHistory> repayments, List<ExistingLoan> loans) {
        when(loanApplicationRepository.findById(10L)).thenReturn(Optional.of(application));
        when(existingLoanRepository.findByApplicantId(1L)).thenReturn(loans);
        when(repaymentHistoryRepository.findByApplicantId(1L)).thenReturn(repayments);
        when(creditScoreResultRepository.save(any(CreditScoreResult.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    private List<RepaymentHistory> repayments(int onTime, int late, int missed) {
        List<RepaymentHistory> list = new ArrayList<>();
        for (int i = 0; i < onTime; i++) list.add(repayment(RepaymentStatus.ON_TIME));
        for (int i = 0; i < late; i++) list.add(repayment(RepaymentStatus.LATE));
        for (int i = 0; i < missed; i++) list.add(repayment(RepaymentStatus.MISSED));
        return list;
    }

    private RepaymentHistory repayment(RepaymentStatus status) {
        RepaymentHistory r = new RepaymentHistory();
        r.setApplicant(applicant);
        r.setStatus(status);
        return r;
    }

    private ExistingLoan loan(double outstanding, boolean defaulted) {
        ExistingLoan l = new ExistingLoan();
        l.setApplicant(applicant);
        l.setOutstandingAmount(outstanding);
        l.setDefaulted(defaulted);
        return l;
    }

    @Test
    void cleanHistoryGivesLowRiskAndMaximumScore() {
        applicant.setEmploymentType(EmploymentType.SALARIED);
        applicant.setMonthlyIncome(100000.0);
        givenApplicantData(repayments(4, 0, 0), List.of());

        CreditScoreResult result = service.calculateScore(10L);

        // 35 + 30 + 20 + 15 = 100 weighted points -> 300 + 550 = 850
        assertEquals(850.0, result.getTotalScore(), 0.001);
        assertEquals(RiskBand.LOW, result.getRiskBand());
    }

    @Test
    void defaultsAndMissedPaymentsGiveHighRisk() {
        applicant.setEmploymentType(EmploymentType.UNEMPLOYED);
        applicant.setMonthlyIncome(20000.0);
        // 0 of 4 on time, debt is 2x annual income, one defaulted loan
        givenApplicantData(repayments(0, 0, 4), List.of(loan(480000.0, true)));

        CreditScoreResult result = service.calculateScore(10L);

        // 0 + 0 + (20 * 0.20 = 4) + (20 * 0.15 = 3) = 7 -> 300 + 0.07 * 550 = 338.5
        assertEquals(338.5, result.getTotalScore(), 0.001);
        assertEquals(RiskBand.HIGH, result.getRiskBand());
    }

    @Test
    void mixedProfileGivesMediumRisk() {
        applicant.setEmploymentType(EmploymentType.SELF_EMPLOYED);
        applicant.setMonthlyIncome(50000.0);
        // 2 of 4 on time, debt is half of annual income, no defaults
        givenApplicantData(repayments(2, 1, 1), List.of(loan(300000.0, false)));

        CreditScoreResult result = service.calculateScore(10L);

        // 50*0.35 = 17.5, 50*0.30 = 15, 100*0.20 = 20, 70*0.15 = 10.5 -> 63 -> 646.5
        assertEquals(646.5, result.getTotalScore(), 0.001);
        assertEquals(RiskBand.MEDIUM, result.getRiskBand());
    }

    @Test
    void savesFourExplainableFactorsPerScore() {
        applicant.setEmploymentType(EmploymentType.SALARIED);
        applicant.setMonthlyIncome(100000.0);
        givenApplicantData(repayments(4, 0, 0), List.of());

        service.calculateScore(10L);

        verify(scoreFactorRepository, times(4)).save(any(ScoreFactor.class));
    }

    @Test
    void unknownApplicationThrowsException() {
        when(loanApplicationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.calculateScore(99L));
    }
}