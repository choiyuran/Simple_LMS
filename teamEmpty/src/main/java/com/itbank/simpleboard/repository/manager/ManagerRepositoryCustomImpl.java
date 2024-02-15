package com.itbank.simpleboard.repository.manager;

import com.itbank.simpleboard.dto.CheckTuitionPaymentDto;
import com.itbank.simpleboard.dto.ManagerDTO;
import com.itbank.simpleboard.dto.QManagerDTO;
import com.itbank.simpleboard.entity.*;
import com.querydsl.core.types.Projections;
import com.itbank.simpleboard.entity.QManager;
import com.itbank.simpleboard.entity.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
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
    public List<ManagerDTO> findBySearchType(HashMap<String, Object> map) {
        BooleanExpression condition = Expressions.asBoolean(true).isTrue();
        String searchType = (String)map.get("searchType");
        String searchValue = (String)map.get("searchValue");
        Boolean leave = (Boolean) map.get("leave");

        // 검색어가 있는 경우
        if(searchType != null && !searchType.isEmpty()) {
            if("name".equals(searchType)) {
                condition = condition.and(manager.user.user_name.likeIgnoreCase("%"+searchValue+"%"));
            } else if ("phone".equals(searchType)) {
                condition = condition.and(manager.user.pnum.likeIgnoreCase("%"+searchValue+"%"));
            } else {
                throw new IllegalArgumentException("유효하지 않은 검색타입입니다.");
            }
        }

        // 체크박스가 체크된 경우
        if(leave != null && leave) {
            condition = condition.and(manager.leave.eq(YesOrNo.valueOf("N")));
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
                .where(condition)
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
