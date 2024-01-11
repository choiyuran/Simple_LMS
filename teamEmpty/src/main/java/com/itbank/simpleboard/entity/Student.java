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

    public Student(Integer student_num, Integer student_grade, User user, Professor professor, Major major) {
        this.student_num = student_num;
        this.student_grade = student_grade;
        this.user = user;
        this.professor = professor;
        this.major = major;
    }
}
