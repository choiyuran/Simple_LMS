package com.itbank.simpleboard.service;

import com.itbank.simpleboard.component.FileComponent;
import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.repository.student.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final FileComponent fileComponent;

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

    @Transactional
    public int planUpload(MultipartFile plan, Long lectureIdx) {
        int row = 0;
        String syllabus = fileComponent.upload(plan, "syllabus");

        if (syllabus != null) {
            Optional<Lecture> optionalLecture = lectureRepository.findById(lectureIdx);

            if (optionalLecture.isPresent()) {
                Lecture lecture = optionalLecture.get();
                lecture.setPlan(syllabus);
                lectureRepository.save(lecture);

                row = 1;
            }
        }

        return row;
    }
}
