package com.itbank.simpleboard.repository.manager;

import com.itbank.simpleboard.dto.CheckTuitionPaymentDto;
import com.itbank.simpleboard.dto.ManagerDTO;
import com.itbank.simpleboard.dto.QManagerDTO;
import com.itbank.simpleboard.entity.QManager;
import com.itbank.simpleboard.entity.QPayments;
import com.itbank.simpleboard.entity.QStudent;
import com.itbank.simpleboard.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.itbank.simpleboard.entity.QManager.*;
import static com.itbank.simpleboard.entity.QUser.user;

@Repository
public class ManagerRepositoryCustomImpl implements ManagerRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Autowired
    public ManagerRepositoryCustomImpl(EntityManager em, EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(em);
        this.entityManager = entityManager;
    }

    @Override
    public List<ManagerDTO> findBySearchType(String searchType, String searchValue) {
        BooleanExpression condition;

        if("name".equals(searchType)){
            condition = manager.user.user_name.likeIgnoreCase("%"+searchValue+"%");
        } else if ("phone".equals(searchType)) {
            condition = manager.user.pnum.likeIgnoreCase("%"+searchValue+"%");
        }
        else {
            throw new IllegalArgumentException("유효하지 않은 검색타입입니다.");
        }

        return queryFactory
                .select(new QManagerDTO(
                        manager.idx,
                        manager.manager_img,
                        user.user_id,
                        user.user_name,
                        user.pnum,
                        user.email,
                        manager.hireDate,
                        user.address,
                        manager.leaveDate
                ))
                .from(manager)
                .innerJoin(user)
                .on(manager.user.user_id.eq(user.user_id))
                .where(
                    condition
                )
                .fetch();
    }

    @Override
    public ManagerDTO selectOneManager(Long idx) {
        return queryFactory
                .select(new QManagerDTO(
                        QManager.manager.idx,
                        QManager.manager.manager_img,
                        QUser.user.user_id,
                        QUser.user.user_name,
                        QUser.user.pnum,
                        QUser.user.email,
                        QManager.manager.hireDate,
                        QUser.user.address,
                        QManager.manager.leaveDate
                        ))
                .from(QManager.manager)
                .join(QManager.manager.user, QUser.user)
                .where(QManager.manager.idx.eq(idx))
                .fetchOne();
    }

    @Override
    public List<CheckTuitionPaymentDto> findAllCheckTuitionPayments() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<CheckTuitionPaymentDto> tuitionPayments = queryFactory
                .select(Projections.constructor(CheckTuitionPaymentDto.class,
                        QStudent.student.idx,
                        user.user_name,
                        QStudent.student.student_grade,
                        QStudent.student.student_num,
                        QPayments.payments.date,
                        QPayments.payments.flag,
                        QPayments.payments.semester))
                .from(QStudent.student)
                .leftJoin(QPayments.payments).on(QStudent.student.eq(QPayments.payments.student))
                .fetch();

        return tuitionPayments;
    }
}
