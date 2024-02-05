package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.Scholarship;
import com.itbank.simpleboard.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {

    @Query("SELECT SUM(sa.scholarship.price) FROM Scholarship_Award sa WHERE sa.student = :student")
    Integer getTotal(@Param("student") Student student);
}
