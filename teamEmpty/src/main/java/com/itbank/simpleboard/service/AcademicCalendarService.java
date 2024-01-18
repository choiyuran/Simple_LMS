package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.AcademicCalendarDto;
import com.itbank.simpleboard.entity.AcademicCalendar;
import com.itbank.simpleboard.repository.AcademicCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AcademicCalendarService {
    private final AcademicCalendarRepository academicCalendarRepository;

    // 학사 일정 조회
    public List<AcademicCalendar> findCalendarAll() {
        return academicCalendarRepository.findAll();
    }

    // 학사 일정 추가
    @Transactional
    public AcademicCalendar addCalendar(AcademicCalendarDto calendarDto) {
        // AcademicCalendarDto에서 AcademicCalendar로 변환
        AcademicCalendar academicCalendar = new AcademicCalendar();
        academicCalendar.setTitle(calendarDto.getTitle());
        academicCalendar.setStart_date(Date.valueOf(String.valueOf(calendarDto.getStart_date())));
        academicCalendar.setEnd_date(Date.valueOf(String.valueOf(calendarDto.getEnd_date())));

        // 저장
        return academicCalendarRepository.save(academicCalendar);
    }
}
