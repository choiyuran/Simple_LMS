package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Situation;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.repository.student.SituationRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SituationServive {
    private final SituationRepository situationRepository;
    private final StudentRepository studentRepository;
    public Situation selectById(Long studentIdx) {
        Student student = studentRepository.findById(studentIdx).get();
        Situation situation = situationRepository.findByStudent(student).get();

        return situation;
    }
}
