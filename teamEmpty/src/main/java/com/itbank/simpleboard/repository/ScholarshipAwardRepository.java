package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Scholarship;
import com.itbank.simpleboard.entity.Scholarship_Award;
import com.itbank.simpleboard.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScholarshipAwardRepository extends JpaRepository<Scholarship_Award, Long> {
    List<Scholarship_Award> findByStudent(Student student);

}
