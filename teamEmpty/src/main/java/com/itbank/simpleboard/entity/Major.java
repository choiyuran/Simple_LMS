package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.stream.Collectors;

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
    @Column(name = "major_name", nullable = false)
    private String name;
    
    // 등록금
    @Column(name = "major_tuition")
    private Integer tuition;

    // 단과대학
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_idx")
    private College college;

    // 폐지 여부
    @Column(name = "major_abolition")
    @Enumerated(EnumType.STRING)
    private YesOrNo abolition;

    public Major(String name, Integer tuition, College college) {
        this.name = name;
        this.tuition = tuition;
        this.college = college;
        this.abolition = YesOrNo.N;
    }
}
