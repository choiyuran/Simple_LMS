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
        List<Lecture> lectureList = lectureRepository.findAll();
        List<LectureDto> dtos = new ArrayList<>();
        for (Lecture lecture : lectureList) {
            LectureDto dto = new LectureDto(lecture.getIdx(), lecture.getName(), lecture.getIntro(), lecture.getCredit(), lecture.getDay(), lecture.getStart(), lecture.getEnd(), lecture.getType().toString(), lecture.getMaxCount(), lecture.getCurrentCount(), lecture.getSemester(), lecture.getGrade(), lecture.getProfessor(), lecture.getPlan(), lecture.getMajor().getIdx(), lecture.getLectureRoom().getIdx(), lecture.getVisible().toString());
            dtos.add(dto);
        }
        System.out.println("dtos : " + dtos);
        return dtos;
    }

    public List<LectureDto> selectAll(String searchType, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        List<LectureDto> lectureDtoList = new ArrayList<>();

        if ("professor".equals(searchType)) {
            builder.and(lecture.professor.user.user_name.contains(keyword)); // 수정: user_name 대신 userName 사용
        } else if ("subject".equals(searchType)) {
            builder.and(lecture.name.contains(keyword));
        } else if ("grade".equals(searchType)) {
            builder.and(lecture.grade.eq(Integer.parseInt(keyword)));
        } else {
            builder.or(lecture.name.contains(keyword))
                    .or(lecture.professor.user.user_name.contains(keyword)) // 수정: user_name 대신 userName 사용
                    .or(lecture.grade.eq(Integer.parseInt(keyword)));
        }

        List<Lecture> lectureList = queryFactory.selectFrom(lecture)
                .where(builder) // 수정: 조건을 builder로 설정
                .fetch();

        // Convert Lecture to LectureDto and return
        for (Lecture lecture1 : lectureList) {
            LectureDto dto = new LectureDto();
            dto.setIdx(lecture1.getIdx());
            dto.setLectureRoom(lecture1.getLectureRoom().getIdx());
            dto.setProfessor(lecture1.getProfessor());
            dto.setMajor(lecture1.getMajor().getIdx());
            dto.setVisible(lecture1.getVisible().toString());
            dto.setStart(lecture1.getStart());
            dto.setEnd(lecture1.getEnd());
            dto.setIdx(lecture1.getIdx());
            dto.setType(lecture1.getType().toString());
            dto.setPlan(lecture1.getPlan());
            dto.setName(lecture1.getName());
            dto.setDay(lecture1.getDay());
            dto.setGrade(lecture1.getGrade());
            dto.setIntro(lecture1.getIntro());
            dto.setCredit(lecture1.getCredit());
            dto.setCurrentCount(lecture1.getCurrentCount());
            dto.setMaxCount(lecture1.getMaxCount());
            dto.setSemester(lecture1.getSemester());

            lectureDtoList.add(dto);
        }

        return lectureDtoList;
    }


    @Transactional
    public String planUpload(MultipartFile plan, Long lectureIdx) {
        String stringPlan = null;
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
        return stringPlan;
    }
}
