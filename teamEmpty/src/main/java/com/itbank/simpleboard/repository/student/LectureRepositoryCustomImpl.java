package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.dto.QLectureDto;
import com.itbank.simpleboard.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.itbank.simpleboard.entity.QLecture.*;

@Repository
public class LectureRepositoryCustomImpl implements LectureRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public LectureRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<LectureDto> getLectureListDtos(String searchType, String keyword, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if ("professor".equals(searchType)) {
            builder.and(lecture.professor.user.user_name.contains(keyword));
        } else if ("subject".equals(searchType)) {
            builder.and(lecture.name.contains(keyword));
        } else if ("grade".equals(searchType)) {
            builder.and(lecture.grade.eq(Integer.parseInt(keyword)));
        } else {
            if (isNumeric(keyword)) {
                builder.or(lecture.name.contains(keyword))
                        .or(lecture.professor.user.user_name.contains(keyword))
                        .or(lecture.grade.eq(Integer.parseInt(keyword)));
            } else {
                builder.or(lecture.name.contains(keyword))
                        .or(lecture.professor.user.user_name.contains(keyword));
            }
        }

        QueryResults<Lecture> results = queryFactory.selectFrom(lecture)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<LectureDto> content = results.getResults().stream().map(lecture1 -> {
            LectureDto dto = new LectureDto();
            dto.setIdx(lecture1.getIdx());
            dto.setStart(lecture1.getStart());
            dto.setEnd(lecture1.getEnd());
            dto.setIdx(lecture1.getIdx());
            dto.setType(lecture1.getType().toString());
            dto.setPlan(lecture1.getPlan());
            dto.setName(lecture1.getName());
            dto.setDay(lecture1.getDay());
            dto.setGrade(lecture1.getGrade());
            dto.setCredit(lecture1.getCredit());
            dto.setCurrentCount(lecture1.getCurrentCount());
            dto.setMaxCount(lecture1.getMaxCount());
            dto.setSemester(lecture1.getSemester());
            dto.setProfessor_name(lecture1.getProfessor().getUser().getUser_name());
            dto.setProfessor(lecture1.getProfessor().getProfessor_idx());
            dto.setIntro(lecture1.getIntro());
            dto.setVisible(lecture1.getVisible().toString());
            dto.setAbolition(lecture1.getAbolition().toString());

            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(content, pageable, results.getTotal());
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Page<LectureDto> getLectureDtos(Pageable pageable) {
        QueryResults<LectureDto> results = queryFactory
                .select(new QLectureDto(
                        lecture.idx,
                        lecture.name,
                        lecture.intro,
                        lecture.credit,
                        lecture.day,
                        lecture.start,
                        lecture.end,
                        lecture.type.stringValue(),
                        lecture.maxCount,
                        lecture.currentCount,
                        lecture.semester,
                        lecture.grade,
                        lecture.professor.professor_idx,
                        lecture.plan,
                        QUser.user.user_name
                ))
                .from(lecture)
                .innerJoin(QProfessor.professor).on(lecture.professor.eq(QProfessor.professor))
                .innerJoin(QUser.user).on(QProfessor.professor.user.eq(QUser.user))
                .orderBy(lecture.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

}