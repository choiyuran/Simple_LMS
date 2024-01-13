package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.LectureDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class LectureRepositoryCustomImpl implements LectureRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public LectureRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<LectureDto> getLectureDtos() {
        return null;
    }
}
