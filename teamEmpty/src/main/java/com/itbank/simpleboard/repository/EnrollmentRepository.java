package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
