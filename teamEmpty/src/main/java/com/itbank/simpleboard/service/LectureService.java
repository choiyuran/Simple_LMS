package com.itbank.simpleboard.service;

import com.itbank.simpleboard.repository.GradeRepository;
import com.itbank.simpleboard.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
}
