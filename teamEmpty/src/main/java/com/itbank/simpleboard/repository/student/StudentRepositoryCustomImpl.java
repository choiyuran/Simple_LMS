package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jdk.jfr.Registered;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;

import static com.itbank.simpleboard.entity.QLecture.lecture;
import static com.itbank.simpleboard.entity.QEnrollment.enrollment;
import static com.itbank.simpleboard.entity.QMajor.major;
import static com.itbank.simpleboard.entity.QPayments.payments;
import static com.itbank.simpleboard.entity.QScholarship.scholarship;
import static com.itbank.simpleboard.entity.QScholarship_Award.scholarship_Award;
import static com.itbank.simpleboard.entity.QStudent.student;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public StudentRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<GradeLectureDto> getLectureDtoList(GradeSearchConditionDto condition) {
        BooleanExpression studentIdxCondition = condition.getStudentIdx() != null ?
                enrollment.student.idx.eq(condition.getStudentIdx()) : null;

        BooleanExpression semesterCondition = condition.getSemester() != null ?
                enrollment.lecture.semester.eq(condition.getSemester()) : null;

        return queryFactory
                .select(new QGradeLectureDto(
                                enrollment.lecture.idx,
                                enrollment.lecture.name,
                                enrollment.lecture.credit,
                                enrollment.lecture.type.stringValue(),
                                enrollment.lecture.semester,
                                enrollment.lecture.grade,
                                QGrade.grade.score,
                                QUser.user.user_name,
                                QMajor.major.name
                        )
                ).from(enrollment)
                .innerJoin(QMajor.major).on(enrollment.lecture.major.eq(QMajor.major))
                .innerJoin(QProfessor.professor).on(enrollment.lecture.professor.eq(QProfessor.professor))
                .innerJoin(QUser.user).on(QProfessor.professor.user.eq(QUser.user))
                .innerJoin(QGrade.grade).on(QGrade.grade.student.eq(enrollment.student).and(QGrade.grade.lecture.eq(enrollment.lecture)))
                .where(
                        studentIdxCondition,
                        semesterCondition
                ).fetch();
    }

    @Override
    public List<TuitionDto> getTuitionData(Long idx) {
        return queryFactory
                .select(
                        Projections.constructor(
                                TuitionDto.class,
                                major.name,
                                student.student_num,
                                payments.date,
                                major.tuition,
                                scholarship.price
                        )
                )
                .from(payments)
                .join(student).on(payments.student.eq(student))
                .join(major).on(student.major.eq(major))
                .leftJoin(scholarship_Award).on(scholarship_Award.student.eq(student))
                .leftJoin(scholarship).on(scholarship_Award.scholarship.eq(scholarship))
                .where(student.idx.eq(idx))
                .fetch();
    }

    @Override
    public List<StudentListDto> selectAllStudent(HashMap<String, Object> map) {
        Long majorIdx = (Long)map.get("major_idx");
        String name = (String)map.get("name");

        BooleanBuilder builder = new BooleanBuilder();
        if(majorIdx != null) {
            builder.and(QStudent.student.major.idx.eq(majorIdx));
        }
        if(name != null && !name.isEmpty()) {
            builder.and(QUser.user.user_name.contains(name));
        }
        return queryFactory
                .select(new QStudentListDto(
                        QStudent.student.idx,
                        QStudent.student.student_num,
                        QUser.user.user_name,
                        QMajor.major.idx,
                        QMajor.major.name,
                        QUser.user.pnum,
                        QUser.user.user_id,
                        QStudent.student.enteranceDate,
                        QUser.user.address,
                        QUser.user.email
                )).from(QStudent.student)
                .join(QStudent.student.user, QUser.user)
                .join(QStudent.student.major, QMajor.major)
                .where(builder)
                .fetch();
    }

    @Override
    public StudentListDto selectOndeStudent(Long idx) {
        return queryFactory
                .select(new QStudentListDto(
                        QStudent.student.idx,
                        QStudent.student.student_num,
                        QUser.user.user_name,
                        QMajor.major.idx,
                        QMajor.major.name,
                        QUser.user.pnum,
                        QUser.user.user_id,
                        QStudent.student.enteranceDate,
                        QUser.user.address,
                        QUser.user.email
                )).from(QStudent.student)
                .join(QStudent.student.user, QUser.user)
                .join(QStudent.student.major, QMajor.major)
                .where(QStudent.student.idx.eq(idx))
                .fetchOne();
    }
}
