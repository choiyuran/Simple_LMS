package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.LectureListDto;
import com.itbank.simpleboard.dto.LectureSearchCondition;
import com.itbank.simpleboard.dto.QLectureListDto;
import com.itbank.simpleboard.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
                        QUser.user.user_name,
                        lecture.plan,
                        QMajor.major.name,
                        QCollege.college.location,
                        QLectureRoom.lectureRoom.room
                ))
                .from(lecture)
                .leftJoin(QProfessor.professor).on(lecture.professor.eq(QProfessor.professor))
                .leftJoin(QUser.user).on(QProfessor.professor.user.eq(QUser.user))
                .leftJoin(lecture.major, QMajor.major)
                .leftJoin(lecture.lectureRoom, QLectureRoom.lectureRoom)
                .leftJoin(QCollege.college).on(QLectureRoom.lectureRoom.college.eq(QCollege.college))
                .where(
                        nameContain(condition.getName()),
                        typeEq(condition.getType()),
                        yearEq(condition.getYear()),
                        semesterEq(condition.getSemester()),
                        gradeEq(condition.getGrade()),
                        professorEq(condition.getProfessor()),
                        majorEq(condition.getMajor())
                )
                .fetch();
    }


    private BooleanExpression nameContain(String name) {
        return StringUtils.hasText(name) ? lecture.name.contains(name) : null;
    }

    private BooleanExpression typeEq(String type) {
        return StringUtils.hasText(type) ? lecture.type.eq(Lecture_Type.valueOf(type)) : null;
    }

    private BooleanExpression yearEq(Integer year) {
        return year != null ? lecture.semester.contains(String.valueOf(year)) : null;
    }

    private BooleanExpression semesterEq(Integer semester) {
        if (semester == null) {
            return null;
        }

        // "xxxx년 x학기"에서 'x학기' 부분을 추출하기 위한 로직
        String semesterString = String.valueOf(lecture.semester);
        int startIndex = semesterString.lastIndexOf(" ") + 1;

        if (startIndex >= semesterString.length()) {
            return null;
        }

        String semesterSubstring = semesterString.substring(startIndex);

        // 추출한 'x학기'를 사용하여 조건 생성
        return lecture.semester.eq(semesterSubstring);
    }

    private BooleanExpression gradeEq(Integer grade) {
        return grade != null ? lecture.grade.eq(grade) : null;
    }

    private BooleanExpression professorEq(Long professor) {
        return professor != null ? QProfessor.professor.professor_idx.eq(professor) : null;
    }

    private BooleanExpression majorEq(String major) {
        return StringUtils.hasText(major) ? QMajor.major.name.eq(major) : null;
    }
}
