package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Manager {
    // 직원 번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 직원 사진
    private String manager_img;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx")
    private User user;

    private Date hire_date; // 입사일
}
