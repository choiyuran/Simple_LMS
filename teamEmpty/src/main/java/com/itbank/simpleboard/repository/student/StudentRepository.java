package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(User User);
}
