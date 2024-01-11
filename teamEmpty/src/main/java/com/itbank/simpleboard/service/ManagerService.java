package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.AcademicCalendar;
import com.itbank.simpleboard.repository.academicCalendar.AcademicCalendarRepository;
import com.itbank.simpleboard.repository.manager.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final AcademicCalendarRepository academicCalendarRepository;


    public List<AcademicCalendar> findAll() {
        return academicCalendarRepository.findAll();
    }
}
