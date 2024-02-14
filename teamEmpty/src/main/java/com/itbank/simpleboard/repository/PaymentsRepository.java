package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.Payments;
import com.itbank.simpleboard.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    Payments findByStudentAndSemester(Student student, String semester);

    List<Payments> findByStudentOrderByIdxDesc(Student student);

    Payments findTopByOrderByIdxDesc();
}
