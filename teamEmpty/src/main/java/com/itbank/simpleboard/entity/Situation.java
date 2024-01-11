package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Situation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 학생번호
    @OneToOne
    @JoinColumn(name = "student_idx")
    private Student student;

    // 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status_type student_status;

    // 시작일
    private Date start_date;

    // 종료일
    private Date end_date;

    public Situation(Student student, Status_type student_status, Date start_date, Date end_date) {
        this.student = student;
        this.student_status = student_status;
        this.start_date = start_date;
        this.end_date = end_date;
    }
}
