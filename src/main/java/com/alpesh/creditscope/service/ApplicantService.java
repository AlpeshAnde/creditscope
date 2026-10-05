package com.alpesh.creditscope.service;

import com.alpesh.creditscope.dto.ApplicantResponseDTO;
import com.alpesh.creditscope.entity.Applicant;
import com.alpesh.creditscope.repository.ApplicantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    private ApplicantResponseDTO toDTO(Applicant applicant) {
        return new ApplicantResponseDTO(
                applicant.getId(),
                applicant.getUser() != null ? applicant.getUser().getId() : null,
                applicant.getUser() != null ? applicant.getUser().getName() : null,
                applicant.getUser() != null ? applicant.getUser().getEmail() : null,
                applicant.getDateOfBirth(),
                applicant.getEmploymentType(),
                applicant.getMonthlyIncome()
        );
    }

    //To Find All Applicant
    public List<ApplicantResponseDTO> findAll() {
        return applicantRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //To find Applicant By Id
    public ApplicantResponseDTO findById(Long id) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Applicant not found with id: " + id));
        return toDTO(applicant);
    }

    //Create Applicant
    public ApplicantResponseDTO createApplicant(Applicant applicant) {
        Applicant saved = applicantRepository.save(applicant);
        return toDTO(saved);
    }

    //To Update Applicant By Id
    public ApplicantResponseDTO updateApplicant(Long id, Applicant updatedApplicant) {
        Applicant existingApplicant = applicantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Applicant not found with id: " + id));
        existingApplicant.setEmploymentType(updatedApplicant.getEmploymentType());
        existingApplicant.setDateOfBirth(updatedApplicant.getDateOfBirth());
        existingApplicant.setMonthlyIncome(updatedApplicant.getMonthlyIncome());
        Applicant saved = applicantRepository.save(existingApplicant);
        return toDTO(saved);
    }

    //To delete The Applicant
    public void deleteApplicant(Long id) {
        applicantRepository.deleteById(id);
    }
}