package com.itbank.simpleboard.service;

import com.itbank.simpleboard.repository.AcademicCalendarRepository;
import com.itbank.simpleboard.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
}
