package com.itbank.simpleboard.entity;

import javax.persistence.*;

@Entity
public class Scholarship_Award {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "award_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_idx")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scholarship_idx")
    private Scholarship scholarship;
}
