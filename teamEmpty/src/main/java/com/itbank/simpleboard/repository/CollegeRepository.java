package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.AcademicCalendar;
import com.itbank.simpleboard.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
}
