package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.*;
import com.itbank.simpleboard.repository.student.EnrollmentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>, EnrollmentRepositoryCustom {
    Optional<Enrollment> findByStudentAndLecture(Student student, Lecture lecture);

    List<Enrollment> findByStudent(Student student);
}
