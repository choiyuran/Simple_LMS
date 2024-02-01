package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
public class AcademicCalendar {

    // 학사일정 ID
    @Id
    @Column(name="calendar_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 일정이름
    @Column(name="calendar_title")
    private String title;

    // 시작일자
    @Column(name="calendar_start_date")
    private LocalDate start_date;

    // 종료일자
    @Column(name="calendar_end_date")
    private LocalDate end_date;

    // 작성일자
    @Column(name="calendar_created_date")
    private LocalDate created_date;

    public AcademicCalendar(String title, LocalDate start_date, LocalDate end_date) {
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.created_date = LocalDate.now();
    }

    @PrePersist
    public void prePersist(){
        this.created_date = LocalDate.now();
    }
}
