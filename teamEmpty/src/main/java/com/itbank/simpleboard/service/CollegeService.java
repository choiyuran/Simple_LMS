package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.repository.manager.CollegeRepository;

import com.itbank.simpleboard.repository.manager.CollegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CollegeService {
    private final CollegeRepository collegeRepository;

    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }
}
