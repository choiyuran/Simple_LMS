package com.itbank.simpleboard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    // 수강 평가 번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evalutaion_idx")
    private Long idx;

    // 1번 항목
    @Column(name = "Q1", nullable = false)
    private Integer q1;

    // 2번 항목
    @Column(name = "Q2", nullable = false)
    private Integer q2;

    // 3번 항목
    @Column(name = "Q3", nullable = false)
    private Integer q3;

    // 4번 항목(주관식)
    @Column(name = "Q4", nullable = false)
    private String q4;

    // 5번 항목(주관식)
    @Column(name = "Q5", nullable = false)
    private String q5;

    // 수강 현황과 연결(학생 번호, 강의 번호)
    @OneToOne
    @JoinColumn(name = "enrollment_idx")
    private Enrollment enrollment;
}
