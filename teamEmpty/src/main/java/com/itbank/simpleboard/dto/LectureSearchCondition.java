package com.itbank.simpleboard.dto;

import lombok.Data;

@Data
public class LectureSearchCondition {
    // 강의 리스트 검색 조건
    // 강의명, 학점, 요일, 타입, 학기, 학년, 담당 교수, 학과(전공), 강의평가
    private String name;
    private Integer credit;
    private String day;
    private String type;
    private String semester;
    private Integer grade;
    private Long professor;
    private Long major;
    private String visible;
}
