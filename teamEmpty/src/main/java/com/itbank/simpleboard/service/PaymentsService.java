package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.PaymentsDto;
import com.itbank.simpleboard.entity.Payments;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.repository.PaymentsRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentsService {
    private final PaymentsRepository paymentsRepository;
    private final StudentRepository studentRepository;
    @Transactional
    public Payments save(PaymentsDto dto) {
        Student student = studentRepository.findById(dto.getStudent()).orElse(null);
        Payments payments = new Payments(student,dto.getSemester());
        return paymentsRepository.save(payments);
    }
}
