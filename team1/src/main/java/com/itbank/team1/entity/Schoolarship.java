package com.itbank.team1.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
// 장학금
public class Schoolarship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스
    // 장학금 번호
    @Column(name = "schoolarship_idx")
    private Long idx;

    // 외부 / 내부 장학금 카테고리
    @Column(name = "schoolarship_category")
    private String category;

    // 장학금 이름
    @Column(name = "schoolarship_name")
    private String name;

    // 금액
    @Column(name = "schoolarship_price")
    private Integer price;
}
