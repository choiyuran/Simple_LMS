package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.LectureRoom;
import com.itbank.simpleboard.repository.LectureRoomRepository;
import com.itbank.simpleboard.repository.manager.CollegeRepository;
import com.itbank.simpleboard.repository.student.LectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LectureRoomService {
    private  final LectureRoomRepository lectureRoomRepository;
    private final CollegeRepository collegeRepository;

    public List<LectureRoom> getLectureroomsByCollege(Long collegeIdx) {
        College college = collegeRepository.findById(collegeIdx).get();
        List<LectureRoom> lectureRoom = lectureRoomRepository.findByCollege(college);

        log.info(lectureRoom.toString());
        return lectureRoom;
    }
}
