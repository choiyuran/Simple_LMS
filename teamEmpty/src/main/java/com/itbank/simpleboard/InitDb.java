package com.itbank.simpleboard;

import com.itbank.simpleboard.component.HashComponent;
import com.itbank.simpleboard.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        try {
            initService.dbInit6(); // 1. 단과대학 + 강의실 + 학과
            initService.generateDummyData(); // user (교수, 학생, 교직원) - 등록금
            initService.insertCalendar(); // 학사 일정
            initService.insertNotice(); // 공지사항
/*            initService.dbInit4(); // 안씀; */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final HashComponent hashComponent;

        public void dbInit6() throws Exception{
            College college1 = new College("인문대학", "인문관");
            College college2 = new College("사회과학대학", "사회과학관");
            College college3 = new College("자연과학대학", "자연과학관");
            College college4 = new College("간호대학", "간호학관");
            College college5 = new College("경영대학", "경영학관");
            College college6 = new College("공과대학", "공학관");
            College college7 = new College("농업생명과학대학", "자연과학관");
            College college8 = new College("미술대학", "예술관");
            College college9 = new College("사범대학", "사범관");
            College college10 = new College("생활과학대학", "인문관");
            College college11 = new College("수의과대학", "의학관");
            College college12 = new College("약학대학", "의학관");
            College college13 = new College("음악대학", "예술관");
            College college14 = new College("의과대학", "의학관");
            College college15 = new College("자유전공학부", "인문관");
            College college16 = new College("첨단융합학부", "첨단과학관");
            em.persist(college1);
            em.persist(college2);
            em.persist(college3);
            em.persist(college4);
            em.persist(college5);
            em.persist(college6);
            em.persist(college7);
            em.persist(college8);
            em.persist(college9);
            em.persist(college10);
            em.persist(college11);
            em.persist(college12);
            em.persist(college13);
            em.persist(college14);
            em.persist(college15);
            em.persist(college16);

            // makeLectureRoom 돌리면 1동 당 9개의 강의실 생김
            // 계산 기준 학과 * 3 해서 많은 쪽으로
            // c1 14개 학과 45개
            makeLectureRoom(5, college1);
            // c2 9개 학과 27개
            makeLectureRoom(3, college2);
            // c3 7개 학과 27개
            makeLectureRoom(3, college3);
            // c4 1개 학과 9개
            makeLectureRoom(1, college4);
            // c5 1개 학과 9개
            makeLectureRoom(1, college5);
            // c6 12개 학과 36개
            makeLectureRoom(4, college6);
            // c7 7개 학과 27개
            makeLectureRoom(3, college7);
            // c8 5개 학과 18개
            makeLectureRoom(3, college8);
            // c9 15개 학과 45개
            makeLectureRoom(5, college9);
            // c10 4개 학과 18개
            makeLectureRoom(3, college10);
            // c11 2개 학과 9개
            makeLectureRoom(1,college11);
            // c12 2개 학과 9개
            makeLectureRoom(1, college12);
            // c13 6개 학과 18개
            makeLectureRoom(3, college13);
            // c14 2개 학과 9개
            makeLectureRoom(1, college14);
            // c15 1개 학과 9개
            makeLectureRoom(1, college15);
            // c16 1개 학과 9개
            makeLectureRoom(1, college16);


            Major c1Major1 = new Major("국어국문학과", 3500000, college1);
            Major c1Major2 = new Major("중어중문학과", 2900000, college1);
            Major c1Major3 = new Major("영어영문학과", 3700000, college1);
            Major c1Major4 = new Major("불어불문학과", 3700000, college1);
            Major c1Major5 = new Major("독어독문학과", 3200000, college1);
            Major c1Major6 = new Major("노어노문학과", 3200000, college1);
            Major c1Major7 = new Major("서어서문학과", 3100000, college1);
            Major c1Major8 = new Major("아시아언어문명학부", 3000000, college1);
            Major c1Major9 = new Major("언어학과", 2800000, college1);
            Major c1Major10 = new Major("역사학부", 3000000, college1);
            Major c1Major11 = new Major("철학과", 2800000, college1);
            Major c1Major12 = new Major("종교학과", 3000000, college1);
            Major c1Major13 = new Major("미학과", 4000000, college1);
            Major c1Major14 = new Major("고고미술사학과", 4100000, college1);
            em.persist(c1Major1);
            em.persist(c1Major2);
            em.persist(c1Major3);
            em.persist(c1Major4);
            em.persist(c1Major5);
            em.persist(c1Major6);
            em.persist(c1Major7);
            em.persist(c1Major8);
            em.persist(c1Major9);
            em.persist(c1Major10);
            em.persist(c1Major11);
            em.persist(c1Major12);
            em.persist(c1Major13);
            em.persist(c1Major14);


            Major c2Major1 = new Major("정치외교학부(정치학전공)", 2900000, college2);
            Major c2Major2 = new Major("정치외교학부(외교학전공)", 2800000, college2);
            Major c2Major3 = new Major("경제학부", 3000000, college2);
            Major c2Major4 = new Major("사회학과", 3100000, college2);
            Major c2Major5 = new Major("인류학과", 3200000, college2);
            Major c2Major6 = new Major("심리학과", 3300000, college2);
            Major c2Major7 = new Major("지리학과", 3400000, college2);
            Major c2Major8 = new Major("사회복지학과", 3500000, college2);
            Major c2Major9 = new Major("언론정보학과", 3000000, college2);
            em.persist(c2Major1);
            em.persist(c2Major2);
            em.persist(c2Major3);
            em.persist(c2Major4);
            em.persist(c2Major5);
            em.persist(c2Major6);
            em.persist(c2Major7);
            em.persist(c2Major8);
            em.persist(c2Major9);

            Major c3Major1 = new Major("수리과학부", 5500000, college3);
            Major c3Major2 = new Major("통계학과", 5700000, college3);
            Major c3Major3 = new Major("물리천문학부(물리학전공)", 5200000, college3);
            Major c3Major4 = new Major("물리천문학부(천문학전공)", 5200000, college3);
            Major c3Major5 = new Major("화학부", 5200000, college3);
            Major c3Major6 = new Major("생명과학부", 5200000, college3);
            Major c3Major7 = new Major("지구환경과학부", 5200000, college3);
            em.persist(c3Major1);
            em.persist(c3Major2);
            em.persist(c3Major3);
            em.persist(c3Major4);
            em.persist(c3Major5);
            em.persist(c3Major6);
            em.persist(c3Major7);

            Major c4Major1 = new Major("간호학과", 5200000, college4);
            em.persist(c4Major1);

            Major c5Major1 = new Major("경영학과", 5200000, college5);
            em.persist(c5Major1);

            Major c6Major1 = new Major("건설환경공학부", 5200000, college6);
            Major c6Major2 = new Major("기계공학부", 5200000, college6);
            Major c6Major3 = new Major("항공우주공학부", 5200000, college6);
            Major c6Major4 = new Major("재료공학부", 5200000, college6);
            Major c6Major5 = new Major("전기·정보공학부", 5200000, college6);
            Major c6Major6 = new Major("컴퓨터공학부", 5200000, college6);
            Major c6Major7 = new Major("화학생물공학부", 5200000, college6);
            Major c6Major8 = new Major("건축학과", 5200000, college6);
            Major c6Major9 = new Major("산업공학과", 5200000, college6);
            Major c6Major10 = new Major("에너지자원공학과", 5200000, college6);
            Major c6Major11 = new Major("원자핵공학과", 5200000, college6);
            Major c6Major12 = new Major("조선해양공학과", 5200000, college6);
            em.persist(c6Major1);
            em.persist(c6Major2);
            em.persist(c6Major3);
            em.persist(c6Major4);
            em.persist(c6Major5);
            em.persist(c6Major6);
            em.persist(c6Major7);
            em.persist(c6Major8);
            em.persist(c6Major9);
            em.persist(c6Major10);
            em.persist(c6Major11);
            em.persist(c6Major12);

            Major c7Major1 = new Major("식물생산과학부", 5100000, college7);
            Major c7Major2 = new Major("산림과학부", 5000000, college7);
            Major c7Major3 = new Major("응용생물화학부", 5100000, college7);
            Major c7Major4 = new Major("식품·동물생명공학부", 5200000, college7);
            Major c7Major5 = new Major("바이오시스템·소재학부", 5300000, college7);
            Major c7Major6 = new Major("조경·지역시스템공학부", 5400000, college7);
            Major c7Major7 = new Major("농경제사회학부", 5500000, college7);
            em.persist(c7Major1);
            em.persist(c7Major2);
            em.persist(c7Major3);
            em.persist(c7Major4);
            em.persist(c7Major5);
            em.persist(c7Major6);
            em.persist(c7Major7);

            Major c8Major1 = new Major("동양화과", 5200000, college8);
            Major c8Major2 = new Major("서양화과", 5100000, college8);
            Major c8Major3 = new Major("조소과", 4900000, college8);
            Major c8Major4 = new Major("디자인학부(공예)", 5000000, college8);
            Major c8Major5 = new Major("디자인학부(디자인)", 5500000, college8);
            em.persist(c8Major1);
            em.persist(c8Major2);
            em.persist(c8Major3);
            em.persist(c8Major4);
            em.persist(c8Major5);

            Major c9Major1 = new Major("교육학과", 5200000, college9);
            Major c9Major2 = new Major("국어교육과", 4900000, college9);
            Major c9Major3 = new Major("영어교육과", 4800000, college9);
            Major c9Major4 = new Major("불어교육과", 3700000, college9);
            Major c9Major5 = new Major("독어교육과", 4900000, college9);
            Major c9Major6 = new Major("사회교육과", 3900000, college9);
            Major c9Major7 = new Major("역사교육과", 3900000, college9);
            Major c9Major8 = new Major("지리교육과", 4100000, college9);
            Major c9Major9 = new Major("윤리교육과", 3600000, college9);
            Major c9Major10 = new Major("수학교육과", 4000000, college9);
            Major c9Major11 = new Major("물리교육과", 5100000, college9);
            Major c9Major12 = new Major("화학교육과", 5000000, college9);
            Major c9Major13 = new Major("생물교육과", 3900000, college9);
            Major c9Major14 = new Major("지구과학교육과", 4900000, college9);
            Major c9Major15 = new Major("체육교육과", 3700000, college9);
            em.persist(c9Major1);
            em.persist(c9Major2);
            em.persist(c9Major3);
            em.persist(c9Major4);
            em.persist(c9Major5);
            em.persist(c9Major6);
            em.persist(c9Major7);
            em.persist(c9Major8);
            em.persist(c9Major9);
            em.persist(c9Major10);
            em.persist(c9Major11);
            em.persist(c9Major12);
            em.persist(c9Major13);
            em.persist(c9Major14);
            em.persist(c9Major15);


            Major c10Major1 = new Major("소비자아동학부(소비자학전공)", 2700000, college10);
            Major c10Major2 = new Major("소비자아동학부(아동가족학)", 3100000, college10);
            Major c10Major3 = new Major("식품영양학과", 4400000, college10);
            Major c10Major4 = new Major("의류학과", 4300000, college10);
            em.persist(c10Major1);
            em.persist(c10Major2);
            em.persist(c10Major3);
            em.persist(c10Major4);

            Major c11Major1 = new Major("수의예과", 5200000, college11);
            Major c11Major2 = new Major("수의학과", 5100000, college11);
            em.persist(c11Major1);
            em.persist(c11Major2);

            Major c12Major1 = new Major("약학과", 5300000, college12);
            Major c12Major2 = new Major("제약학과", 5000000, college12);
            em.persist(c12Major1);
            em.persist(c12Major2);

            Major c13Major1 = new Major("성악과", 5100000, college13);
            Major c13Major2 = new Major("작곡과", 5200000, college13);
            Major c13Major3 = new Major("음악학과", 5600000, college13);
            Major c13Major4 = new Major("피아노과", 4900000, college13);
            Major c13Major5 = new Major("관현악과", 3700000, college13);
            Major c13Major6 = new Major("국악과", 4000000, college13);
            em.persist(c13Major1);
            em.persist(c13Major2);
            em.persist(c13Major3);
            em.persist(c13Major4);
            em.persist(c13Major5);
            em.persist(c13Major6);

            Major c14Major1 = new Major("의예과", 5700000, college14);
            Major c14Major2 = new Major("의학과", 5900000, college14);
            em.persist(c14Major1);
            em.persist(c14Major2);

            Major c15Major1 = new Major("자유전공학부", 4500000, college15);
            em.persist(c15Major1);

            Major c16Major1 = new Major("첨단융합학부", 4900000, college16);
            em.persist(c16Major1);
        }

        private void makeLectureRoom(int dong, College college) {
            Random ran = new Random();
            String[] floor = {"1", "2", "3", "4", "5"};
            String[] roomNum = {"1", "2", "3", "4", "5", "6", "7"};
            ArrayList<String> makeRoom = new ArrayList<>();
            for (int i = 1; i <= dong; i++) {
                while (makeRoom.size() < 9) {
                    int floorRan = ran.nextInt(floor.length);
                    int roomRan = ran.nextInt(roomNum.length);
                    String room = i + " " + floor[floorRan] + "0" + roomNum[roomRan] + "호";
                    if (!makeRoom.contains(room)) {
                        makeRoom.add(room);
                        LectureRoom lectureRoom = new LectureRoom(room, college);
                        em.persist(lectureRoom);
                    }
                }
                makeRoom.clear();
            }
        }

        // 이거 안씀
        public void dbInit4() {
            String randomSalt = hashComponent.getRandomSalt();
            String hash = hashComponent.getHash("1234", randomSalt);

            // 날짜 형식 지정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // 2024년 2월 25일로 설정
            LocalDate createdAt = LocalDate.parse("2024-02-25", formatter);
            java.sql.Date sqlCreatedAt = java.sql.Date.valueOf(createdAt);

            College college1 = new College("사범대학", "인문관1");
            College college2 = new College("사회과학대학", "인문관2");
            College college3 = new College("의과대학", "공학관");
            em.persist(college1);
            em.persist(college2);
            em.persist(college3);

            Major major1 = new Major("정치외교학과", 3500000, college1);
            Major major2 = new Major("지리학과", 3200000, college1);
            Major major3 = new Major("군사학과", 3300000, college1);
            em.persist(major1);
            em.persist(major2);
            em.persist(major3);

            Major major4 = new Major("수학교육과", 2900000, college2);
            Major major5 = new Major("사회교육과", 2800000, college2);
            Major major6 = new Major("국어교육과", 3000000, college2);
            em.persist(major4);
            em.persist(major5);
            em.persist(major6);

            Major major7 = new Major("신경과", 5500000, college3);
            Major major9 = new Major("정신과", 5700000, college3);
            Major major8 = new Major("마취과", 5200000, college3);
            em.persist(major7);
            em.persist(major8);
            em.persist(major9);

            LectureRoom lectureRoom1 = new LectureRoom("101", college1);
            LectureRoom lectureRoom2 = new LectureRoom("102", college1);
            LectureRoom lectureRoom3 = new LectureRoom("201", college2);
            LectureRoom lectureRoom4 = new LectureRoom("202", college2);
            LectureRoom lectureRoom5 = new LectureRoom("501", college3);
            LectureRoom lectureRoom6 = new LectureRoom("502", college3);

            em.persist(lectureRoom1);
            em.persist(lectureRoom2);
            em.persist(lectureRoom3);
            em.persist(lectureRoom4);
            em.persist(lectureRoom5);
            em.persist(lectureRoom6);


            User user1 = new User(hash, randomSalt, "정수용", "222222-2222222", "부산광역시 미남", "010-1234-1234", "tesrht2@naver.com", User_role.교수, sqlCreatedAt);
            User user2 = new User(hash, randomSalt, "최유란", "111111-1111111", "울산광역시 남구", "010-1234-1234", "tesagat1@naver.com", User_role.교수, sqlCreatedAt);
            User user3 = new User(hash, randomSalt, "이병길", "333333-3333333", "부산광역시 대연동", "010-1234-1234", "ahtest3@naver.com", User_role.교수, sqlCreatedAt);
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);

            User user5 = new User(hash, randomSalt, "황민우", "555555-5555555", "서울특별시 강북", "010-1234-1234", "teulahst2@naver.com", User_role.교직원, sqlCreatedAt);
            User user6 = new User(hash, randomSalt, "박소은", "666666-6666666", "부산광역시 수영구", "010-1234-1234", "teulhst3@naver.com", User_role.교직원, sqlCreatedAt);
            User user4 = new User(hash, randomSalt, "안지혜", "444444-4444444", "서울특별시 강남", "010-1234-1234", "testuil1@naver.com", User_role.교직원, sqlCreatedAt);
            em.persist(user4);
            em.persist(user5);
            em.persist(user6);

            User user7 = new User(hash, randomSalt, "송근욱", "777777-7777777", "울산광역시 남구", "010-1234-1234", "tesryidt1@naver.com", User_role.학생, sqlCreatedAt);
            User user8 = new User(hash, randomSalt, "이순신", "888888-8888888", "부산광역시 미남", "010-1234-1234", "tedlfyist2@naver.com", User_role.학생, sqlCreatedAt);
            User user9 = new User(hash, randomSalt, "안중근", "999999-9999999", "부산광역시 대연동", "010-1234-1234", "tesilfyilt3@naver.com", User_role.학생, sqlCreatedAt);
            em.persist(user7);
            em.persist(user8);
            em.persist(user9);


            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date utilDate = sdf.parse("2024/11/02");
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                Professor professor1 = new Professor("hi", user1, major1, sqlDate);
                Professor professor2 = new Professor("hi", user2, major2, sqlDate);
                Professor professor3 = new Professor("hi", user3, major3, sqlDate);
                em.persist(professor1);
                em.persist(professor2);
                em.persist(professor3);

                Manager manager1 = new Manager("hi", user4, sqlDate);
                Manager manager2 = new Manager("hi", user5, sqlDate);
                Manager manager3 = new Manager("hi", user6, sqlDate);
                em.persist(manager1);
                em.persist(manager2);
                em.persist(manager3);

                Student student1 = new Student(24000001, 3, user7, professor1, major1, sqlDate);
                Student student2 = new Student(24000002, 3, user8, professor2, major2, sqlDate);
                Student student3 = new Student(24000003, 3, user9, professor3, major3, sqlDate);
                em.persist(student1);
                em.persist(student2);
                em.persist(student3);

                Situation situation1 = new Situation(student1, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
                Situation situation2 = new Situation(student2, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
                Situation situation3 = new Situation(student3, Status_type.재학, new java.sql.Date(new Date().getTime()), null);

                em.persist(situation1);
                em.persist(situation2);
                em.persist(situation3);

                SituationRecord record1 = new SituationRecord(Status_type.재학, student1, situation1.getStart_date(), null);
                SituationRecord record2 = new SituationRecord(Status_type.재학, student2, situation2.getStart_date(), null);
                SituationRecord record3 = new SituationRecord(Status_type.재학, student3, situation3.getStart_date(), null);

                em.persist(record1);
                em.persist(record2);
                em.persist(record3);

                Payments payments = new Payments(student1, "2023학년 1학기");
                Payments payments2 = new Payments(student2, "2023학년 1학기");
                Payments payments3 = new Payments(student3, "2023학년 1학기");

                Payments payments4 = new Payments(student1, "2023학년 2학기");
                Payments payments5 = new Payments(student2, "2023학년 2학기");
                Payments payments6 = new Payments(student3, "2023학년 2학기");

                Payments payments7 = new Payments(student1, "2024학년 1학기");
                Payments payments8 = new Payments(student2, "2024학년 1학기");
                Payments payments9 = new Payments(student3, "2024학년 1학기");


                em.persist(payments);
                em.persist(payments2);
                em.persist(payments3);
                em.persist(payments4);
                em.persist(payments5);
                em.persist(payments6);
                em.persist(payments7);
                em.persist(payments8);
                em.persist(payments9);


                Scholarship scholarship1 = new Scholarship("내부", "성적우수장학금", 1000000, 2023, 1);
                em.persist(scholarship1);
                Scholarship scholarship2 = new Scholarship("내부", "근로장학금", 2000000, 2023, 2);
                em.persist(scholarship2);
                Scholarship scholarship3 = new Scholarship("외부", "국가장학금", 300000, 2023, 3);
                em.persist(scholarship3);
                Scholarship scholarship4 = new Scholarship("내부", "성적우수장학금", 1000000, 2024, 1);
                em.persist(scholarship4);
                Scholarship scholarship5 = new Scholarship("내부", "근로장학금", 2000000, 2024, 2);
                em.persist(scholarship5);
                Scholarship scholarship6 = new Scholarship("외부", "국가장학금", 300000, 2024, 3);
                em.persist(scholarship6);


                Scholarship_Award scholarshipAward1 = new Scholarship_Award(student1, scholarship1);
                Scholarship_Award scholarshipAward2 = new Scholarship_Award(student1, scholarship2);
                Scholarship_Award scholarshipAward3 = new Scholarship_Award(student1, scholarship3);
                Scholarship_Award scholarshipAward4 = new Scholarship_Award(student2, scholarship1);
                Scholarship_Award scholarshipAward5 = new Scholarship_Award(student2, scholarship2);
                Scholarship_Award scholarshipAward6 = new Scholarship_Award(student3, scholarship1);
                Scholarship_Award scholarshipAward7 = new Scholarship_Award(student1, scholarship4);
                Scholarship_Award scholarshipAward8 = new Scholarship_Award(student1, scholarship5);
                Scholarship_Award scholarshipAward9 = new Scholarship_Award(student1, scholarship6);
                Scholarship_Award scholarshipAward10 = new Scholarship_Award(student2, scholarship4);
                Scholarship_Award scholarshipAward11 = new Scholarship_Award(student2, scholarship5);
                Scholarship_Award scholarshipAward12 = new Scholarship_Award(student3, scholarship6);

                em.persist(scholarshipAward1);
                em.persist(scholarshipAward2);
                em.persist(scholarshipAward3);
                em.persist(scholarshipAward4);
                em.persist(scholarshipAward5);
                em.persist(scholarshipAward6);
                em.persist(scholarshipAward7);
                em.persist(scholarshipAward8);
                em.persist(scholarshipAward9);
                em.persist(scholarshipAward10);
                em.persist(scholarshipAward11);
                em.persist(scholarshipAward12);


            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }

        public void insertCalendar() throws Exception {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

            LocalDate startDate1 = LocalDate.parse("2024/03/01", formatter);
            LocalDate endDate1 = LocalDate.parse("2024/03/04", formatter);

            AcademicCalendar calendar1 = new AcademicCalendar("1학기 시작", startDate1, endDate1);
            em.persist(calendar1);

            LocalDate startDate2 = LocalDate.parse("2024/06/21", formatter);
            LocalDate endDate2 = LocalDate.parse("2024/09/01", formatter);

            AcademicCalendar calendar2 = new AcademicCalendar("여름 방학 시작", startDate2, endDate2);
            em.persist(calendar2);

            LocalDate startDate3 = LocalDate.parse("2024/12/01", formatter);
            LocalDate endDate3 = LocalDate.parse("2025/03/01", formatter);

            AcademicCalendar calendar3 = new AcademicCalendar("겨울 방학 시작", startDate3, endDate3);
            em.persist(calendar3);

            LocalDate startDate4 = LocalDate.parse("2024/04/13", formatter);
            LocalDate endDate4 = LocalDate.parse("2025/04/17", formatter);

            AcademicCalendar calendar4 = new AcademicCalendar("센텀 대학 축제", startDate4, endDate4);
            em.persist(calendar4);
        }

        public void insertNotice() {

            Notice notice1 = new Notice("공지사항", "공지사항 내용이에요.");
            em.persist(notice1);

            Notice notice2 = new Notice("두번째 공지사항", "두번째 공지사항이에요.");
            em.persist(notice2);

        }


        private final String[] LAST_NAMES = {"성준", "서연", "지훈", "지우", "은서", "도윤", "서현", "준우", "아름", "윤서",
                "지민", "민준", "서윤", "하준", "지윤", "지후", "다은", "하은", "민서", "우진"};
        private final String[] FIRST_NAMES = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임"};

        public void generateDummyData() {
            int startingStudentNum = 24000004;

            for (int i = 0; i < 100; i++) {
                int studentNum = startingStudentNum + i;


                String randomSalt = hashComponent.getRandomSalt();
                String hash = hashComponent.getHash("1234", randomSalt);


                java.sql.Date sqlCreatedAt = java.sql.Date.valueOf(generateRandomDate());

                User user1 = new User(hash, randomSalt, generateRandomName(), generateRandomResidentNumber(), generateRandomKoreanAddress(), generateRandomPhoneNumber(), generateRandomString(8) + i + "@example.com", User_role.교수, sqlCreatedAt);
                em.persist(user1);

                User user2 = new User(hash, randomSalt, generateRandomName(), generateRandomResidentNumber(), generateRandomKoreanAddress(), generateRandomPhoneNumber(), generateRandomString(8) + i + "@example.com", User_role.교직원, sqlCreatedAt);
                em.persist(user2);

                User user3 = new User(hash, randomSalt, generateRandomName(), generateRandomResidentNumber(), generateRandomKoreanAddress(), generateRandomPhoneNumber(), generateRandomString(8) + i + "@example.com", User_role.학생, sqlCreatedAt);
                em.persist(user3);


                Random random = new Random();
                Long count = em.createQuery("SELECT COUNT(e) FROM Major e", Long.class).getSingleResult();
                Long majorId = Math.abs(random.nextLong()) % count + 1L;; // 1부터 n까지의 랜덤값 생성
                Major major = em.find(Major.class, majorId);


                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Date utilDate = sdf.parse("2024/11/02");
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    Professor professor = new Professor("hi", user1, major, sqlDate);
                    em.persist(professor);
                    Manager manager = new Manager("hi", user2, sqlDate);
                    em.persist(manager);

                    if (i == 7 || i == 43 || i == 47 || i == 65 || i == 70) {

                        Date parsedDate = sdf.parse("2024/03/04");
                        java.sql.Date sqlparsedDate = new java.sql.Date(parsedDate.getTime());
                        Student student = new Student(studentNum, 1, user3, professor, major, sqlDate);
                        em.persist(student);
                        Situation situation = new Situation(student, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
                        em.persist(situation);

                        SituationRecord record = new SituationRecord(Status_type.입학, student, situation.getStart_date(), null);
                        em.persist(record);
                        Payments payments = new Payments(student, "2024학년 1학기");
                        em.persist(payments);


                    } else if (10 <= i && i <= 40 && i % 2 != 0) {
                        Student student = new Student(studentNum, 1, user3, professor, major, sqlDate);
                        em.persist(student);
                        Situation situation = new Situation(student, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
                        em.persist(situation);

                        SituationRecord record = new SituationRecord(Status_type.입학, student, situation.getStart_date(), null);
                        em.persist(record);
                        Payments payments = new Payments(student, "2024학년 1학기");
                        em.persist(payments);


                    } else if (10 <= i && i <= 40 && i % 2 == 0) {
                        Student student = new Student(studentNum, 1, user3, professor, major, sqlDate);
                        em.persist(student);
                        Situation situation = new Situation(student, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
                        em.persist(situation);

                        SituationRecord record = new SituationRecord(Status_type.입학, student, situation.getStart_date(), null);
                        em.persist(record);
                        Payments payments = new Payments(student, "2024학년 1학기");
                        em.persist(payments);


                    } else if (75 <= i && i % 2 == 0) {
                        Student student = new Student(studentNum, 1, user3, professor, major, sqlDate);
                        em.persist(student);
                        Situation situation = new Situation(student, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
                        em.persist(situation);

                        SituationRecord record = new SituationRecord(Status_type.입학, student, situation.getStart_date(), null);
                        em.persist(record);
                        Payments payments = new Payments(student, "2024학년 1학기");
                        em.persist(payments);

                    } else {
                        Student student = new Student(studentNum, 1, user3, professor, major, sqlDate);
                        em.persist(student);
                        Situation situation = new Situation(student, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
                        em.persist(situation);

                        SituationRecord record = new SituationRecord(Status_type.입학, student, situation.getStart_date(), null);
                        em.persist(record);
                        Payments payments = new Payments(student, "2024학년 1학기");
                        em.persist(payments);

                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            // 장학금
            Random random = new Random();
            for (int year = 2021; year <= 2024; year++) {   // 2021년 1학기~20242학기까지 반복 생성
                for (int quarter = 1; quarter <= 2; quarter++) {
                    // 내부 장학금 생성
                    Scholarship scholarship1 = new Scholarship("내부", "성적우수장학금", 1000000, year, quarter);
                    em.persist(scholarship1);
                    Scholarship scholarship2 = new Scholarship("내부", "교내근로장학금", 240500, year, quarter);
                    em.persist(scholarship2);
                    Scholarship scholarship3 = new Scholarship("내부", "맞춤형장학금", 800000, year, quarter);
                    em.persist(scholarship3);
                    Scholarship scholarship4 = new Scholarship("내부", "생활지원근로장학금", 332400, year, quarter);
                    em.persist(scholarship4);

                    // 외부 장학금 생성
                    Scholarship scholarship5 = new Scholarship("외부", "센텀대학교발전재단장학금", 2500000, year, quarter);
                    em.persist(scholarship5);
                    Scholarship scholarship6 = new Scholarship("외부", "선한인재장학금", 300000, year, quarter);
                    em.persist(scholarship6);
                    Scholarship scholarship7 = new Scholarship("외부", "해외수학장학금", 1500000, year, quarter);
                    em.persist(scholarship7);
                    Scholarship scholarship8 = new Scholarship("외부", "생활지원장학금", 2000000, year, quarter);
                    em.persist(scholarship8);
                    Scholarship scholarship9 = new Scholarship("외부", "국가장학금(유형1)", 3433000, year, quarter);
                    em.persist(scholarship9);
                    Scholarship scholarship10 = new Scholarship("외부", "국가장학금(지역인재)", 2250000, year, quarter);
                    em.persist(scholarship10);
                    Scholarship scholarship11 = new Scholarship("외부", "국가장학금(다자녀)", 2600000, year, quarter);
                    em.persist(scholarship11);
                    Scholarship scholarship12 = new Scholarship("외부", "교외장학금", 500000, year, quarter);
                    em.persist(scholarship12);
                    Scholarship scholarship13 = new Scholarship("외부", "학업장려금", 145000, year, quarter);
                    em.persist(scholarship13);

                    // 장학금 리스트
                    List<Scholarship> scholarships = Arrays.asList(scholarship1, scholarship2, scholarship3, scholarship4,
                            scholarship5, scholarship6, scholarship7, scholarship8, scholarship9, scholarship10,
                            scholarship11, scholarship12, scholarship13);
                    List<Student> students = new ArrayList<>();
                    for (long i = 1L; i <= 100L; i++) {
                        students.add(em.find(Student.class, i));
                    }

                    // 학생에게 장학금 랜덤하게 수여
                    for (Student student : students) {
                        // 장학금 랜덤 선택
                        int randomIndex = random.nextInt(scholarships.size());
                        Scholarship scholarship = scholarships.get(randomIndex);

                        // 학생리스트에 속한 학생은 2021 1학기부터 2024 2학기까지 랜덤 장학금을 수여받음
                        // 장학금 리스트 중 랜덤하게 장학금 선택하는 Scholarship_Award 생성 후 영속성 컨텍스트에 저장
                        Scholarship_Award scholarshipAward = new Scholarship_Award(student, scholarship);
                        em.persist(scholarshipAward);
                    }

                }
            }
        }

        private String generateRandomPhoneNumber() {
            StringBuilder sb = new StringBuilder();
            Random random = new Random();

            // Append '010' as the first part of the phone number
            sb.append("010-");

            // Append remaining 8 digits with '-' after the third and sixth digit
            for (int i = 0; i < 8; i++) {
                if (i == 4) {
                    sb.append("-");
                }
                sb.append(random.nextInt(10));
            }

            return sb.toString();
        }

        private String generateRandomResidentNumber() {
            StringBuilder sb = new StringBuilder();
            Random random = new Random();

            // Append 6 digits for birth date
            for (int i = 0; i < 6; i++) {
                sb.append(random.nextInt(10));
            }
            sb.append("-");

            // Append 7 digits for unique number
            for (int i = 0; i < 7; i++) {
                sb.append(random.nextInt(10));
            }

            // Increment the last 3 digits from 001
            int increment = 1 + random.nextInt(998); // Random number from 1 to 998
            sb.replace(sb.length() - 3, sb.length(), String.format("%03d", increment));

            return sb.toString();
        }


        private String generateRandomString(int length) {
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

            for (int i = 0; i < length; i++) {
                int index = random.nextInt(characters.length());
                sb.append(characters.charAt(index));
            }

            return sb.toString();
        }

        private String generateRandomKoreanAddress() {
            String[] cities = {"서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "세종특별자치시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도"};
            String[] districts = {"강남구", "강동구", "강서구", "강북구", "서초구", "송파구", "용산구", "마포구", "중구", "종로구", "성동구", "광진구", "은평구", "도봉구", "동대문구", "중랑구", "관악구", "서대문구", "영등포구", "금천구", "구로구", "양천구", "노원구"};
            String[] neighborhoods = {"역삼동", "삼성동", "청담동", "논현동", "서초동", "반포동", "잠실동", "신사동", "압구정동", "신천동", "서초동", "잠실동", "영등포동", "고덕동", "천호동", "길동", "명일동", "성내동", "강일동", "상일동", "오륜동", "개포동", "세곡동"};

            Random random = new Random();
            String city = cities[random.nextInt(cities.length)];
            String district = districts[random.nextInt(districts.length)];
            String neighborhood = neighborhoods[random.nextInt(neighborhoods.length)];

            return city + " " + district + " " + neighborhood;
        }


        private String generateRandomName() {
            Random random = new Random();
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            return firstName + lastName;
        }

        private LocalDate generateRandomDate() {
            Random random = new Random();
            long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
            long maxDay = LocalDate.now().toEpochDay();
            long randomDay = minDay + random.nextLong() % (maxDay - minDay + 1);
            return LocalDate.ofEpochDay(randomDay);
        }


    }
}
