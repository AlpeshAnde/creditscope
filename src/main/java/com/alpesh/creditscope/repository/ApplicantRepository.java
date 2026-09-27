package com.alpesh.creditscope.repository;

import com.alpesh.creditscope.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
}