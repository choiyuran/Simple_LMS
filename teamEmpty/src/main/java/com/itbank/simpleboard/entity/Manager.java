package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Manager {
    // 직원 번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 직원 사진
    private String manager_img;

    @OneToOne
    @JoinColumn(name="user_idx")
    private User user;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name="manager_leave")
    @Enumerated(EnumType.STRING)
    private YesOrNo leave;

    private Date leaveDate;

    public Manager(String manager_img, User user, Date hireDate) {
        this.manager_img = manager_img;
        this.user = user;
        this.hireDate = hireDate;
        this.leave = YesOrNo.N;
    }
}
