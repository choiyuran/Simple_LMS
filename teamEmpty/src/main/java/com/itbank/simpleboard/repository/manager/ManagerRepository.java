package com.itbank.simpleboard.repository.manager;

import com.itbank.simpleboard.dto.ProfessorListDto;
import com.itbank.simpleboard.entity.Manager;
import com.itbank.simpleboard.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long>,ManagerRepositoryCustom {
}
