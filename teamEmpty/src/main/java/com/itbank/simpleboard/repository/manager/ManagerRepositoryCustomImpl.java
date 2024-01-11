package com.itbank.simpleboard.repository.manager;

import com.itbank.simpleboard.repository.professor.ProfessorRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ManagerRepositoryCustomImpl implements ManagerRepositoryCustom {
    private final JPAQueryFactory QueryFacotry;

    @Autowired
    public ManagerRepositoryCustomImpl(EntityManager em) {
        this.QueryFacotry = new JPAQueryFactory(em);
    }
}
