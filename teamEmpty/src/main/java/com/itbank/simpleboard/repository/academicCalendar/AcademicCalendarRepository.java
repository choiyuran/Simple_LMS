package com.itbank.simpleboard.repository.academicCalendar;

import com.itbank.simpleboard.entity.AcademicCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicCalendarRepository extends JpaRepository<AcademicCalendar, Long> {
}
