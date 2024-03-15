//package com.itbank.simpleboard;
//
//import com.itbank.simpleboard.component.HashComponent;
//import com.itbank.simpleboard.entity.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.text.ParseException;
//import java.util.Date;
//import java.util.Random;
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        try {
//            initService.dbInit6(); // 1. 단과대학 + 학과
//            initService.generateDummyData();
///*            initService.dbInit4();
///            initService.insertCalendar();
//           */
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
//        private final HashComponent hashComponent;
//
//        public void dbInit6() {
//            College college1 = new College("인문대학", "인문관");
//            College college2 = new College("사회과학대학", "사회과학관");
//            College college3 = new College("자연과학대학", "자연과학관");
//            College college4 = new College("간호대학", "간호학관");
//            College college5 = new College("경영대학", "경영학관");
//            College college6 = new College("공과대학", "공학관");
//            College college7 = new College("농업생명과학대학", "자연과학관");
//            College college8 = new College("미술대학", "예술관");
//            College college9 = new College("사범대학", "사범관");
//            College college10 = new College("생활과학대학", "인문관");
//            College college11 = new College("수의과대학", "의학관");
//            College college12 = new College("약학대학", "의학관");
//            College college13 = new College("음악대학", "예술관");
//            College college14 = new College("의과대학", "의학관");
//            College college15 = new College("자유전공학부", "인문관");
//            College college16 = new College("첨단융합학부", "첨단과학관");
//            em.persist(college1);
//            em.persist(college2);
//            em.persist(college3);
//            em.persist(college4);
//            em.persist(college5);
//            em.persist(college6);
//            em.persist(college7);
//            em.persist(college8);
//            em.persist(college9);
//            em.persist(college10);
//            em.persist(college11);
//            em.persist(college12);
//            em.persist(college13);
//            em.persist(college14);
//            em.persist(college15);
//            em.persist(college16);
//
//            Major c1Major1 = new Major("국어국문학과",3500000,college1);
//            Major c1Major2 = new Major("중어중문학과",2900000,college1);
//            Major c1Major3 = new Major("영어영문학과",3700000,college1);
//            Major c1Major4 = new Major("불어불문학과",3700000,college1);
//            Major c1Major5 = new Major("독어독문학과",3200000,college1);
//            Major c1Major6 = new Major("노어노문학과",3200000,college1);
//            Major c1Major7 = new Major("서어서문학과",3100000,college1);
//            Major c1Major8 = new Major("아시아언어문명학부",3000000,college1);
//            Major c1Major9 = new Major("언어학과",2800000,college1);
//            Major c1Major10 = new Major("역사학부",3000000,college1);
//            Major c1Major11 = new Major("철학과",2800000,college1);
//            Major c1Major12 = new Major("종교학과",3000000,college1);
//            Major c1Major13 = new Major("미학과",4000000,college1);
//            Major c1Major14 = new Major("고고미술사학과",4100000,college1);
//            em.persist(c1Major1);
//            em.persist(c1Major2);
//            em.persist(c1Major3);
//            em.persist(c1Major4);
//            em.persist(c1Major5);
//            em.persist(c1Major6);
//            em.persist(c1Major7);
//            em.persist(c1Major8);
//            em.persist(c1Major9);
//            em.persist(c1Major10);
//            em.persist(c1Major11);
//            em.persist(c1Major12);
//            em.persist(c1Major13);
//            em.persist(c1Major14);
//
//
//            Major c2Major1 = new Major("정치외교학부(정치학전공)",2900000,college2);
//            Major c2Major2 = new Major("정치외교학부(외교학전공)",2800000,college2);
//            Major c2Major3 = new Major("경제학부",3000000,college2);
//            Major c2Major4 = new Major("사회학과",3100000,college2);
//            Major c2Major5 = new Major("인류학과",3200000,college2);
//            Major c2Major6 = new Major("심리학과",3300000,college2);
//            Major c2Major7 = new Major("지리학과",3400000,college2);
//            Major c2Major8 = new Major("사회복지학과",3500000,college2);
//            Major c2Major9 = new Major("언론정보학과",3000000,college2);
//            em.persist(c2Major1);
//            em.persist(c2Major2);
//            em.persist(c2Major3);
//            em.persist(c2Major4);
//            em.persist(c2Major5);
//            em.persist(c2Major6);
//            em.persist(c2Major7);
//            em.persist(c2Major8);
//            em.persist(c2Major9);
//
//            Major c3Major1 = new Major("수리과학부",5500000,college3);
//            Major c3Major2 = new Major("통계학과",5700000,college3);
//            Major c3Major3 = new Major("물리천문학부(물리학전공)",5200000,college3);
//            Major c3Major4 = new Major("물리천문학부(천문학전공)",5200000,college3);
//            Major c3Major5 = new Major("화학부",5200000,college3);
//            Major c3Major6 = new Major("생명과학부",5200000,college3);
//            Major c3Major7 = new Major("지구환경과학부",5200000,college3);
//            em.persist(c3Major1);
//            em.persist(c3Major2);
//            em.persist(c3Major3);
//            em.persist(c3Major4);
//            em.persist(c3Major5);
//            em.persist(c3Major6);
//            em.persist(c3Major7);
//
//            Major c4Major1 = new Major("간호학과",5200000,college4);
//            em.persist(c4Major1);
//
//            Major c5Major1 = new Major("경영학과",5200000,college5);
//            em.persist(c5Major1);
//
//            Major c6Major1 = new Major("건설환경공학부",5200000,college6);
//            Major c6Major2 = new Major("기계공학부",5200000,college6);
//            Major c6Major3 = new Major("항공우주공학부",5200000,college6);
//            Major c6Major4 = new Major("재료공학부",5200000,college6);
//            Major c6Major5 = new Major("전기·정보공학부",5200000,college6);
//            Major c6Major6 = new Major("컴퓨터공학부",5200000,college6);
//            Major c6Major7 = new Major("화학생물공학부",5200000,college6);
//            Major c6Major8 = new Major("건축학과",5200000,college6);
//            Major c6Major9 = new Major("산업공학과",5200000,college6);
//            Major c6Major10 = new Major("에너지자원공학과",5200000,college6);
//            Major c6Major11 = new Major("원자핵공학과",5200000,college6);
//            Major c6Major12 = new Major("조선해양공학과",5200000,college6);
//            em.persist(c6Major1);
//            em.persist(c6Major2);
//            em.persist(c6Major3);
//            em.persist(c6Major4);
//            em.persist(c6Major5);
//            em.persist(c6Major6);
//            em.persist(c6Major7);
//            em.persist(c6Major8);
//            em.persist(c6Major9);
//            em.persist(c6Major10);
//            em.persist(c6Major11);
//            em.persist(c6Major12);
//
//            Major c7Major1 = new Major("식물생산과학부",5100000,college7);
//            Major c7Major2 = new Major("산림과학부",5000000,college7);
//            Major c7Major3 = new Major("응용생물화학부",5100000,college7);
//            Major c7Major4 = new Major("식품·동물생명공학부",5200000,college7);
//            Major c7Major5 = new Major("바이오시스템·소재학부",5300000,college7);
//            Major c7Major6 = new Major("조경·지역시스템공학부",5400000,college7);
//            Major c7Major7 = new Major("농경제사회학부",5500000,college7);
//            em.persist(c7Major1);
//            em.persist(c7Major2);
//            em.persist(c7Major3);
//            em.persist(c7Major4);
//            em.persist(c7Major5);
//            em.persist(c7Major6);
//            em.persist(c7Major7);
//
//            Major c8Major1 = new Major("동양화과",5200000,college8);
//            Major c8Major2 = new Major("서양화과",5100000,college8);
//            Major c8Major3 = new Major("조소과",4900000,college8);
//            Major c8Major4 = new Major("디자인학부(공예)",5000000,college8);
//            Major c8Major5 = new Major("디자인학부(디자인)",5500000,college8);
//            em.persist(c8Major1);
//            em.persist(c8Major2);
//            em.persist(c8Major3);
//            em.persist(c8Major4);
//            em.persist(c8Major5);
//
//            Major c9Major1 = new Major("교육학과",5200000,college9);
//            Major c9Major2 = new Major("국어교육과",4900000,college9);
//            Major c9Major3 = new Major("영어교육과",4800000,college9);
//            Major c9Major4 = new Major("불어교육과",3700000,college9);
//            Major c9Major5 = new Major("독어교육과",4900000,college9);
//            Major c9Major6 = new Major("사회교육과",3900000,college9);
//            Major c9Major7 = new Major("역사교육과",3900000,college9);
//            Major c9Major8 = new Major("지리교육과",4100000,college9);
//            Major c9Major9 = new Major("윤리교육과",3600000,college9);
//            Major c9Major10 = new Major("수학교육과",4000000,college9);
//            Major c9Major11 = new Major("물리교육과",5100000,college9);
//            Major c9Major12 = new Major("화학교육과",5000000,college9);
//            Major c9Major13 = new Major("생물교육과",3900000,college9);
//            Major c9Major14= new Major("지구과학교육과",4900000,college9);
//            Major c9Major15 = new Major("체육교육과",3700000,college9);
//            em.persist(c9Major1);
//            em.persist(c9Major2);
//            em.persist(c9Major3);
//            em.persist(c9Major4);
//            em.persist(c9Major5);
//            em.persist(c9Major6);
//            em.persist(c9Major7);
//            em.persist(c9Major8);
//            em.persist(c9Major9);
//            em.persist(c9Major10);
//            em.persist(c9Major11);
//            em.persist(c9Major12);
//            em.persist(c9Major13);
//            em.persist(c9Major14);
//            em.persist(c9Major15);
//
//
//            Major c10Major1 = new Major("소비자아동학부(소비자학전공)",2700000,college10);
//            Major c10Major2 = new Major("소비자아동학부(아동가족학)",3100000,college10);
//            Major c10Major3 = new Major("식품영양학과",4400000,college10);
//            Major c10Major4 = new Major("의류학과",4300000,college10);
//            em.persist(c10Major1);
//            em.persist(c10Major2);
//            em.persist(c10Major3);
//            em.persist(c10Major4);
//
//            Major c11Major1 = new Major("수의예과",5200000,college11);
//            Major c11Major2 = new Major("수의학과",5100000,college11);
//            em.persist(c11Major1);
//            em.persist(c11Major2);
//
//            Major c12Major1 = new Major("약학과",5300000,college12);
//            Major c12Major2 = new Major("제약학과",5000000,college12);
//            em.persist(c12Major1);
//            em.persist(c12Major2);
//
//            Major c13Major1 = new Major("성악과",5100000,college13);
//            Major c13Major2 = new Major("작곡과",5200000,college13);
//            Major c13Major3 = new Major("음악학과",5600000,college13);
//            Major c13Major4 = new Major("피아노과",4900000,college13);
//            Major c13Major5 = new Major("관현악과",3700000,college13);
//            Major c13Major6 = new Major("국악과",4000000,college13);
//            em.persist(c13Major1);
//            em.persist(c13Major2);
//            em.persist(c13Major3);
//            em.persist(c13Major4);
//            em.persist(c13Major5);
//            em.persist(c13Major6);
//
//            Major c14Major1 = new Major("의예과",5700000,college14);
//            Major c14Major2 = new Major("의학과",5900000,college14);
//            em.persist(c14Major1);
//            em.persist(c14Major2);
//
//            Major c15Major1 = new Major("자유전공학부",4500000,college15);
//            em.persist(c15Major1);
//
//            Major c16Major1 = new Major("첨단융합학부",4900000,college16);
//            em.persist(c16Major1);
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
//        public void dbInit4() {
//            String randomSalt = hashComponent.getRandomSalt();
//            String hash = hashComponent.getHash("1234", randomSalt);
//
//            // 날짜 형식 지정
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//            // 2024년 2월 25일로 설정
//            LocalDate createdAt = LocalDate.parse("2024-02-25", formatter);
//            java.sql.Date sqlCreatedAt = java.sql.Date.valueOf(createdAt);
//
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
//
//
//            User user1 = new User(hash, randomSalt, "정수용", "222222-2222222", "부산광역시 미남", "010-1234-1234", "tesrht2@naver.com", User_role.교수,sqlCreatedAt);
//            User user2 = new User(hash, randomSalt, "최유란", "111111-1111111", "울산광역시 남구", "010-1234-1234", "tesagat1@naver.com", User_role.교수,sqlCreatedAt);
//            User user3 = new User(hash, randomSalt, "이병길", "333333-3333333", "부산광역시 대연동", "010-1234-1234", "ahtest3@naver.com", User_role.교수,sqlCreatedAt);
//            em.persist(user1);
//            em.persist(user2);
//            em.persist(user3);
//
//            User user5 = new User(hash, randomSalt, "황민우", "555555-5555555", "서울특별시 강북", "010-1234-1234", "teulahst2@naver.com", User_role.교직원,sqlCreatedAt);
//            User user6 = new User(hash, randomSalt, "박소은", "666666-6666666", "부산광역시 수영구", "010-1234-1234", "teulhst3@naver.com", User_role.교직원,sqlCreatedAt);
//            User user4 = new User(hash, randomSalt, "안지혜", "444444-4444444", "서울특별시 강남", "010-1234-1234", "testuil1@naver.com", User_role.교직원,sqlCreatedAt);
//            em.persist(user4);
//            em.persist(user5);
//            em.persist(user6);
//
//            User user7 = new User(hash, randomSalt, "송근욱", "777777-7777777", "울산광역시 남구", "010-1234-1234", "tesryidt1@naver.com", User_role.학생,sqlCreatedAt);
//            User user8 = new User(hash, randomSalt, "이순신", "888888-8888888", "부산광역시 미남", "010-1234-1234", "tedlfyist2@naver.com", User_role.학생,sqlCreatedAt);
//            User user9 = new User(hash, randomSalt, "안중근", "999999-9999999", "부산광역시 대연동", "010-1234-1234", "tesilfyilt3@naver.com", User_role.학생,sqlCreatedAt);
//            em.persist(user7);
//            em.persist(user8);
//            em.persist(user9);
//
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
//                Student student1 = new Student(24000001, 3, user7, professor1, major1, sqlDate);
//                Student student2 = new Student(24000002, 3, user8, professor2, major2, sqlDate);
//                Student student3 = new Student(24000003, 3, user9, professor3, major3, sqlDate);
//                em.persist(student1);
//                em.persist(student2);
//                em.persist(student3);
//
//                Situation situation1 = new Situation(student1, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
//                Situation situation2 = new Situation(student2, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
//                Situation situation3 = new Situation(student3, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
//
//                em.persist(situation1);
//                em.persist(situation2);
//                em.persist(situation3);
//
//                SituationRecord record1 = new SituationRecord(Status_type.재학,student1,situation1.getStart_date(),null);
//                SituationRecord record2 = new SituationRecord(Status_type.재학,student2,situation2.getStart_date(),null);
//                SituationRecord record3 = new SituationRecord(Status_type.재학,student3,situation3.getStart_date(),null);
//
//                em.persist(record1);
//                em.persist(record2);
//                em.persist(record3);
//
//                Payments payments = new Payments(student1, "2023학년 1학기");
//                Payments payments2 = new Payments(student2,"2023학년 1학기");
//                Payments payments3 = new Payments(student3,"2023학년 1학기");
//
//                Payments payments4 = new Payments(student1, "2023학년 2학기");
//                Payments payments5 = new Payments(student2,"2023학년 2학기");
//                Payments payments6 = new Payments(student3,"2023학년 2학기");
//
//                Payments payments7 = new Payments(student1, "2024학년 1학기");
//                Payments payments8 = new Payments(student2,"2024학년 1학기");
//                Payments payments9 = new Payments(student3,"2024학년 1학기");
//
//
//                em.persist(payments);
//                em.persist(payments2);
//                em.persist(payments3);
//                em.persist(payments4);
//                em.persist(payments5);
//                em.persist(payments6);
//                em.persist(payments7);
//                em.persist(payments8);
//                em.persist(payments9);
//
//
//                Scholarship scholarship1 = new Scholarship("내부", "성적우수장학금", 1000000, 2023, 1);
//                em.persist(scholarship1);
//                Scholarship scholarship2 = new Scholarship("내부", "근로장학금", 2000000, 2023, 2);
//                em.persist(scholarship2);
//                Scholarship scholarship3 = new Scholarship("외부", "국가장학금", 300000, 2023, 3);
//                em.persist(scholarship3);
//                Scholarship scholarship4 = new Scholarship("내부", "성적우수장학금", 1000000, 2024, 1);
//                em.persist(scholarship4);
//                Scholarship scholarship5 = new Scholarship("내부", "근로장학금", 2000000, 2024, 2);
//                em.persist(scholarship5);
//                Scholarship scholarship6 = new Scholarship("외부", "국가장학금", 300000, 2024, 3);
//                em.persist(scholarship6);
//
//
//                Scholarship_Award scholarshipAward1 = new Scholarship_Award(student1,scholarship1);
//                Scholarship_Award scholarshipAward2 = new Scholarship_Award(student1,scholarship2);
//                Scholarship_Award scholarshipAward3 = new Scholarship_Award(student1,scholarship3);
//                Scholarship_Award scholarshipAward4 = new Scholarship_Award(student2,scholarship1);
//                Scholarship_Award scholarshipAward5 = new Scholarship_Award(student2,scholarship2);
//                Scholarship_Award scholarshipAward6 = new Scholarship_Award(student3,scholarship1);
//                Scholarship_Award scholarshipAward7 = new Scholarship_Award(student1,scholarship4);
//                Scholarship_Award scholarshipAward8 = new Scholarship_Award(student1,scholarship5);
//                Scholarship_Award scholarshipAward9 = new Scholarship_Award(student1,scholarship6);
//                Scholarship_Award scholarshipAward10 = new Scholarship_Award(student2,scholarship4);
//                Scholarship_Award scholarshipAward11 = new Scholarship_Award(student2,scholarship5);
//                Scholarship_Award scholarshipAward12 = new Scholarship_Award(student3,scholarship6);
//
//                em.persist(scholarshipAward1);
//                em.persist(scholarshipAward2);
//                em.persist(scholarshipAward3);
//                em.persist(scholarshipAward4);
//                em.persist(scholarshipAward5);
//                em.persist(scholarshipAward6);
//                em.persist(scholarshipAward7);
//                em.persist(scholarshipAward8);
//                em.persist(scholarshipAward9);
//                em.persist(scholarshipAward10);
//                em.persist(scholarshipAward11);
//                em.persist(scholarshipAward12);
//
//
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//
//        public void insertCalendar() throws Exception {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//
//            LocalDate startDate1 = LocalDate.parse("2024/01/01", formatter);
//            LocalDate endDate1 = LocalDate.parse("2024/01/31", formatter);
//
//            AcademicCalendar calendar1 = new AcademicCalendar("학기 시작", startDate1, endDate1);
//            em.persist(calendar1);
//
//            LocalDate startDate2 = LocalDate.parse("2024/06/21", formatter);
//            LocalDate endDate2 = LocalDate.parse("2024/09/01", formatter);
//
//            AcademicCalendar calendar2 = new AcademicCalendar("여름 방학 시작", startDate2, endDate2);
//            em.persist(calendar2);
//
//            LocalDate startDate3 = LocalDate.parse("2024/12/01", formatter);
//            LocalDate endDate3 = LocalDate.parse("2025/03/01", formatter);
//
//            AcademicCalendar calendar3 = new AcademicCalendar("겨울 방학 시작", startDate3, endDate3);
//            em.persist(calendar3);
//        }
//
//        public void dbInit() {
//            Lecture lecture= new Lecture("정치학개론", "정치학의 기본적인 이론과 원칙, 그리고 주요한 주제들에 대해서 다룹니다.", 3, "월,수,금", "09:00,15:30,09:00", "12:00,17:30,12:00", Lecture_Type.전공필수,  em.find(Professor.class, 1L), 30, "2022년 1학기", 1, null, em.find(Major.class, 2L), em.find(LectureRoom.class, 1L));
//            em.persist(lecture);
//
//            Lecture lecture2 = new Lecture("지리학개론", "지리학의 기본 개념과 원리를 소개합니다.", 3, "월,수,금", "10:00,14:30,09:00", "12:00,16:30,11:00", Lecture_Type.전공필수,  em.find(Professor.class, 1L), 30, "2024년 2학기", 1, null, em.find(Major.class, 1L), em.find(LectureRoom.class, 1L));
//            em.persist(lecture2);
//
//            Lecture lecture3 = new Lecture("군사학개론", "군사학의 기본적인 이론과 원칙을 학습합니다.", 3, "월,금", "13:00,16:00", "15:00,18:00", Lecture_Type.전공선택,  em.find(Professor.class, 1L), 30,  "2023년 1학기", 1, null, em.find(Major.class, 3L), em.find(LectureRoom.class, 1L));
//            em.persist(lecture3);
//
//            Lecture lecture4 = new Lecture("수학교육론", "수학 교육의 이론과 방법에 대해 다룹니다.", 3, "월,목,금", "09:00,15:30,9:00", "12:00,17:30,12:00", Lecture_Type.선택교양,  em.find(Professor.class, 2L), 30, "2022년 1학기", 1, null, em.find(Major.class, 4L), em.find(LectureRoom.class, 2L));
//            em.persist(lecture4);
//
//            Lecture lecture5 = new Lecture("사회학", "사회의 기본 구조와 기능, 그리고 사회 현상을 이해하는 방법을 다룹니다.", 3, "수,금", "15:30,09:00", "17:30,12:00", Lecture_Type.전공선택,  em.find(Professor.class, 2L), 30, "2022년 2학기", 1, null, em.find(Major.class, 5L), em.find(LectureRoom.class, 2L));
//            em.persist(lecture5);
//
//            Lecture lecture6 = new Lecture("국어교육론", " 국어 교육의 이론과 방법에 대해 다룹니다.", 3, "월,목", "09:00,09:00", "12:00,12:00", Lecture_Type.전공필수,  em.find(Professor.class, 2L), 30, "2023년 2학기", 1, null, em.find(Major.class, 6L), em.find(LectureRoom.class, 2L));
//            em.persist(lecture6);
//
//            Lecture lecture7 = new Lecture("신경과학개론", "신경계의 기본 구조와 기능에 대해 배웁니다.", 3, "월,수,금", "09:00,15:30,09:00", "12:00,17:30,12:00", Lecture_Type.전공필수,  em.find(Professor.class, 3L), 30,  "2024년 1학기", 1, null, em.find(Major.class, 7L), em.find(LectureRoom.class, 3L));
//            em.persist(lecture7);
//
//            Lecture lecture8 = new Lecture("정신과학개론", "정신과학의 기본 원리와 개념에 대해 배웁니다.", 3, "수,금", "15:30,15:00", "17:30,15:00", Lecture_Type.선택교양,  em.find(Professor.class, 3L), 30, "2022년 2학기", 1, null, em.find(Major.class, 8L), em.find(LectureRoom.class, 3L));
//            em.persist(lecture8);
//
//            Lecture lecture9 = new Lecture("마취과학개론", "마취의 기본 원리와 마취 약물에 대해 배웁니다.", 3, "월,화,금", "09:00,15:30,09:00", "12:00,17:30,12:00", Lecture_Type.전공필수, em.find(Professor.class, 3L), 30, "2024년 1학기", 1, null, em.find(Major.class, 9L), em.find(LectureRoom.class, 3L));
//            em.persist(lecture9);
//
//
//
//            Notice notice1 = new Notice("공지사항", "공지사항 내용이에요.");
//            em.persist(notice1);
//
//            Notice notice2 = new Notice("두번째 공지사항", "두번째 공지사항이에요.");
//            em.persist(notice2);
//
//        }
//
//
//
//        private final String[] LAST_NAMES = {"성준", "서연", "지훈", "지우", "은서", "도윤", "서현", "준우", "아름", "윤서",
//                "지민", "민준", "서윤", "하준", "지윤", "지후", "다은", "하은", "민서", "우진"};
//        private final String[] FIRST_NAMES = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임"};
//
//        public void generateDummyData() {
//            int startingStudentNum = 24000004;
//
//            for (int i = 0; i < 100; i++) {
//                int studentNum = startingStudentNum + i;
//
//
//                String randomSalt = hashComponent.getRandomSalt();
//                String hash = hashComponent.getHash("1234", randomSalt);
//
//
//                java.sql.Date sqlCreatedAt = java.sql.Date.valueOf(generateRandomDate());
//
//                User user1 = new User(hash, randomSalt, generateRandomName(), generateRandomResidentNumber(), generateRandomKoreanAddress(), generateRandomPhoneNumber(), generateRandomString(8) + i + "@example.com", User_role.교수, sqlCreatedAt);
//                em.persist(user1);
//
//                User user2 = new User(hash, randomSalt, generateRandomName(), generateRandomResidentNumber(), generateRandomKoreanAddress(), generateRandomPhoneNumber(), generateRandomString(8) + i + "@example.com", User_role.교직원, sqlCreatedAt);
//                em.persist(user2);
//
//                User user3 = new User(hash, randomSalt, generateRandomName(), generateRandomResidentNumber(), generateRandomKoreanAddress(), generateRandomPhoneNumber(), generateRandomString(8) + i + "@example.com", User_role.학생, sqlCreatedAt);
//                em.persist(user3);
//
//
//                Random random = new Random();
//                Integer count = em.createQuery("SELECT COUNT(e) FROM Lecture e", Integer.class).getSingleResult();
//                long majorId = random.nextInt(count) + 1; // 1부터 n까지의 랜덤값 생성
//                Major major = em.find(Major.class, majorId);
//
//
//                try {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//                    Date utilDate = sdf.parse("2024/11/02");
//                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//                    Professor professor = new Professor("hi", user1, major, sqlDate);
//                    em.persist(professor);
//                    Manager manager = new Manager("hi", user2, sqlDate);
//                    em.persist(manager);
//
//                    if (i == 7 || i == 43 || i == 47 || i == 65 || i == 70) {
//
//                        Date parsedDate = sdf.parse("2024/03/04");
//                        java.sql.Date sqlparsedDate = new java.sql.Date(parsedDate.getTime());
//                        Student student = new Student(studentNum, 1, user3, professor, major, sqlDate);
//                        em.persist(student);
//                        Situation situation = new Situation(student, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
//                        em.persist(situation);
//
//                        SituationRecord record = new SituationRecord(Status_type.입학, student, situation.getStart_date(), null);
//                        em.persist(record);
//                        Payments payments = new Payments(student, "2024학년 1학기");
//                        em.persist(payments);
//
//
//                    } else if (10 <= i && i <= 40 && i % 2 != 0) {
//                        Student student = new Student(studentNum, 1, user3, professor, major, sqlDate);
//                        em.persist(student);
//                        Situation situation = new Situation(student, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
//                        em.persist(situation);
//
//                        SituationRecord record = new SituationRecord(Status_type.입학, student, situation.getStart_date(), null);
//                        em.persist(record);
//                        Payments payments = new Payments(student, "2024학년 1학기");
//                        em.persist(payments);
//
//
//                    } else if (10 <= i && i <= 40 && i % 2 == 0) {
//                        Student student = new Student(studentNum, 1, user3, professor, major, sqlDate);
//                        em.persist(student);
//                        Situation situation = new Situation(student, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
//                        em.persist(situation);
//
//                        SituationRecord record = new SituationRecord(Status_type.입학, student, situation.getStart_date(), null);
//                        em.persist(record);
//                        Payments payments = new Payments(student, "2024학년 1학기");
//                        em.persist(payments);
//
//
//                    } else if (75 <= i && i % 2 == 0) {
//                        Student student = new Student(studentNum, 1, user3, professor, major, sqlDate);
//                        em.persist(student);
//                        Situation situation = new Situation(student, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
//                        em.persist(situation);
//
//                        SituationRecord record = new SituationRecord(Status_type.입학, student, situation.getStart_date(), null);
//                        em.persist(record);
//                        Payments payments = new Payments(student, "2024학년 1학기");
//                        em.persist(payments);
//
//                    } else {
//                        Student student = new Student(studentNum, 1, user3, professor, major, sqlDate);
//                        em.persist(student);
//                        Situation situation = new Situation(student, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
//                        em.persist(situation);
//
//                        SituationRecord record = new SituationRecord(Status_type.입학, student, situation.getStart_date(), null);
//                        em.persist(record);
//                        Payments payments = new Payments(student, "2024학년 1학기");
//                        em.persist(payments);
//
//                    }
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//        private String generateRandomPhoneNumber() {
//            StringBuilder sb = new StringBuilder();
//            Random random = new Random();
//
//            // Append '010' as the first part of the phone number
//            sb.append("010-");
//
//            // Append remaining 8 digits with '-' after the third and sixth digit
//            for (int i = 0; i < 8; i++) {
//                if (i == 4) {
//                    sb.append("-");
//                }
//                sb.append(random.nextInt(10));
//            }
//
//            return sb.toString();
//        }
//
//        private String generateRandomResidentNumber() {
//            StringBuilder sb = new StringBuilder();
//            Random random = new Random();
//
//            // Append 6 digits for birth date
//            for (int i = 0; i < 6; i++) {
//                sb.append(random.nextInt(10));
//            }
//            sb.append("-");
//
//            // Append 7 digits for unique number
//            for (int i = 0; i < 7; i++) {
//                sb.append(random.nextInt(10));
//            }
//
//            // Increment the last 3 digits from 001
//            int increment = 1 + random.nextInt(998); // Random number from 1 to 998
//            sb.replace(sb.length() - 3, sb.length(), String.format("%03d", increment));
//
//            return sb.toString();
//        }
//
//
//
//        private String generateRandomString(int length) {
//            StringBuilder sb = new StringBuilder();
//            Random random = new Random();
//            String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
//
//            for (int i = 0; i < length; i++) {
//                int index = random.nextInt(characters.length());
//                sb.append(characters.charAt(index));
//            }
//
//            return sb.toString();
//        }
//        private String generateRandomKoreanAddress() {
//            String[] cities = {"서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "세종특별자치시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도"};
//            String[] districts = {"강남구", "강동구", "강서구", "강북구", "서초구", "송파구", "용산구", "마포구", "중구", "종로구", "성동구", "광진구", "은평구", "도봉구", "동대문구", "중랑구", "관악구", "서대문구", "영등포구", "금천구", "구로구", "양천구", "노원구"};
//            String[] neighborhoods = {"역삼동", "삼성동", "청담동", "논현동", "서초동", "반포동", "잠실동", "신사동", "압구정동", "신천동", "서초동", "잠실동", "영등포동", "고덕동", "천호동", "길동", "명일동", "성내동", "강일동", "상일동", "오륜동", "개포동", "세곡동"};
//
//            Random random = new Random();
//            String city = cities[random.nextInt(cities.length)];
//            String district = districts[random.nextInt(districts.length)];
//            String neighborhood = neighborhoods[random.nextInt(neighborhoods.length)];
//
//            return city + " " + district + " " + neighborhood;
//        }
//
//
//        private String generateRandomName() {
//            Random random = new Random();
//            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
//            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
//            return firstName + lastName;
//        }
//
//        private LocalDate generateRandomDate() {
//            Random random = new Random();
//            long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
//            long maxDay = LocalDate.now().toEpochDay();
//            long randomDay = minDay + random.nextLong() % (maxDay - minDay + 1);
//            return LocalDate.ofEpochDay(randomDay);
//        }
//
//
//    }
//}
