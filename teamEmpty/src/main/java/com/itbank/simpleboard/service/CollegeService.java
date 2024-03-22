package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.repository.manager.CollegeRepository;

import com.itbank.simpleboard.repository.manager.CollegeRepository;
import com.itbank.simpleboard.repository.manager.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CollegeService {
    private final CollegeRepository collegeRepository;
    private final MajorRepository majorRepository;
    public List<College> getAllColleges() {
        return collegeRepository.findAll();
    }

    public College findByIdx(Long collegeIdx) {
        return collegeRepository.findById(collegeIdx).get();
    }

    public College findByMajorIdx(Long idx) {
        Major major = majorRepository.findById(idx).get();
        return collegeRepository.findById(major.getCollege().getIdx()).get();
    }
}
