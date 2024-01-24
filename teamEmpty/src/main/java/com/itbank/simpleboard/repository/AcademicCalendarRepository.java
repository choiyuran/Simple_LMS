package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.dto.AcademicCalendarDto;
import com.itbank.simpleboard.entity.AcademicCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicCalendarRepository extends JpaRepository<AcademicCalendar, Long> {
}
