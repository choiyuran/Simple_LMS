package com.itbank.simpleboard.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.sql.Date;
import java.util.Random;
import java.util.UUID;
@Slf4j
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


    public Student(Integer student_grade, User user, Professor professor, Major major, Date enteranceDate,Long majorIdx,Integer lastStudentNum) {
        this.student_grade = student_grade;
        this.user = user;
        this.professor = professor;
        this.major = major;
        this.enteranceDate = enteranceDate;

        // 학생 번호 생성
//        this.student_num = randomId();
        this.student_num = generateStudentNumber(majorIdx,enteranceDate,lastStudentNum);
    }

    private Integer generateStudentNumber(Long majorIdx, Date enteranceDate,Integer lastStudentNum){
        // 년도의 뒷자리 두 개 가져오기
        String yearDigits = String.valueOf(enteranceDate.toLocalDate().getYear()% 100);
        log.info("yearDigits : {}",yearDigits);

        // major.getidx()로 받아온 두 자리 숫자를 가져오기
        String midx = String.format("%02d",majorIdx);
        log.info("midx : {}",midx);

        // 시퀀스로 1씩 증가하는 4자리 숫자 생성
        // 예시: 시퀀스로 1씩 증가하는 값이 1이면 0001로 생성
        String sequenceDigits = generateCombinedSequence(lastStudentNum);
        log.info("sequenceDigits : {}",sequenceDigits);

        student_num = Integer.valueOf(yearDigits + midx + sequenceDigits);
        log.info("student_num : {}",student_num);
        System.err.println("student_num" + student_num);
        System.err.println("enteranceDate" + enteranceDate);
        // 학생 번호 조합
        return student_num;
    }


    public String generateCombinedSequence(Integer fourDigit) {
        if(fourDigit == 0){
            return "0001";
        }else{

            // 네 자리 수에서 뒤의 4자리를 추출
            int lastFourDigits = fourDigit % 10000;
            int frontDigits = lastFourDigits / 100;
            int backDigits = lastFourDigits % 100;

            // 뒤의 두 자리수가 99이면 01로 재설정하고, 앞의 두 자리수를 1 증가시킴
            if (backDigits == 99) {
                backDigits = 1;
                if (frontDigits == 99) {
                    frontDigits = 1;
                } else {
                    frontDigits++;
                }
            } else {
                backDigits++; // 뒤의 두 자리수를 1 증가
            }

            return String.format("%02d%02d", frontDigits, backDigits);
        }
    }
}
