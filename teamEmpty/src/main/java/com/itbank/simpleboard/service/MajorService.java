package com.itbank.simpleboard.service;

import com.itbank.simpleboard.repository.manager.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MajorService {
    private final MajorRepository majorRepository;
}
