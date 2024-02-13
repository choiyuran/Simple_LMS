package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.*;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jdk.jfr.Registered;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.itbank.simpleboard.entity.QLecture.lecture;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryCustomImpl implements StudentRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Autowired
    public StudentRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public List<GradeLectureDto> getLectureDtoList(GradeSearchConditionDto condition) {
        return queryFactory
                .select(new QGradeLectureDto(
                        lecture.idx,
                        lecture.name,
                        lecture.credit,
                        lecture.type.stringValue(),
                        lecture.semester,
                        lecture.grade,
                        lecture.abolition.stringValue(),
                        QGrade.grade.score,
                        lecture.professor.professor_idx,
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
                        QMajor.major.abolition.eq(YesOrNo.valueOf("N")),
                        student_idxEq(condition.getStudent_idx()),
                        nameContain(condition.getName()),
                        typeEq(condition.getType()),
                        yearEq(condition.getYear()),
                        semesterEq(condition.getSemester()),
                        gradeEq(condition.getGrade()),
                        professorContain(condition.getProfessor()),
                        majorEq(condition.getMajor()),
                        professor_idxEq(condition.getProfessor_idx())
                )
                .fetch();
    }

    private Predicate student_idxEq(Long studentIdx) {
        return studentIdx != null ? QGrade.grade.student.idx.eq(studentIdx) : QGrade.grade.student.idx.isNull();
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

    private BooleanExpression professor_idxEq(Long professorIdx) {
        return professorIdx != null ? QProfessor.professor.professor_idx.eq(professorIdx) : null;
    }
}
