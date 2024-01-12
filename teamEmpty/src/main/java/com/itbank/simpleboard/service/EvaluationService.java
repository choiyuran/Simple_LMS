package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Evaluation;
import com.itbank.simpleboard.repository.EnrollmentRepository;
import com.itbank.simpleboard.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
}
