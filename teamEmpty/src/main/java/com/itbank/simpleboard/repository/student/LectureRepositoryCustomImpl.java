package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.entity.Lecture;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.itbank.simpleboard.entity.QLecture.*;

@Repository
public class LectureRepositoryCustomImpl implements LectureRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public LectureRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<LectureDto> getLectureListDtos(String searchType, String keyword) {
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
            dto.setLectureRoom(lecture1.getLectureRoom());
            dto.setProfessor(lecture1.getProfessor());
            dto.setMajor(lecture1.getMajor());
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

}
