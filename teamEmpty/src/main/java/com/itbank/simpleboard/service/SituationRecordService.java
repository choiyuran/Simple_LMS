package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.SituationRecord;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.repository.student.SituationRecordRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SituationRecordService {
    private final SituationRecordRepository situationRecordRepository;
    private final StudentRepository studentRepository;

    public SituationRecord findFirstRecordByIdOrderByDesc(Long studentIdx){

        Student student = studentRepository.findById(studentIdx).isPresent() ? studentRepository.findById(studentIdx).get() : null;
        return situationRecordRepository.findTopByStudentOrderByIdxDesc(student).orElseGet(() -> null);

    }
}
