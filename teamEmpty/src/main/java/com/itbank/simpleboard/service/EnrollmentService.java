package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.repository.EnrollmentRepository;
import com.itbank.simpleboard.repository.student.LectureRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    public Enrollment save(Long studentIdx, Long lectureIdx) {
        Student student = studentRepository.findById(studentIdx).get();
        Lecture lecture = lectureRepository.findById(lectureIdx).get();
        Enrollment enrollment = new Enrollment(student, lecture);
        return enrollmentRepository.save(enrollment);
    }
}
