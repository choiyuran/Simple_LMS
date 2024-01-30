package com.itbank.simpleboard.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "enrollment")
@NoArgsConstructor
public class Enrollment {
// 수강 현황
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_idx")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_idx")
    private Lecture lecture;

    public Enrollment(Student student, Lecture lecture) {
        this.student = student;
        this.lecture = lecture;
    }
}
