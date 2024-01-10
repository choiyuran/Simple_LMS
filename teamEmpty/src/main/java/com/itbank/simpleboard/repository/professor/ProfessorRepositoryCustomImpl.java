package com.itbank.simpleboard.repository.professor;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ProfessorRepositoryCustomImpl implements ProfessorRepositoryCustom {
    private final JPAQueryFactory QueryFacotry;

    @Autowired
    public ProfessorRepositoryCustomImpl(EntityManager em) {
        this.QueryFacotry = new JPAQueryFactory(em);
    }
}
