package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.dto.ScholarshipDto;
import com.itbank.simpleboard.entity.Scholarship;
import com.itbank.simpleboard.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, Long>, ScholarshipRepositoryCustom {

    @Query("SELECT SUM(sa.scholarship.price) FROM Scholarship_Award sa WHERE sa.student = :student and sa.scholarship.year=2024 and (sa.scholarship.quarter = 1 or sa.scholarship.quarter = 2)")
    Integer getTotal(@Param("student") Student student);

    List<Scholarship> findByYearContaining(String year);

    List<Scholarship> findByYear(Integer year);



}
