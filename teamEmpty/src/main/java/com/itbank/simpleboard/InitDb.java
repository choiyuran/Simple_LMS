//package com.itbank.simpleboard;
//
//import com.itbank.simpleboard.entity.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        try {
//            initService.insertCalendar();
//            initService.dbInit3();
//            initService.dbInit6();
//            initService.dbInit4();
//            initService.dbInit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//        private final EntityManager em;
//
//        public void dbInit4() {
//            User user1 = new User("professor1", "1234", "1234", "정수용", "111111-1111111", "울산광역시 남구", "010-1234-1234", "test1@naver.com", User_role.교수);
//            User user2 = new User("professor2", "1234", "1234", "최유란", "222222-2222222", "부산광역시 미남", "010-1234-1234", "test2@naver.com", User_role.교수);
//            User user3 = new User("professor3", "1234", "1234", "이병길", "333333-3333333", "부산광역시 대연동", "010-1234-1234", "test3@naver.com", User_role.교수);
//            em.persist(user1);
//            em.persist(user2);
//            em.persist(user3);
//
//            User user4 = new User("manager1", "1234", "1234", "황민우", "444444-4444444", "서울특별시 강남", "010-1234-1234", "test1@naver.com", User_role.교직원);
//            User user5 = new User("manager2", "1234", "1234", "박소은", "555555-5555555", "서울특별시 강북", "010-1234-1234", "test2@naver.com", User_role.교직원);
//            User user6 = new User("manager3", "1234", "1234", "안지혜", "666666-6666666", "부산광역시 수영구", "010-1234-1234", "test3@naver.com", User_role.교직원);
//            em.persist(user4);
//            em.persist(user5);
//            em.persist(user6);
//
//            User user7 = new User("24000001", "1234", "1234", "박지성", "777777-7777777", "울산광역시 남구", "010-1234-1234", "test1@naver.com", User_role.학생);
//            User user8 = new User("24000002", "1234", "1234", "손흥민", "888888-8888888", "부산광역시 미남", "010-1234-1234", "test2@naver.com", User_role.학생);
//            User user9 = new User("24000003", "1234", "1234", "박찬호", "999999-9999999", "부산광역시 대연동", "010-1234-1234", "test3@naver.com", User_role.학생);
//            em.persist(user7);
//            em.persist(user8);
//            em.persist(user9);
//
//            Major major1 = em.find(Major.class, 1L);
//            Major major2 = em.find(Major.class, 2L);
//            Major major3 = em.find(Major.class, 3L);
//
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//                Date utilDate = sdf.parse("2024/11/02");
//                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//
//                Professor professor1 = new Professor("hi", user1, major1, sqlDate);
//                Professor professor2 = new Professor("hi", user2, major2, sqlDate);
//                Professor professor3 = new Professor("hi", user3, major3, sqlDate);
//                em.persist(professor1);
//                em.persist(professor2);
//                em.persist(professor3);
//
//                Manager manager1 = new Manager("hi", user4, sqlDate);
//                Manager manager2 = new Manager("hi", user5, sqlDate);
//                Manager manager3 = new Manager("hi", user6, sqlDate);
//                em.persist(manager1);
//                em.persist(manager2);
//                em.persist(manager3);
//
//                Student student1 = new Student(24000001, 1, user7, professor1, major1, sqlDate);
//                Student student2 = new Student(24000002, 2, user8, professor2, major2, sqlDate);
//                Student student3 = new Student(24000003, 3, user9, professor3, major3, sqlDate);
//                em.persist(student1);
//                em.persist(student2);
//                em.persist(student3);
//
//                Situation situation1 = new Situation(student1, Status_type.재학, new Date(), null);
//                Situation situation2 = new Situation(student2, Status_type.재학, new Date(), null);
//                Situation situation3 = new Situation(student3, Status_type.재학, new Date(), null);
//
//                em.persist(situation1);
//                em.persist(situation2);
//                em.persist(situation3);
//
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//
//        public void insertCalendar() throws Exception {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//
//            Date startDate1 = sdf.parse("2024/01/01");
//            Date endDate1 = sdf.parse("2024/01/31");
//
//            AcademicCalendar calendar1 = new AcademicCalendar("학기 시작", startDate1, endDate1);
//            em.persist(calendar1);
//
//            Date startDate2 = sdf.parse("2024/06/01");
//            Date endDate2 = sdf.parse("2024/06/30");
//
//            AcademicCalendar calendar2 = new AcademicCalendar("여름 방학 시작", startDate2, endDate2);
//            em.persist(calendar2);
//
//            Date startDate3 = sdf.parse("2024/12/01");
//            Date endDate3 = sdf.parse("2024/12/31");
//
//            AcademicCalendar calendar3 = new AcademicCalendar("겨울 방학 시작", startDate3, endDate3);
//            em.persist(calendar3);
//        }
//
//        public void dbInit3() {
//            Scholarship scholarship1 = new Scholarship("내부", "성적우수장학금", 1000000, 2024, 1);
//            em.persist(scholarship1);
//            Scholarship scholarship2 = new Scholarship("내부", "근로장학금", 2000000, 2024, 2);
//            em.persist(scholarship2);
//            Scholarship scholarship3 = new Scholarship("외부", "국가장학금", 300000, 2024, 3);
//            em.persist(scholarship3);
//        }
//
//
//
//        public void dbInit6() {
//            College college1 = new College("사범대학","인문관1");
//            College college2 = new College("사회과학대학","인문관2");
//            College college3 = new College("의과대학","공학관");
//            em.persist(college1);
//            em.persist(college2);
//            em.persist(college3);
//
//            Major major1 = new Major("정치외교학과",3500000,college1);
//            Major major2 = new Major("지리학과",3200000,college1);
//            Major major3 = new Major("군사학과",3300000,college1);
//            em.persist(major1);
//            em.persist(major2);
//            em.persist(major3);
//
//            Major major4 = new Major("수학교육과",2900000,college2);
//            Major major5 = new Major("사회교육과",2800000,college2);
//            Major major6 = new Major("국어교육과",3000000,college2);
//            em.persist(major4);
//            em.persist(major5);
//            em.persist(major6);
//
//            Major major7 = new Major("신경과",5500000,college3);
//            Major major9 = new Major("정신과",5700000,college3);
//            Major major8 = new Major("마취과",5200000,college3);
//            em.persist(major7);
//            em.persist(major8);
//            em.persist(major9);
//
//            LectureRoom lectureRoom1 = new LectureRoom(101,college1);
//            LectureRoom lectureRoom2 = new LectureRoom(102,college1);
//            LectureRoom lectureRoom3 = new LectureRoom(201,college2);
//            LectureRoom lectureRoom4 = new LectureRoom(202,college2);
//            LectureRoom lectureRoom5 = new LectureRoom(501,college3);
//            LectureRoom lectureRoom6 = new LectureRoom(502,college3);
//
//            em.persist(lectureRoom1);
//            em.persist(lectureRoom2);
//            em.persist(lectureRoom3);
//            em.persist(lectureRoom4);
//            em.persist(lectureRoom5);
//            em.persist(lectureRoom6);
//        }
//        public void dbInit() {
//            Lecture lecture= new Lecture("정치학개론", "정치학의 기본적인 이론과 원칙, 그리고 주요한 주제들에 대해서 다룹니다.", 3, "월,수,금", "9:00,15:30,9:00", "12:00,17:30,12:00", Lecture_Type.전공필수,  em.find(Professor.class, 1L), 30, 25, "2022년 1학기", 1, null, em.find(Major.class, 2L), em.find(LectureRoom.class, 1L), YesOrNo.N);
//            em.persist(lecture);
//
//            Lecture lecture2 = new Lecture("지리학개론", "지리학의 기본 개념과 원리를 소개합니다.", 3, "월,수,금", "10:00,14:30,9:00", "12:00,16:30,11:00", Lecture_Type.전공필수,  em.find(Professor.class, 1L), 30, 25, "2022년 1학기", 1, null, em.find(Major.class, 1L), em.find(LectureRoom.class, 1L), YesOrNo.N);
//            em.persist(lecture2);
//
//            Lecture lecture3 = new Lecture("군사학개론", "군사학의 기본적인 이론과 원칙을 학습합니다.", 3, "월,금", "13:00,16:00", "15:00,18:00", Lecture_Type.전공선택,  em.find(Professor.class, 1L), 30, 20, "2022년 2학기", 3, null, em.find(Major.class, 3L), em.find(LectureRoom.class, 1L), YesOrNo.N);
//            em.persist(lecture3);
//
//            Lecture lecture4 = new Lecture("수학교육론", "수학 교육의 이론과 방법에 대해 다룹니다.", 3, "월,목,금", "9:00,15:30,9:00", "12:00,17:30,12:00", Lecture_Type.선택교양,  em.find(Professor.class, 2L), 30, 22, "2022년 2학기", 1, null, em.find(Major.class, 4L), em.find(LectureRoom.class, 2L), YesOrNo.N);
//            em.persist(lecture4);
//
//            Lecture lecture5 = new Lecture("사회학", "사회의 기본 구조와 기능, 그리고 사회 현상을 이해하는 방법을 다룹니다.", 3, "수,금", "15:30,9:00", "17:30,12:00", Lecture_Type.전공선택,  em.find(Professor.class, 2L), 30, 28, "2023년 1학기", 2, null, em.find(Major.class, 5L), em.find(LectureRoom.class, 2L), YesOrNo.N);
//            em.persist(lecture5);
//
//            Lecture lecture6 = new Lecture("국어교육론", " 국어 교육의 이론과 방법에 대해 다룹니다.", 3, "월,목", "9:00,9:00", "12:00,12:00", Lecture_Type.전공필수,  em.find(Professor.class, 2L), 30, 20, "2023년 2학기", 2, null, em.find(Major.class, 6L), em.find(LectureRoom.class, 2L), YesOrNo.N);
//            em.persist(lecture6);
//
//            Lecture lecture7 = new Lecture("신경과학개론", "신경계의 기본 구조와 기능에 대해 배웁니다.", 3, "월,수,금", "9:00,15:30,9:00", "12:00,17:30,12:00", Lecture_Type.전공필수,  em.find(Professor.class, 3L), 30, 25, "2023년 1학기", 3, null, em.find(Major.class, 7L), em.find(LectureRoom.class, 3L), YesOrNo.N);
//            em.persist(lecture7);
//
//            Lecture lecture8 = new Lecture("정신과학개론", "정신과학의 기본 원리와 개념에 대해 배웁니다.", 3, "수,금", "15:30,15:00", "17:30,15:00", Lecture_Type.선택교양,  em.find(Professor.class, 3L), 30, 29, "2022년 1학기", 4, null, em.find(Major.class, 8L), em.find(LectureRoom.class, 3L), YesOrNo.N);
//            em.persist(lecture8);
//
//            Lecture lecture9 = new Lecture("마취과학개론", "마취의 기본 원리와 마취 약물에 대해 배웁니다.", 3, "월,화,금", "9:00,15:30,9:00", "12:00,17:30,12:00", Lecture_Type.전공필수,  em.find(Professor.class, 3L), 30, 30, "2023년 2학기", 4, null, em.find(Major.class, 9L), em.find(LectureRoom.class, 3L), YesOrNo.N);
//            em.persist(lecture9);
//        }
//
//
//    }
//}
