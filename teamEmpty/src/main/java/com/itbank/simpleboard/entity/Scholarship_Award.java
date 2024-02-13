package com.itbank.simpleboard.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
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

    public Scholarship_Award(Student student, Scholarship scholarship) {
        this.student = student;
        this.scholarship = scholarship;
    }
}
