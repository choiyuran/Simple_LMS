package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Grade;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.repository.GradeRepository;
import com.itbank.simpleboard.repository.student.LectureRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    public int save(Long studentIdx, Long lectureIdx, String score) {
        int row = 0;
        Optional<Student> studentById = studentRepository.findById(studentIdx);
        Optional<Lecture> lectureById = lectureRepository.findById(lectureIdx);
        if (studentById.isPresent() && lectureById.isPresent()) {
            Student student = studentById.get();
            Lecture lecture = lectureById.get();
//            Grade grade = new Grade(student, lecture, score);
//            gradeRepository.save(grade);
            row = 1;
        }
        return row;
    }
}
