package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
// 성적
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_idx")
    private Long idx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="enrollment_idx")
    private Enrollment enrollment;

    // 학점
    @Column(name = "grade_score")
    private String score;

    public Grade(Enrollment enrollment, String score) {
        this.enrollment = enrollment;
        this.score = score;
    }
}