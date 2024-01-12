package com.itbank.simpleboard.service;

import com.itbank.simpleboard.repository.LectureRepository;
import com.itbank.simpleboard.repository.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentsService {
    private final PaymentsRepository paymentsRepository;
}
