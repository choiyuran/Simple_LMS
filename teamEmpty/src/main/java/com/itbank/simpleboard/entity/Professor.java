package com.itbank.simpleboard.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("professor")
@Data
public class Professor {
    // 교수 번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long professor_idx;

    // 교수 사진
    private String professor_img;

    @OneToOne
    @JoinColumn(name="user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_idx")
    private Major major;    // 학과 번호

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name="professor_leave")
    @Enumerated(EnumType.STRING)
    private YesOrNo leave;

    private Date leaveDate;

    public Professor(String professor_img, User user, Major major, Date hireDate) {
        this.professor_img = professor_img;
        this.user = user;
        this.major = major;
        this.hireDate = hireDate;
        this.leave = YesOrNo.N;
    }
}