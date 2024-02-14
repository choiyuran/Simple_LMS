package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.ProfessorUserDto;
import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.entity.Professor;
import com.itbank.simpleboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long>, ProfessorRepositoryCustom {
    Professor findByUser(User user);

    @Query("SELECT m.name FROM Major m")
    List<String> findAllMajorNames();

    List<Professor> findAllByMajor(Major major);


    List<Professor> findAllByMajorName(String majorName);
}
