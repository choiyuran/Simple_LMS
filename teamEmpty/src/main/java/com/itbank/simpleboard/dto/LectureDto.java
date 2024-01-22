package com.itbank.simpleboard.dto;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import com.itbank.simpleboard.entity.Lecture;
import com.itbank.simpleboard.entity.LectureRoom;
import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.entity.Professor;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LectureDto {
    private Long idx;
    private String name;
    private String intro;
    private Integer credit;
    private String day;
    private String start;
    private String end;
    private String type;
    private Integer maxCount;
    private Integer currentCount;
    private String semester;
    private Integer grade;

    private Professor professor;
    private String plan;
    private Major major;
    private LectureRoom lectureRoom;
    private String visible;

    @QueryProjection
    public LectureDto(Long idx, String name, String intro, Integer credit, String day, String start, String end, String type, Integer maxCount, Integer currentCount, String semester, Integer grade, Professor professor, Major major, LectureRoom lectureRoom, String visible) {
        this(idx,name, intro, credit, day, start, end, type, maxCount, currentCount, semester, grade, professor, null, major, lectureRoom, null);
    }

    @QueryProjection
    public LectureDto(Long idx, String name, String intro, Integer credit, String day, String start, String end, String type, Integer maxCount, Integer currentCount, String semester, Integer grade, Professor professor, String plan, Major major, LectureRoom lectureRoom, String visible) {
        this.idx = idx;
        this.name = name;
        this.intro = intro;
        this.credit = credit;
        this.day = day;
        this.start = start;
        this.end = end;
        this.type = type;
        this.maxCount = maxCount;
        this.currentCount = currentCount;
        this.semester = semester;
        this.grade = grade;
        this.professor = professor;
        this.plan = plan;
        this.major = major;
        this.lectureRoom = lectureRoom;
        this.visible = visible;
    }
}
