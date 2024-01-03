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
    private Long situationId;

    // 학생번호
    @OneToOne
    @JoinColumn(name = "student_idx")
    private Student student_idx;

    // 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status_type student_status;

    // 시작일
    private Date start_date;

    // 종료일
    private Date end_date;



}
