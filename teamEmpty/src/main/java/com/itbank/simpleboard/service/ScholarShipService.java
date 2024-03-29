package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Scholarship;
import com.itbank.simpleboard.dto.ScholarshipDto;
import com.itbank.simpleboard.entity.Scholarship;
import com.itbank.simpleboard.entity.Scholarship_Award;
import com.itbank.simpleboard.repository.ScholarshipAwardRepository;
import com.itbank.simpleboard.repository.ScholarshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScholarShipService {
    private final ScholarshipRepository scholarshipRepository;

    public List<Scholarship> findAllByContainSemester(String semester) {
        Integer year = Integer.parseInt(semester.split("년")[0]);
        System.err.println("year");
        return scholarshipRepository.findByYear(year);
    }

    @Transactional
    public Scholarship addScholarShip(ScholarshipDto dto) {
        Scholarship scholarship = new Scholarship(
                dto.getCategory(),
                dto.getName(),
                dto.getPrice(),
                dto.getYear(),
                dto.getQuarter()
        );
        return scholarshipRepository.save(scholarship);
    }
}
