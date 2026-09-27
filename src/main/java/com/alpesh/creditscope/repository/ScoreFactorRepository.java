package com.alpesh.creditscope.repository;

import com.alpesh.creditscope.entity.ScoreFactor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreFactorRepository extends JpaRepository<ScoreFactor, Long> {
}
