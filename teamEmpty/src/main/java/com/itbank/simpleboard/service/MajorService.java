package com.itbank.simpleboard.service;


import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.repository.manager.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MajorService {
    private final MajorRepository majorRepository;

    public Integer getTuition(Long idx) {
        Major major = majorRepository.findById(idx).orElse(null);
        // 장학금 받아오기
        return major != null ? major.getTuition() : 0;
    }

    public List<Major> findById() {
        return majorRepository.findAll();
    }
}
