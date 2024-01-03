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
    private Long student_idx;

    // 학번
    private Integer student_num;

    // 학년
    private Integer student_grade;

    // 장학금아이디
//    @JoinColumn(name = "scholarship_idx")
//    private Scholarship scholarship_idx;

    // 기본키
//    private User user_idx;

    // 지도교수번호
//    private Professor professor_idx;

//     학과번호
//    private Major major_idx;

}
