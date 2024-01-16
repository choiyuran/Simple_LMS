package com.itbank.simpleboard.dto;

import com.itbank.simpleboard.entity.Professor;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
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
    private Long major;
    private Long lectureRoom;
    private String visible;

    @QueryProjection
    public LectureDto(Long idx, String name, String intro, Integer credit, String day, String start, String end, String type, Integer maxCount, Integer currentCount, String semester, Integer grade) {
        this(idx,name, intro, credit, day, start, end, type, maxCount, currentCount, semester, grade, null, null, 0L, 0L, null);
    }

    @QueryProjection
    public LectureDto(Long idx, String name, String intro, Integer credit, String day, String start, String end, String type, Integer maxCount, Integer currentCount, String semester, Integer grade, Professor professor, String plan, Long major, Long lectureRoom, String visible) {
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
