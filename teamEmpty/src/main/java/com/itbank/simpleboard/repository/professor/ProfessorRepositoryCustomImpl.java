package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.itbank.simpleboard.entity.QLectureRoom;
import com.itbank.simpleboard.entity.QMajor;
import com.itbank.simpleboard.entity.QProfessor;
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
    public List<ProfessorLectureDto> getLectureDtoList(LectureSearchConditionDto condition) {
        return queryFactory
                .select(new QProfessorLectureDto(
                        lecture.idx,
                        lecture.name,
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

    @Override
    public ProfessorLectureDto getLectureDto(Long idx) {
        return queryFactory
                .select(new QProfessorLectureDto(
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
                        lecture.idx.eq(idx)
                )
                .fetchOne();
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


//    public List<ProfessorUserDto> getProfessorNamesByMajor(Long majorIdx) {
//        List<Long> userIds = queryFactory
//                .select(QProfessor.professor.user.idx)
//                .from(QProfessor.professor)
//                .where(QProfessor.professor.major.idx.eq(majorIdx))
//                .fetch();
//
//        return queryFactory
//                .select(QUser.user.user_name)
//                .from(QUser.user)
//                .where(QUser.user.idx.in(userIds))
//                .fetch();

//    }

    public List<ProfessorUserDto> getProfessorNamesByMajor(Long majorIdx) {
        return queryFactory
                .select(new QProfessorUserDto(
                        QProfessor.professor.professor_idx,
                        QProfessor.professor.user.idx,
                        QProfessor.professor.hireDate,
                        QUser.user.user_name
                ))
                .from(QProfessor.professor)
                .where(QProfessor.professor.major.idx.eq(majorIdx))
                .fetch();
    }
}
