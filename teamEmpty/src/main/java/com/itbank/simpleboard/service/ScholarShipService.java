package com.itbank.simpleboard.service;

import com.itbank.simpleboard.repository.ScholarshipAwardRepository;
import com.itbank.simpleboard.repository.ScholarshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScholarShipService {
    private final ScholarshipRepository scholarshipRepository;
}
