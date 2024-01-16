package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
