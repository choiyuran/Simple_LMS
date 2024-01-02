package com.itbank.simpleboard.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity @Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long user_idx;   // 유저 생성 번호

    @Column(name = "user_id")
    private String user_id; // 아이디

    @Column(name = "user_pw")
    private String user_pw; // Hash 비밀번호

    @Column(name = "salt")
    private String salt;    // Hash 처리에 사용된 salt

    @Column(name = "user_name")
    private String user_name;   // 사용자 이름

    @Column(name = "user_security")
    private String security;   // 주민번호

    @Column(name = "user_address")
    private String address;    // 주소

    @Column(name = "user_pnum")
    private String pnum;   // 연락처

    @Column(name = "user_email")
    private String email;  // email

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private User_role role;
}
