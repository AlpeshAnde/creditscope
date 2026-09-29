package com.alpesh.creditscope.service;

import com.alpesh.creditscope.entity.Applicant;
import com.alpesh.creditscope.repository.ApplicantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;
    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    //To Find All Applicant
    public List<Applicant> findAll() {
        return applicantRepository.findAll();
    }

    //To find Applicant By Id
    public Applicant findById(Long id) {
        return applicantRepository.findById(id).orElse(null);
    }

    //Create Applicant
    public Applicant createApplicant(Applicant applicant) {
        return applicantRepository.save(applicant);
    }
    //To Update Applicant By Id

    public Applicant updateApplicant(Long id,Applicant updatedApplicant) {
       Applicant existingApplicant = applicantRepository.findById(id).orElseThrow(()->new RuntimeException("Applicant not found with id: " + id));
       existingApplicant.setEmploymentType(updatedApplicant.getEmploymentType());
       existingApplicant.setDateOfBirth(updatedApplicant.getDateOfBirth());
       existingApplicant.setMonthlyIncome(updatedApplicant.getMonthlyIncome());
       return applicantRepository.save(existingApplicant);
    }
    //To delete The Applicant
    public void deleteApplicant(Long id) {
        applicantRepository.deleteById(id);
    }

}
