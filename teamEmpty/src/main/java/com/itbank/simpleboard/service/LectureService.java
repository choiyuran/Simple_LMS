package com.itbank.simpleboard.service;

import com.itbank.simpleboard.component.GlobalVariable;
import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.LectureRoom;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.repository.student.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final FileService fileService;
    private final GlobalVariable globalVariable;

    public Page<LectureDto> selectAll(Pageable pageable) {
        return lectureRepository.getLectureDtos(pageable,globalVariable.getGlobalSememster());
    }

    public LectureDto selectOne(Long idx){
        Lecture lecture = lectureRepository.findById(idx).orElse(null);
        LectureDto dto = new LectureDto();
        dto.setIdx(lecture.getIdx());
        dto.setName(lecture.getName());
        dto.setIntro(lecture.getIntro());
        dto.setCredit(lecture.getCredit());
        dto.setDay(lecture.getDay());
        dto.setStart(lecture.getStart());
        dto.setEnd(lecture.getEnd());
        dto.setType(lecture.getType().toString());
        dto.setMaxCount(lecture.getMaxCount());
        dto.setCurrentCount(lecture.getCurrentCount());
        dto.setSemester(lecture.getSemester());
        dto.setGrade(lecture.getGrade());
        dto.setProfessor(lecture.getProfessor().getProfessor_idx());
        dto.setPlan(lecture.getPlan());
        return dto;
    }

    public Page<LectureDto> selectAll(String searchType, String keyword, Pageable pageable) {
        return lectureRepository.getLectureListDtos(searchType,keyword, pageable);
    }

    @Transactional
    public String planUpload(MultipartFile plan, Long lectureIdx) {
        String stringPlan = null;
        if (!plan.isEmpty()) {
            Optional<Lecture> optionalLecture = lectureRepository.findById(lectureIdx);
            if (optionalLecture.isPresent()) {
                Lecture lecture = optionalLecture.get();
                if (lecture.getPlan() != null) {
                    fileService.deleteFile(lecture.getPlan(), "syllabus");
                }
                String syllabus = fileService.upload(plan, "syllabus");
                if (syllabus != null) {
                    lecture.setPlan(syllabus);
                    lectureRepository.save(lecture);
                    stringPlan = syllabus;
                }
            }
        }
        return stringPlan;
    }

}