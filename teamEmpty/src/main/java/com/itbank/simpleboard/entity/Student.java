package com.itbank.simpleboard.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Student {

    // 학생번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 학번
    @Column(nullable = false, unique = true)
    private Integer student_num;

    // 학년
    @Column(nullable = false)
    private Integer student_grade;

    // 장학금아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scholarship_idx")
    private Scholarship scholarship;

    // 기본키
    @OneToOne
    @JoinColumn(name = "user_idx")
    private User user;

    // 지도교수번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_idx")
    private Professor professor;

//     학과번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_idx")
    private Major major;

}
