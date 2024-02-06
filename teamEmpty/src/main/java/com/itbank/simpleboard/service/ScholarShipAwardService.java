package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Scholarship;
import com.itbank.simpleboard.entity.Scholarship_Award;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.repository.PaymentsRepository;
import com.itbank.simpleboard.repository.ScholarshipAwardRepository;
import com.itbank.simpleboard.repository.ScholarshipRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScholarShipAwardService {
    private final ScholarshipAwardRepository scholarshipAwardRepository;
    private final ScholarshipRepository scholarshipRepository;
    private final StudentRepository studentRepository;

    public Integer getTotal(Long idx) {
        Student student = studentRepository.findById(idx).orElse(null);
        return student == null  ? 0 : scholarshipRepository.getTotal(student);
    }
}
