package com.alpesh.creditscope.controller;

import com.alpesh.creditscope.entity.Applicant;
import com.alpesh.creditscope.service.ApplicantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applicant")
public class ApplicantController {
    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }
    //Creating Applicant
    @PostMapping
    public Applicant createApplicant( Applicant applicant) {
        return applicantService.createApplicant(applicant);
    }
    //Updating Applicant
    @PutMapping("/{id}")
    public Applicant updateApplicant(@PathVariable Long id,@RequestBody Applicant applicant) {
        return applicantService.updateApplicant(id, applicant);
    }
    //Getting Applicant By id
    @GetMapping("/{id}")
    public Applicant getApplicantById(@PathVariable Long id) {
        return applicantService.findById(id);
    }
    //Getting All Applicant
    @GetMapping
    public List<Applicant> getApplicants() {
        return applicantService.findAll();
    }
    //delete Applicant
    @DeleteMapping("/{id}")
    public void deleteApplicant(@PathVariable Long id) {
        applicantService.deleteApplicant(id);
    }

}
