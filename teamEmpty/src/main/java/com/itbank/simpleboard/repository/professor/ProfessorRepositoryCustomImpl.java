package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.QlectureListDto;
import com.itbank.simpleboard.dto.lectureListDto;
import com.itbank.simpleboard.entity.QLecture;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.itbank.simpleboard.entity.QLecture.*;

@Repository
public class ProfessorRepositoryCustomImpl implements ProfessorRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public ProfessorRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<lectureListDto> getLectureListDto() {
        return queryFactory
                .select(new QlectureListDto(
                        lecture.name,
                        lecture.intro,
                        lecture.credit,
                        lecture.day,
                        lecture.start,
                        lecture.end,
                        lecture.type.stringValue(),
                        lecture.maxCount,
                        lecture.currentCount,
                        lecture.semester,
                        lecture.grade
                ))
                .from(lecture)
                .fetch();
    }
}
