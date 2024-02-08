package com.itbank.simpleboard.dto;

import lombok.Data;

@Data
public class LectureSearchConditionDto {
    // 강의 리스트 검색 조건
    // 강의명, 타입, 학기, 학년, 담당 교수, 학과(전공)
    private String name;
    private String type;
    private Integer year;
    private Integer semester;
    private Integer grade;
    private Long professor_idx;
    private String major;
    private String isAbolition;
}
