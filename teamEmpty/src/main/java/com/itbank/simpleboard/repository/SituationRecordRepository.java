package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.SituationRecord;
import com.itbank.simpleboard.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SituationRecordRepository extends JpaRepository<SituationRecord,Long> {
    Optional<SituationRecord> findTopByStudentOrderByIdxDesc(Student student);
}
