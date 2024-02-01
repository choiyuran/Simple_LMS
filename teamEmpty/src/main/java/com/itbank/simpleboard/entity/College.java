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
public class College {
    // 단과대학 번호
    @Id
    @Column(name="college_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 단과대학_이름
    @Column(name="college_name")
    private String name;

    // 건물위치
    @Column(name="college_location")
    private String location;

    public College(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
