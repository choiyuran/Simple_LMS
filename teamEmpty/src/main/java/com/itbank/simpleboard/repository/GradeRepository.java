package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.Evaluation;
import com.itbank.simpleboard.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<Grade> findByenrollment_idx(Long enrollmentIdx);
}
