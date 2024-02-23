package com.itbank.simpleboard.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

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

    //학과번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_idx")
    private Major major;

    @Column(name = "enterance_date")
    private Date enteranceDate;

    public Student(Integer student_num, Integer student_grade, User user, Professor professor, Major major, Date enteranceDate) {
        this.student_num = student_num;
        this.student_grade = student_grade;
        this.user = user;
        this.professor = professor;
        this.major = major;
        this.enteranceDate = enteranceDate;
    }


    public Student(Integer student_grade, User user, Professor professor, Major major, Date enteranceDate,Long majorIdx) {
        this.student_grade = student_grade;
        this.user = user;
        this.professor = professor;
        this.major = major;
        this.enteranceDate = enteranceDate;

        // 학생 번호 생성
        this.student_num = generateStudentNumber(majorIdx,enteranceDate);
    }

    private Integer generateStudentNumber(Long majorIdx, Date enteranceDate){
        // 년도의 뒷자리 두 개 가져오기
//        int yearDigits = enteranceDate.getYear() % 100;
        int yearDigits = 21;

        // major.getidx()로 받아온 두 자리 숫자를 가져오기

        // 시퀀스로 1씩 증가하는 4자리 숫자 생성
        // 예시: 시퀀스로 1씩 증가하는 값이 1이면 0001로 생성
        String sequenceDigits = String.format("%04d", idx.intValue());
        student_num = Math.toIntExact(yearDigits * 1000000 + majorIdx * 10000 + Integer.parseInt(sequenceDigits));
        System.err.println("student_num" + student_num);
        System.err.println("enteranceDate" + enteranceDate);
        // 학생 번호 조합
        return student_num;
    }
}
