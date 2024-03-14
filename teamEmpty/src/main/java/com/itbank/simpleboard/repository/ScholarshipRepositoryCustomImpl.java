package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.dto.QScholarshipDto;
import com.itbank.simpleboard.dto.ScholarshipDto;
import com.itbank.simpleboard.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


@Repository
public class ScholarshipRepositoryCustomImpl implements ScholarshipRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Autowired
    public ScholarshipRepositoryCustomImpl(EntityManager em, EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(em);
        this.entityManager = entityManager;
    }
    @Override
    public Page<ScholarshipDto> findAllScholarship(Pageable pageable, ScholarshipDto dto) {
        BooleanBuilder builder = new BooleanBuilder();

        if (dto.getName() != null) {
            builder.and(QScholarship.scholarship.name.eq(dto.getName()));
        }
        if (dto.getYear() != null) {
            builder.and(QScholarship.scholarship.year.eq(Integer.valueOf(String.valueOf(dto.getYear()))));
        }
        if (dto.getQuarter() != null) {
            builder.and(QScholarship.scholarship.quarter.eq(dto.getQuarter()));
        }

        QueryResults<ScholarshipDto> results = queryFactory
                .select(new QScholarshipDto(
                        QScholarship.scholarship.idx,
                        QScholarship.scholarship.category,
                        QScholarship.scholarship.name,
                        QScholarship.scholarship.price,
                        QScholarship.scholarship.year,
                        QScholarship.scholarship.quarter
                )).from(QScholarship.scholarship)
                .where(builder)
                .orderBy(QScholarship.scholarship.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

}
