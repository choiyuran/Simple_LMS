package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.dto.LectureSearchCondition;
import com.itbank.simpleboard.dto.QLectureDto;
import com.itbank.simpleboard.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.itbank.simpleboard.entity.QLecture.*;

@Repository
public class ProfessorRepositoryCustomImpl implements ProfessorRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public ProfessorRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<LectureDto> getLectureDtoList(LectureSearchCondition condition) {
        return queryFactory
                .select(new QLectureDto(
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
                        QUser.user.user_name,
                        lecture.plan,
                        QMajor.major.name,
                        QCollege.college.location,
                        QLectureRoom.lectureRoom.room
                ))
                .from(lecture)
                .innerJoin(QProfessor.professor).on(lecture.professor.eq(QProfessor.professor))
                .innerJoin(QUser.user).on(QProfessor.professor.user.eq(QUser.user))
                .innerJoin(lecture.major, QMajor.major)
                .innerJoin(lecture.lectureRoom, QLectureRoom.lectureRoom)
                .innerJoin(QCollege.college).on(QLectureRoom.lectureRoom.college.eq(QCollege.college))
                .where(
                        nameContain(condition.getName()),
                        typeEq(condition.getType()),
                        yearEq(condition.getYear()),
                        semesterEq(condition.getSemester()),
                        gradeEq(condition.getGrade()),
                        professorContain(condition.getProfessor()),
                        majorEq(condition.getMajor())
                )
                .fetch();
    }

    private BooleanExpression nameContain(String name) {
        return StringUtils.hasText(name) ? lecture.name.contains(name) : null;
    }

    private BooleanExpression typeEq(String type) {
        return StringUtils.hasText(type) ? lecture.type.eq(Lecture_Type.valueOf(type)) : null;
    }

    private BooleanExpression yearEq(Integer year) {
        return year != null ? lecture.semester.contains(String.valueOf(year)) : null;
    }

    private BooleanExpression semesterEq(Integer semester) {
        return semester != null ? lecture.semester.contains(semester + "학기") : null;
    }

    private BooleanExpression gradeEq(Integer grade) {
        return grade != null ? lecture.grade.eq(grade) : null;
    }

    private BooleanExpression professorContain(String professor) {
        return StringUtils.hasText(professor) ? QUser.user.user_name.contains(professor) : null;
    }

    private BooleanExpression majorEq(String major) {
        return StringUtils.hasText(major) ? QMajor.major.name.eq(major) : null;
    }
}
