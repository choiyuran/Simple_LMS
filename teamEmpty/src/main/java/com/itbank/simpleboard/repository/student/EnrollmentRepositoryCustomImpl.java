package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.EnrollmentDto;
import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Student;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnrollmentRepositoryCustomImpl implements EnrollmentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public EnrollmentRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<EnrollmentDto> getEnrollListByStudent(Student student) {

        return null;
    }
}
