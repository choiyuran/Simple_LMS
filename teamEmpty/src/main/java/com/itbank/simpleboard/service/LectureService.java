package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.repository.student.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    public List<LectureDto> selectAll() {
        List<Lecture> lectureList = lectureRepository.findAll();
        List<LectureDto> dtos = new ArrayList<>();
        for(Lecture lecture : lectureList){
            LectureDto dto = new LectureDto(lecture.getIdx(), lecture.getName(), lecture.getIntro(),lecture.getCredit(), lecture.getDay(), lecture.getStart(), lecture.getEnd(), lecture.getType().toString(), lecture.getMaxCount(), lecture.getCurrentCount(), lecture.getSemester(), lecture.getGrade(),lecture.getProfessor(),lecture.getPlan(),lecture.getMajor().getIdx(),lecture.getLectureRoom().getIdx(), lecture.getVisible().toString());
            dtos.add(dto);
        }
        System.out.println("dtos : " + dtos);
        return dtos;
    }

}
