package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.dto.RegisterlectureDto;
import com.itbank.simpleboard.entity.Grade;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.LectureRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface LectureRepository extends JpaRepository<Lecture, Long>, LectureRepositoryCustom {


    List<Lecture> findByLectureRoom(LectureRoom lectureRoom);
}
