package com.itbank.simpleboard.repository.user;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.QManager;
import com.itbank.simpleboard.entity.QStudent;
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
                        professor.hireDate.as("hireDate")))
                .from(professor)
                .where(professor.user.idx.eq(user.getIdx()))
                .fetchOne();
    }

    @Override
    public StudentDto getStudent(UserDTO user) {
        return queryFactory
                .select(Projections.bean(StudentDto.class,
                        QStudent.student.idx,
                        QStudent.student.student_num,
                        QStudent.student.student_grade,
                        Projections.bean(ProfessorDto.class,
                                professor.professor_idx,
                                professor.professor_img,
                                professor.hireDate.as("hireDate")).as("professor"),
                        Projections.bean(MajorDto.class,
                                QStudent.student.major.idx,
                                QStudent.student.major.name,
                                QStudent.student.major.tuition,
                                QStudent.student.major.college,
                                QStudent.student.major.abolition).as("major"),
                        QStudent.student.enteranceDate.as("enteranceDate")))
                .from(QStudent.student)
                .where(QStudent.student.user.idx.eq(user.getIdx()))
                .fetchOne();
    }

    @Override
    public ManagerLoginDto getManager(UserDTO user) {
        return queryFactory
                .select(Projections.bean(ManagerLoginDto.class,
                        QManager.manager.idx,
                        QManager.manager.manager_img,
                        QManager.manager.hireDate.as("hire_date")))
                .from(QManager.manager)
                .where(QManager.manager.user.idx.eq(user.getIdx()))
                .fetchOne();
    }
}
