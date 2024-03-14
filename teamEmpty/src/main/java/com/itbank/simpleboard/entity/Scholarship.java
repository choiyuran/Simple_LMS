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
// 장학금
public class Scholarship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스
    // 장학금 번호
    @Column(name = "scholarship_idx")
    private Long idx;

    // 외부 / 내부 장학금 카테고리
    @Column(name = "scholarship_category")
    private String category;

    // 장학금 이름
    @Column(name = "scholarship_name")
    private String name;

    // 금액
    @Column(name = "scholarship_price")
    private Integer price;

    // 수여 년도
    @Column(name = "scholarship_year")
    private Integer year;

    // 수여 분기
    @Column(name = "scholarship_quarter")
    private Integer quarter;

    public Scholarship(String category, String name, Integer price, Integer year, Integer quarter) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.year = year;
        this.quarter = quarter;
    }


}
