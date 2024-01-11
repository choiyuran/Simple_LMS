package com.itbank.simpleboard.dto;

import com.querydsl.core.annotations.QueryProjection;

public class lectureListDto {
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

    @QueryProjection
    public lectureListDto(String name, String intro, Integer credit, String day, String start, String end, String type, Integer maxCount, Integer currentCount, String semester, Integer grade) {
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
    }
}
