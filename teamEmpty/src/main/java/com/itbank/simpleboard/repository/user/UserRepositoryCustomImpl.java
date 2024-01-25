package com.itbank.simpleboard.repository.user;

import com.itbank.simpleboard.dto.MajorDto;
import com.itbank.simpleboard.dto.ProfessorDto;
import com.itbank.simpleboard.dto.UserDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.itbank.simpleboard.entity.QProfessor.professor;
import static com.itbank.simpleboard.entity.QUser.user;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public UserRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public UserDTO getUser(String userId) {
        return queryFactory
                .select(Projections.bean(UserDTO.class,
                        user.idx,
                        user.user_name,
                        user.user_id,
                        user.user_pw,
                        user.salt,
                        user.email,
                        user.address,
                        user.pnum,
                        user.role))
                .from(user)
                .where(user.user_id.eq(userId))
                .fetchOne();
    }

    @Override
    public ProfessorDto getProfessor(UserDTO user) {
        return queryFactory
                .select(Projections.bean(ProfessorDto.class,
                        professor.professor_idx,
                        professor.professor_img,
                        Projections.bean(MajorDto.class,
                                professor.major.idx,
                                professor.major.name,
                                professor.major.tuition,
                                professor.major.college,
                                professor.major.abolition).as("major"),
                        professor.hireDate))
                .from(professor)
                .where(professor.user.idx.eq(user.getIdx()))
                .fetchOne();
    }
}
