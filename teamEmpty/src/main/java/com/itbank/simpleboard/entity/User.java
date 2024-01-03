package com.itbank.simpleboard.entity;

import lombok.*;

import javax.persistence.*;

@Entity @Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long user_idx;   // 유저 생성 번호

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

    @Column(name = "user_email", nullable = false)
    private String email;  // email

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private User_role role;
}
