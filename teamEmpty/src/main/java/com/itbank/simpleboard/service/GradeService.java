package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Grade;
import com.itbank.simpleboard.repository.EnrollmentRepository;
import com.itbank.simpleboard.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public int save(Long enrollment_idx, String score) {
        int row = 0;
        Optional<Grade> gradeOptional = gradeRepository.findByenrollment_idx(enrollment_idx);
        if (gradeOptional.isPresent()) {
            Grade grade = gradeOptional.get();
            grade.setScore(score);
            row = 1;
        } else {
            Optional<Enrollment> enrollmentById = enrollmentRepository.findById(enrollment_idx);
            if (enrollmentById.isPresent()) {
                Enrollment enrollment = enrollmentById.get();
                Grade grade = new Grade(enrollment, score);
                gradeRepository.save(grade);
                row = 1;
            }
        }
        return row;
    }
}
