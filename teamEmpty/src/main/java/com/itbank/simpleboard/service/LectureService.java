package com.itbank.simpleboard.service;

import com.itbank.simpleboard.component.FileComponent;
import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.QLecture;
import com.itbank.simpleboard.repository.student.LectureRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

    private final JPAQueryFactory queryFactory;
    QLecture lecture = QLecture.lecture;
    public List<LectureDto> selectAll() {
        List<LectureDto> dtos = lectureRepository.getLectureDtos();
        return dtos;
    }

    public List<LectureDto> selectAll(String searchType, String keyword) {
        List<LectureDto> lectureDtoList = lectureRepository.getLectureListDtos(searchType,keyword);
        return lectureDtoList;
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
