package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.entity.User;
import com.itbank.simpleboard.repository.EnrollmentRepository;
import com.itbank.simpleboard.repository.UserRepository;
import com.itbank.simpleboard.repository.student.LectureRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    
    // 수강 취소
    @Transactional
    public void cancel(Long studentIdx, Long lectureIdx) {
        Student student = studentRepository.findById(studentIdx).get();
        Lecture lecture = lectureRepository.findById(lectureIdx).get();
        lecture.setCurrentCount(lecture.getCurrentCount()-1);

        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findByStudentAndLecture(student, lecture);

        if (optionalEnrollment.isPresent()) {
            Enrollment enrollment = optionalEnrollment.get();
            enrollmentRepository.delete(enrollment);
        }

    }

    @Transactional
    public Enrollment save(Long studentIdx, Long lectureIdx) {
        Student student = studentRepository.findById(studentIdx).get();
        Lecture lecture = lectureRepository.findById(lectureIdx).get();
        Enrollment enrollment = null;
        if(lecture.getCurrentCount() < lecture.getMaxCount()){
            lecture.setCurrentCount(lecture.getCurrentCount()+1);
            enrollment = new Enrollment(student, lecture);
        }else{
            return null;
        }

        return enrollmentRepository.save(enrollment);
    }
    
    public List<Enrollment> findByStudent(Long studentIdx) {
        // 일단 페이징 안됨
        Student student = studentRepository.findById(studentIdx).get();
        List<Enrollment> enrollmentList = enrollmentRepository.findAllByStudent(student);
        return enrollmentList;
    }
}
