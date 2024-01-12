package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.LectureListDto;
import com.itbank.simpleboard.dto.LectureSearchCondition;
import com.itbank.simpleboard.dto.QLectureListDto;
import com.itbank.simpleboard.entity.QLectureRoom;
import com.itbank.simpleboard.entity.QMajor;
import com.itbank.simpleboard.entity.QProfessor;
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
    public List<LectureListDto> getLectureListDto(LectureSearchCondition condition) {
        return queryFactory
                .select(new QLectureListDto(
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
                        lecture.grade,
                        QProfessor.professor.professor_idx,
                        lecture.plan,
                        QMajor.major.idx,
                        QLectureRoom.lectureRoom.idx,
                        lecture.visible.stringValue()
                ))
                .from(lecture)
                .leftJoin(lecture.professor, QProfessor.professor)  // lecture 엔티티에서 professor를 조인하고, professor 엔티티 전체를 불러온다
                .leftJoin(lecture.major, QMajor.major)  // lecture 엔티티에서 major를 조인하고, major 엔티티 전체를 불러온다
                .leftJoin(lecture.lectureRoom, QLectureRoom.lectureRoom)    // lecture 엔티티에서 lectureRoom을 조인하고, lectureRoom 엔티티 전체를 불러온다
                .where(

                )
                .fetch();
    }
}
