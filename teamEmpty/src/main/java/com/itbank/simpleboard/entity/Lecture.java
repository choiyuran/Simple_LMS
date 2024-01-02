
package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Lecture {
    
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스
    @Column(name = "lecture_idx")
    private Long idx;

    // 강의명
    @Column(name = "lecture_name")
    private String name;

    // 강의소개
    @Column(name = "lecture_intro")
    private String intro;

    // 수강 학점
    @Column(name = "lecture_credit")
    private Integer credit;

    // 수강 요일
    @Column(name = "lecture_day")
    private String day;

    // 수강 시작 시간(시)
    @Column(name = "lecture_start")
    private Integer start;

    // 수강 끝 식단
    @Column(name = "lecture_end")
    private Integer end;

    // 수강 강의타입
    @Column(name = "lecture_type")
    @Enumerated(EnumType.STRING)
    private Lecture_Type type;
    
    // 교수번호
    // 연관관계 만들어라
    
    // 최대 인원수
    @Column(name = "max_count")
    private Integer maxCount;
    
    // 현재 수강자 수 
    @Column(name = "current_count")
    private Integer currentCount;

    // 강의 학기
    @Column(name = "lecture_semester")
    private String semester;

    // 학년
    @Column(name = "lecture_grade")
    private Integer grade;

    // 학과번호 연관관계

    // 강의실 번호 연관관계

}
