package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
// 성적
public class Grade {

    @Id
    // 학생번호 (외래키)
    private Long student_idx;
//    @JoinColumn(name = "student_idx")
//    private Student student_idx;

    // 강의번호 (외래키)
//    @JoinColumn(name = "lecture_idx")
//    private Lecture lecture_idx;

    // 학점
    @Column(name = "grade_score")
    private String score;

}