package com.itbank.simpleboard.service;

import com.itbank.simpleboard.component.FileComponent;
import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.QLecture;
import com.itbank.simpleboard.repository.student.LectureRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final FileComponent fileComponent;

    public List<LectureDto> selectAll() {
        return lectureRepository.getLectureDtos();
    }

    public List<LectureDto> selectAll(String searchType, String keyword) {
        return lectureRepository.getLectureListDtos(searchType, keyword);
    }


    @Transactional
    public String planUpload(MultipartFile plan, Long lectureIdx) {
        String stringPlan = null;
        if (!plan.isEmpty()) {
            Optional<Lecture> optionalLecture = lectureRepository.findById(lectureIdx);
            if (optionalLecture.isPresent()) {
                Lecture lecture = optionalLecture.get();
                if (lecture.getPlan() != null) {
                    fileComponent.deleteFile(lecture.getPlan(), "syllabus");
                }
                String syllabus = fileComponent.upload(plan, "syllabus");
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