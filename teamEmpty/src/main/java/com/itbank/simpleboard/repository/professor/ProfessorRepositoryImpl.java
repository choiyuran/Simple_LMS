package com.itbank.simpleboard.repository.professor;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class ProfessorRepositoryImpl implements ProfessorRepositoryCustom {
    private final JPAQueryFactory QueryFacotry;

    public ProfessorRepositoryImpl(EntityManager em) {
        this.QueryFacotry = new JPAQueryFactory(em);
    }
}
