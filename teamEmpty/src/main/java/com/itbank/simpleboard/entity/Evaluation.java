package com.itbank.simpleboard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation { // 수강 평가

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_idx", unique = true)
    private Enrollment enrollment;

    public Evaluation(Integer q1, Integer q2, Integer q3, String q4, String q5, Enrollment enrollment) {
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.enrollment = enrollment;
    }
}
