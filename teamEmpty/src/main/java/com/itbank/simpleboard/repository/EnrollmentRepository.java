package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findAllByStudent(Student student);
    Optional<Enrollment> findByStudentAndLecture(Student student, Lecture lecture);
}
