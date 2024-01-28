package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.SituationStuDto;
import com.itbank.simpleboard.entity.Situation;
import com.itbank.simpleboard.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SituationRepository extends JpaRepository<Situation, Long>, SituationRepositoryCustom {
    public Optional<Situation> findByStudent(Student student);

}
