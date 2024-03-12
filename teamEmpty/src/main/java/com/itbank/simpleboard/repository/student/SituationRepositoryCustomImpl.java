package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.QSituationStuDto;
import com.itbank.simpleboard.dto.SituationStuDto;
import com.itbank.simpleboard.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SituationRepositoryCustomImpl implements SituationRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public SituationRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<SituationStuDto> findAllSituationStu(HashMap<String, Object> map, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        String name = (String)map.get("name");
        String status = (String)map.get("status");
        Boolean waiting = (Boolean)map.get("waiting");

        if (name != null && !name.isEmpty()) {
            builder.and(QUser.user.user_name.like("%" + name + "%"));
        }
        if (status != null && !status.isEmpty()) {
            builder.and(QSituation.situation.student_status.stringValue().contains(status));
        }
        if (waiting != null && waiting) {
            builder.and(QSituation.situation.student_status.stringValue().contains("신청"));
        }

        QueryResults<SituationStuDto> results = queryFactory
                .select(new QSituationStuDto(
                        QSituation.situation.idx,
                        QStudent.student.student_num,
                        QUser.user.user_name, // name
                        QMajor.major.name, // major
                        QSituation.situation.start_date, // start_date
                        QSituation.situation.end_date, // end_date
                        QSituation.situation.student_status, // status
                        QStudent.student.idx,   // student_idx
                        QMajor.major.idx,   // major_idx
                        QUser.user.idx  // user_idx
                ))
                .from(QSituation.situation)
                .join(QSituation.situation.student, QStudent.student)
                .join(QStudent.student.user, QUser.user)
                .join(QStudent.student.major, QMajor.major)
                .where(builder) // 동적으로 생성된 where 절 사용
                .orderBy(QSituation.situation.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public SituationStuDto findOneSituation(Long idx) {
        return queryFactory
                .select(new QSituationStuDto(
                        QSituation.situation.idx,
                        QStudent.student.student_num,
                        QUser.user.user_name, // name
                        QMajor.major.name, // major
                        QSituation.situation.start_date, // start_date
                        QSituation.situation.end_date, // end_date
                        QSituation.situation.student_status, // status
                        QStudent.student.idx,   // student_idx
                        QMajor.major.idx,   // major_idx
                        QUser.user.idx  // user_idx
                ))
                .from(QSituation.situation)
                .join(QSituation.situation.student, QStudent.student)
                .join(QStudent.student.user, QUser.user)
                .join(QStudent.student.major, QMajor.major)
                .where(QSituation.situation.idx.eq(idx))
                .fetchOne();
    }
}
