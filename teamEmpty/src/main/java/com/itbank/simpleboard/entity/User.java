package com.itbank.simpleboard.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Entity @Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long idx;   // 유저 생성 번호

    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private String user_id; // 아이디

    @Column(name = "user_pw", nullable = false)
    private String user_pw; // Hash 비밀번호

    @Column(name = "salt", nullable = false)
    private String salt;    // Hash 처리에 사용된 salt

    @Column(name = "user_name", nullable = false)
    private String user_name;   // 사용자 이름

    @Column(name = "user_security", nullable = false, unique = true, length = 15)
    private String security;   // 주민번호

    @Column(name = "user_address", nullable = false)
    private String address;    // 주소

    @Column(name = "user_pnum", nullable = false)
    private String pnum;   // 연락처

    @Column(name = "user_email", nullable = false, unique = true, length = 200)
    private String email;  // email

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private User_role role;

    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private YesOrNo authority;  // 계정 권한

    @Column(name = "createdAt")
    private Date createdAt;



    public User(String user_pw,String salt, String user_name, String security, String address, String pnum, String email, User_role role) {
        this.user_id = email;
        this.user_pw = user_pw;
        this.salt = salt;
        this.user_name = user_name;
        this.security = security;
        this.address = address;
        this.pnum = pnum;
        this.email = email;
        this.role = role;
        this.authority =  YesOrNo.Y;
        this.createdAt = Date.valueOf(LocalDate.now());
    }


    // 더미데이터 생성자
    public User(String user_pw,String salt, String user_name, String security, String address, String pnum, String email, User_role role,Date createdAt) {
        this.user_id = email;
        this.user_pw = user_pw;
        this.salt = salt;
        this.user_name = user_name;
        this.security = security;
        this.address = address;
        this.pnum = pnum;
        this.email = email;
        this.role = role;
        this.authority =  YesOrNo.Y;
        this.createdAt = createdAt;
    }



}