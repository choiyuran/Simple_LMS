package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;

import static com.itbank.simpleboard.entity.QLecture.lecture;

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
                        lecture.abolition.stringValue(),
                        lecture.professor.professor_idx,
                        QUser.user.user_name.as("professor_name"),
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
                        nameOrProfessorContain(condition.getName()),  // 수정된 부분
                        typeEq(condition.getType()),
                        yearEq(condition.getYear()),
                        semesterEq(condition.getSemester()),
                        gradeEq(condition.getGrade()),
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
                        lecture.abolition.stringValue(),
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

    private BooleanExpression nameOrProfessorContain(String name) {
        if (StringUtils.hasText(name)) {
            return lecture.name.contains(name)
                    .or(QUser.user.user_name.contains(name));
        }
        return null;
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

    private BooleanExpression majorEq(String major) {
        return StringUtils.hasText(major) ? QMajor.major.name.eq(major) : null;
    }

    private BooleanExpression professor_idxEq(Long professorIdx) {
        return professorIdx != null ? QProfessor.professor.professor_idx.eq(professorIdx) : null;
    }

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

    @Override
    public List<EvaluateFormDto> getMyEvaluation(Long idx) {
        return queryFactory
                .select(new QEvaluateFormDto(
                        QEvaluation.evaluation.idx,
                        QEvaluation.evaluation.q1,
                        QEvaluation.evaluation.q2,
                        QEvaluation.evaluation.q3,
                        QEvaluation.evaluation.q4,
                        QEvaluation.evaluation.q5
                ))
                .from(QEvaluation.evaluation)
                .innerJoin(QEnrollment.enrollment).on(QEnrollment.enrollment.lecture.idx.eq(idx))
                .fetch();
    }

    @Override
    public List<EnrollmentDto> getEnrollmentList(Long lectureIdx) {
        return queryFactory
                .select(Projections.fields(EnrollmentDto.class,
                        QEnrollment.enrollment.student.idx.as("student_idx"),
                        QEnrollment.enrollment.student.student_num.as("student_num"),
                        QEnrollment.enrollment.student.user.user_name.as("student_name"),
                        QEnrollment.enrollment.lecture.idx.as("lecture_idx"),
                        QEnrollment.enrollment.lecture.name.as("lecture_name"),
                        ExpressionUtils
                                .as(JPAExpressions
                                        .select(QGrade.grade.idx)
                                        .from(QGrade.grade)
                                        .where(
                                                QGrade.grade.student.eq(QEnrollment.enrollment.student)
                                                        .and(QGrade.grade.lecture.eq(QEnrollment.enrollment.lecture))
                                        )
                                        .exists(), "hasGrade")))
                .from(QEnrollment.enrollment)
                .where(QEnrollment.enrollment.lecture.idx.eq(lectureIdx))
                .fetch();
        return null;
    }

    @Override
    public List<ProfessorListDto> searchByMajorAndProfessorAndLeave(HashMap<String, Object> map) {
        Long majorIdx = (Long)map.get("major_idx");
        String name = (String)map.get("name");
        BooleanBuilder builder = new BooleanBuilder();

        if (majorIdx != null) {
            builder.and(QProfessor.professor.major.idx.eq(majorIdx));
        }
        if (name != null && !name.isEmpty()) {
            builder.and(QUser.user.user_name.like("%"+name+"%"));
        }
        builder.and(QProfessor.professor.leave.eq(YesOrNo.valueOf("N")));

        return queryFactory
                .select(Projections.fields(EnrollmentDto.class,
                        QEnrollment.enrollment.idx.as("idx"),
                        QEnrollment.enrollment.student.idx.as("student_idx"),
                        QEnrollment.enrollment.student.student_num.as("student_num"),
                        QEnrollment.enrollment.student.user.user_name.as("student_name"),
                        QEnrollment.enrollment.lecture.idx.as("lecture_idx"),
                        QEnrollment.enrollment.lecture.name.as("lecture_name"),
                        ExpressionUtils
                                .as(JPAExpressions
                                        .select(QGrade.grade.idx)
                                        .from(QGrade.grade)
                                        .where(
                                                QGrade.grade.enrollment.student.eq(QEnrollment.enrollment.student)
                                                        .and(QGrade.grade.enrollment.lecture.eq(QEnrollment.enrollment.lecture))
                                        )
                                        .exists(), "hasGrade")))
                .from(QEnrollment.enrollment)
                .where(QEnrollment.enrollment.lecture.idx.eq(lectureIdx))
                .orderBy(QEnrollment.enrollment.student.idx.asc())
                .select(new QProfessorListDto(
                        QProfessor.professor.professor_idx,
                        QProfessor.professor.professor_img,
                        QProfessor.professor.hireDate,
                        QUser.user.user_name,
                        QUser.user.user_id,
                        QUser.user.address,
                        QUser.user.pnum,
                        QUser.user.email,
                        QMajor.major.idx,
                        QMajor.major.name
                )).from(QProfessor.professor)
                .join(QProfessor.professor.user, QUser.user)
                .join(QProfessor.professor.major, QMajor.major)
                .where(builder)
                .fetch();
    }


    @Override
    public List<ProfessorListDto> searchByMajorAndProfessor(HashMap<String, Object> map) {
        Long majorIdx = (Long)map.get("major_idx");
        String name = (String)map.get("name");

        BooleanBuilder builder = new BooleanBuilder();

        if (majorIdx != null) {
            builder.and(QProfessor.professor.major.idx.eq(majorIdx));
        }
        if (name != null && !name.isEmpty()) {
            builder.and(QUser.user.user_name.like("%"+name+"%"));
        }

        return queryFactory
                .select(new QProfessorListDto(
                        QProfessor.professor.professor_idx,
                        QProfessor.professor.professor_img,
                        QProfessor.professor.hireDate,
                        QUser.user.user_name,
                        QUser.user.user_id,
                        QUser.user.address,
                        QUser.user.pnum,
                        QUser.user.email,
                        QMajor.major.idx,
                        QMajor.major.name
                )).from(QProfessor.professor)
                .join(QProfessor.professor.user, QUser.user)
                .join(QProfessor.professor.major, QMajor.major)
                .where(builder)
                .fetch();
    }

    @Override
    public ProfessorListDto selectOneProfessor(Long idx) {
        return queryFactory
                .select(new QProfessorListDto(
                        QProfessor.professor.professor_idx,
                        QProfessor.professor.professor_img,
                        QProfessor.professor.hireDate,
                        QUser.user.user_name,
                        QUser.user.user_id,
                        QUser.user.address,
                        QUser.user.pnum,
                        QUser.user.email,
                        QMajor.major.idx,
                        QMajor.major.name
                )).from(QProfessor.professor)
                .join(QProfessor.professor.user, QUser.user)
                .join(QProfessor.professor.major, QMajor.major)
                .where(QProfessor.professor.professor_idx.eq(idx))
                .fetchOne();
    }
}
