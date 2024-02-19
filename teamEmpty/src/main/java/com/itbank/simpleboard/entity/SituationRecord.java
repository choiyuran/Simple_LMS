package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "situationRecord")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SituationRecord {      // Situation은 현 상황을 나타내고 SituationRecord는 상황기록테이블
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "situationRecord_idx")
    @Id
    Long idx;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status_type student_status;

    // 학생번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_idx")
    private Student student;

    // 시작일
    @Column(nullable = false)
    private Date start_date;

    // 종료일
    private Date end_date;

    public SituationRecord(Status_type student_status, Student student, Date start_date, Date end_date) {
        this.student_status = student_status;
        this.student = student;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public SituationRecord(Status_type student_status, Student student) {
        this.student_status = student_status;
        this.student = student;
        this.start_date = student.getEnteranceDate();
    }
}
