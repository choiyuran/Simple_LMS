package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.QStudentListDto;
import com.itbank.simpleboard.dto.StudentListDto;
import com.itbank.simpleboard.dto.TuitionDto;
import com.itbank.simpleboard.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.HashMap;
import java.util.List;

import static com.itbank.simpleboard.entity.QMajor.major;
import static com.itbank.simpleboard.entity.QPayments.payments;
import static com.itbank.simpleboard.entity.QScholarship.scholarship;
import static com.itbank.simpleboard.entity.QStudent.student;
import static com.itbank.simpleboard.entity.QScholarship_Award.scholarship_Award;

public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public StudentRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<TuitionDto> getTuitionData(Long idx) {
        return jpaQueryFactory
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
        return jpaQueryFactory
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
        return jpaQueryFactory
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

