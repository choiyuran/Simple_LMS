package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Scholarship_Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScholarshipAwardRepository extends JpaRepository<Scholarship_Award, Long> {
}
