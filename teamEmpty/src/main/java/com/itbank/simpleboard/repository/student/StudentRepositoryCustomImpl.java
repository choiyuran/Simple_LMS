package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.TuitionDto;
import com.itbank.simpleboard.entity.QScholarship_Award;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

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
}

