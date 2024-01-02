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

    // 학생번호
    @Id
    @JoinColumn(name = "student_idx")
    private Long student_idx;

    // 상태
    @Enumerated(EnumType.STRING)
    private Status_type student_status;

    // 시작일
    private Date start_date;

    // 종료일
    private Date end_date;



}
