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
public class Major {
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_idx")
    private Long idx;

    // 전공 이름
    @Column(name = "major_name")
    private String name;
    
    // 등록금
    @Column(name = "major_tuition")
    private Integer tuition;


}
