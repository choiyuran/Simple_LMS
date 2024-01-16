package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.entity.Grade;
import com.itbank.simpleboard.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long>, LectureRepositoryCustom {

}
