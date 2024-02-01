package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.LectureRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRoomRepository extends JpaRepository<LectureRoom, Long> {
    List<LectureRoom> findByCollege(College college);
}
