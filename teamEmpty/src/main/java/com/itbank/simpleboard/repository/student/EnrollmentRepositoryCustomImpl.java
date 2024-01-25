package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.EnrollmentDto;
import com.itbank.simpleboard.dto.QEnrollmentDto;
import com.itbank.simpleboard.dto.StudentDto;
import com.itbank.simpleboard.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.itbank.simpleboard.entity.QEnrollment.enrollment;


@Repository
public class EnrollmentRepositoryCustomImpl implements EnrollmentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public EnrollmentRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<EnrollmentDto> findByStudentAll(Student student) {
        // 1. 특정 학생의 모든 수강 신청을 찾습니다.
        List<Enrollment> enrollments = queryFactory
                .selectFrom(enrollment)
                .where(enrollment.student.eq(student))
                .fetch();

        // 2. 각 수강 신청에 대해 강의 평가가 없는 경우를 찾습니다.
        List<EnrollmentDto> result = new ArrayList<>();
        for (Enrollment e : enrollments) {
            long count = queryFactory
                    .selectFrom(QEvaluation.evaluation)
                    .where(QEvaluation.evaluation.enrollment.eq(e))
                    .fetchCount();

            if (count == 0) {
                result.add(new EnrollmentDto(
                        e.getIdx(),
                        e.getLecture().getName(),
                        e.getLecture().getProfessor().getUser().getUser_name(),
                        e.getLecture().getProfessor().getProfessor_idx()));
            }
        }

        return result;
    }
}
