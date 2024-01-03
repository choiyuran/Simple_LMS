package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("professor")
public class Professor {
    // 교수 번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long professor_idx;

    // 교수 사진
    private String professor_img;

    @OneToOne
    @JoinColumn(name="user_idx")
    private User user_idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_idx")
    private Major major_idx;    // 학과 번호
}
