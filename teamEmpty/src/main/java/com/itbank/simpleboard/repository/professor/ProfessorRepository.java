package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long>, ProfessorRepositoryCustom {

}
