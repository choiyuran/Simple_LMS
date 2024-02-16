package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.entity.SituationRecord;
import com.itbank.simpleboard.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SituationRecordRepository extends JpaRepository<SituationRecord, Long> {
    List<SituationRecord> findAllByStudent(Student student);



}