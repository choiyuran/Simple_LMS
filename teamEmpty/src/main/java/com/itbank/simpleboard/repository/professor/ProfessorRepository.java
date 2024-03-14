package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long>, ProfessorRepositoryCustom {
    List<Professor> findAllByMajor(Major major);

    List<Professor> findAllByMajorName(String majorName);
}
