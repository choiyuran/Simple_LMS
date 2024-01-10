package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private Date start_date;


    // 종료일자
    @Column(name="calendar_end_date")
    private Date end_date;

}
