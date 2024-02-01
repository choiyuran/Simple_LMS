package com.itbank.simpleboard.repository.manager;

import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollegeRepository extends JpaRepository<College, Long> {

    List<College> findByName(String collegeName);
}
