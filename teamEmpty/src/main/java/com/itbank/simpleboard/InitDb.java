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

            // makeLectureRoom 돌리면 1동 당 9개의 강의실 생김
            // 계산 기준 학과 * 3 해서 많은 쪽으로
            // Map을 반환하는 거 삭제 => 강의에 사람도 넣어야 하는데 사람 만드는게 다른 메서드라서 어차피 메서드 분리해야했네
            
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
            makeLectureRoom(1, college11);
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

        private void addLecture() {
            List<String> m1LectureName = new ArrayList<>();
            m1LectureName.add("(필)한국어연구입문(Introduction to Korean Linguistics)");
            m1LectureName.add("한국어문학연구입문(Introduction to Korean Literature");
            m1LectureName.add("한국문학과 한국사회 (Korean Liturature and Society)");
            m1LectureName.add("한국어음운론 (Korean Phonology)");
            m1LectureName.add("한국고전시가강독 (Readings in Classical Korean Poetry)");
            m1LectureName.add("한국현대희곡론 (Korean Modern Drama)");
            m1LectureName.add("(필)한국고전문학사 (History of Classical Korean Literature)");
            m1LectureName.add("(필)한국현대문학사 (History of Korean Modern Literature)");
            m1LectureName.add("한국고전산문강독 (Readings in Classical Korean Prose)");
            m1LectureName.add("(필)한국어의역사 (History of Korean Language)");
            m1LectureName.add("한국어문법론 (Studies in Korean Grammar)");
            m1LectureName.add("한국고전소설론 (Classical Korean Novel)");
            m1LectureName.add("한국현대시론 (Korean Modern Poetry)");
            m1LectureName.add("한국현대시인론 (Korean Modern Poets)");
            m1LectureName.add("한국현대소설론 (Korean Modern Novel)");
            m1LectureName.add("한국현대작가론 (Korean Modern Authors)");
            m1LectureName.add("한국한문학론 (Studies in Sino－Korean Literature)");
            m1LectureName.add("한국고전시가론 (Classical Korean Poetry)");
            m1LectureName.add("한국어 정보의 전산처리 (Computational Treatment of Korean Language Information)");
            m1LectureName.add("한국어방언학 (Korean Dialectology)");
            m1LectureName.add("한국어학사 (History of Korean Linguistics)");
            m1LectureName.add("한국어의미론 (Studies in Korean Semantics)");
            m1LectureName.add("한국구비문학론 (Korean Oral Literature)");
            m1LectureName.add("한국현대소설강독 (Readings in Korean Modern Novel)");
            m1LectureName.add("한국현대문학비평 (Korean Modern Literary Criticism)");
            m1LectureName.add("한국현대시강독 (Readings in Korean Modern Poetry)");
            m1LectureName.add("한국어어휘론 (Korean Lexicology)");
            m1LectureName.add("한국영상문학론 (Korean Film and Television Drama)");
            m1LectureName.add("한국어학자료읽기 (Readings in Korean Linguistics)");
            m1LectureName.add("세계 속의 한국문학 (Korean Literature in a Global Context)");
            m1LectureName.add("한국고전소설강독 (Readings in Classical Korean Novel)");
            m1LectureName.add("K문학의 이해 (Understanding K-Literature)");

            List<String> m1LectureIntro = new ArrayList<>();
            m1LectureIntro.add("이 과목은 한국어 연구, 즉 국어학은 어떠한 학문이며, 구체적으로 무엇을 연구하는지에 대한 정보를 제공하고, 동시에 국어가 어떠한 특징을 지닌 언어인지 알아봄으로써 학생들이 보다 쉽게 국어학에 접근할 수 있도록 한다. 구체적으로는 일반언어학과의 관계에서 정립되는 국어학의 위치, 국어학의 하위 분야들, 연구대상 및 범위, 국어의 역사적인 변화 양상, 현대국어의 음운, 문법, 어휘적 특징 등을 고찰한다. 이를 바탕으로 학생들이 더욱 깊이 있는 국어연구를 위한 기초를 다지도록 한다.");
            m1LectureIntro.add("이 교과목은 한국문학의 연구 대상과 연구 방법은 무엇인가 하는 물음에 대해 충실한 답변을 제시함으로써 학생들이 우리 문학 전반에 대해 이해하고 우리 문학을 연구하는 데 필요한 기본 지식을 습득하도록 하는 데 목적을 둔다. 구체적으로는 한국문학의 개념과 범위, 갈래 체계와 역사적 전개 과정, 주제적, 미학적 특성, 전반적인 작품의 실상 등을 체계적으로 고찰한다. 이를 통해 학생들은 본격적인 한국문학 연구의 기초를 다지게 된다.");
            m1LectureIntro.add("이 교과목은 문학이 사회를 반영하면서 동시에 작품의 배경으로 삼기도 한다는 문학 일반의 원칙을 한국 문학 작품들을 통해 확인함으로써, 학생들이 문학과 실제 삶의 연관성을 이해하도록 하는 데 목적을 둔다. 구체적으로는 시, 소설, 평론 등으로 짜여진 한국 문학을 한국인의 삶의 현장인 한국사회의 관점에서 살펴보는 것을 주된 내용으로 삼는다. 이를 바탕으로 학생들은 본격적인 한국문학 연구를 위한 기초를 다지게 된다.");
            m1LectureIntro.add("이 과목은 음운론에 관한 일반적인 이론의 흐름을 개관하고 그것이 한국어에 어떻게 적용될 수 있는지 알아본다. 먼저 음소 설정 방법을 통해 한국어의 음소에는 어떤 것이 있는지 알아보고 그 음소들은 어떤 체계를 구성하고 있는지 알아본다. 나아가 음운규칙을 설정하고 어떤 음운과정이 있는지 살핀다. 이러한 공시음운론 외에 음운의 역사적 변화를 다루는 통시음운론도 여기서 다루어진다.");
            m1LectureIntro.add("고대가요에서부터 향가, 고려가요, 시조, 가사 등 고전시가 작품에 대한 전반적인 이해를 도모하고 문학 작품으로의 이해와 해석의 방법을 체득할 수 있도록 한다. 어법, 운율, 표현 방법 등에 유의하여 고전 시가 작품을 강독하면서 작품을 읽고 작품을 분석하고 그 의미를 해석하는 방법을 익히도록 한다.");
            m1LectureIntro.add("개화기 시기부터 최근의 희곡작품을 일차자료로 삼아 작품을 분석하는 능력을 배양하고 한국 현대 희곡의 흐름을 조망함으로써 희곡 연구의 기초를 닦는다. 구체적인 작품에 대한 평가와 그 작품의 공연과 관련된 사회사적 맥락을 아울러 살펴봄으로써 공연예술로서의 연극에 대한 미학적 이해를 아울러 시도한다.");
            m1LectureIntro.add("고대에서부터 구한말에 이르기까지 한국문학의 존재 양상과 생성⋅성장⋅소멸한 다양한 갈래의 존재 양상을 역사적으로 고찰함으로써 한국고전문학사의 전개의 양상과 원리를 찾아본다. 이를 위하여 첫째, 한국고전문학의 시대 구분, 갈래 체계, 작품에 대한 분석과 해석의 방법 등에 대한 기존의 연구와 학설을 점검하고, 둘째, 시대에 따른 작품의 실상을 살펴보게 될 것이다.");
            m1LectureIntro.add("개화기부터 1960년대까지 이르는 기간 동안 각 시기마다 문학사적으로 중심이 되는 비평, 소설, 시, 희곡 작품들을 대상으로 하여 현대한국문학사의 전개과정을 다룬다. 개화기의 신소설과 시가 문학에서 비롯, 이광수⋅최남선⋅김동인⋅염상섭 등에 의한 초기 신문학과 1920년대의 프로 문학, 1930년대의 사실주의와 모더니즘 문학, 해방 공간의 문학과 전후 문학 등을 거치는 한국현대문학사의 시각 속에서 작품들의 구체적인 위상을 파악한다.");
            m1LectureIntro.add("말과 문자로 이루어진 산문문학을 연구하기 위해서는 작품의 수집과 분류, 감상과 이해, 해석과 분석에 이르는 과정을 이해할 수 있어야 한다. 이 가운데 작품을 읽고 해석할 수 있는 능력은 그 기초가 되는 것으로, 고전산문이 갖는 독특한 독법을 체득하여 작품의 생생한 미감을 파악하도록 함으로써, 강독한 작품들에 대한 실제적인 분석을 시도하게 한다.");
            m1LectureIntro.add("이 과목은 한국어가 고대에서부터 현대까지 변화해온 모습을 개관하는 데 목적을 둔다. 먼저 한국어의 계통과 형성에 대해 알아보고 한국어 역사의 시대구분 방법에 대해 논의한다. 이러한 시대구분이 이루어지면 표기법, 음운, 문법, 어휘체계 등으로 나누어 시대별 특징을 살펴본다. 그리고 그 특징을 비교함으로써 국어의 변화된 모습을 파악할 수 있도록 한다.\n");
            m1LectureIntro.add("한국어 문법론은 한국어 음운론과 함께 우리말의 구조를 다루는 한 분야이다. 음운론이 자음, 모음, 음절, 액센트 등 언어의 소리 쪽을 다룬다면 문법론은 형태소, 단어, 구, 문장 등 그 자체가 어떤 의미를 동반하고 있는 단위, 곧 문법 단위들을 다룬다. 이 과목은 현대국어를 대상으로 우리말의 다양한 문법 현상과 그 바탕에 깔려 있는 규칙들을 발견하고 이해해 나가는 데 그 목적이 있다.");
            m1LectureIntro.add("고전소설 작품에 대한 전반적인 이해를 바탕으로, 고전소설의 미학적 특징과 그에 표현된 한국인의 생활 감정과 사상을 이해하고 작품을 분석하고 해석하는 연구의 방법을 체득하도록 한다. 이를 위하여 첫째, 작품 및 갈래 등에 대한 지금까지의 연구 성과를 점검하고, 둘째, 구체적인 작품이나 갈래를 대상으로 그 작품을 분석하고 해석하거나 갈래의 미학적 특징을 규명하는 방법을 실습한다.");
            m1LectureIntro.add("한국 현대시의 효과적인 이해를 위해서 시의 개별 요소에 대한 이론들을 일별하고 이론과 실제 창작 사이의 거리를 살펴본다. 아울러 그들이 한국시에 어떻게 적용․이해될 수 있는가를 설명, 이해시키고자 한다. 한국 현대시의 기능적 이해를 위한 이론을 익히고, 실제 작품 분석, 검토를 한다. 중요 내용은 시의 언어적 속성, 비유론, 상징론, 심상론, 운율, 형태론, 역설, 아이러니 등이다.");
            m1LectureIntro.add("개화기 이후 1950년대까지의 한국 근대 시사에서 중요하다고 판단되는 시인을 선정하여 각 시인들의 연구사를 검토하고, 그 시인들에 부합되는 방법론을 채택하여 새로운 연구 방법을 제시하는 데까지 강의를 한다.");
            m1LectureIntro.add("소설의 구조를 분석할 수 있는 일반이론을 소개하고 이론 자체의 계보적 특징을 습득한다. 그리하여 한국현대소설에 대한 엄정한 평가를 가능케 하는 이론적 기반을 마련하고, 내용과 형식의 연관을 통해 총체적으로 의미체를 파악하고 실증적으로 평가하는 태도를 기른다. 현대한국소설의 구조와 그 이론적 체계를 살펴보고, 현대소설의 전개과정에서 찾을 수 있는 문제점을 연구, 강독을 하는 강좌로서 현대한국소설이 지닌 특성과 서술기법, 구성의 조직, 작가의 작품 분석 방법론을 중심대상으로 한다.");
            m1LectureIntro.add("작가론의 방법에 대한 이해를 토대로 하여 한국 현대 작가의 전반적 특징을 파악한 후 특정 작가의 작품세계를 파악하는 것을 목표로 한다. 현대 한국 작가를 대상으로 작가에 대한 연구사 검토와 작품 분석을 중심내용으로 하는 연구성과를 세미나 형식으로 검토함으로써 수강생들의 한국소설사에 대한 지식과 인식을 향상시키도록 한다.");
            m1LectureIntro.add("한국한문학에 대한 전반적인 이해를 바탕으로 한문학의 미학적 특징과 그에 표현된 한국인의 생활 감정과 사상을 이해하고 작품을 분석하고 해석하는 연구의 방법을 체득하도록 한다. 이를 위하여 첫째, 작품 및 갈래 등에 대한 지금까지의 연구 성과를 점검하고, 둘째, 구체적인 작품이나 갈래를 대상으로 그 작품을 분석하고 해석하거나 갈래의 미학적 특징을 규명하는 방법을 실습한다.");
            m1LectureIntro.add("고대가요에서부터 향가, 고려가요, 시조, 가사 등 고전시가 작품에 대한 전반적인 이해를 바탕으로 고전시가의 미학적 특질과 그에 표현된 한국인의 생활 감정과 사상을 이해하고 작품을 분석하고 해석하는 연구의 방법을 체득하도록 한다. 이를 위하여 첫째, 작품 및 갈래 등에 대한 지금까지의 연구 성과를 점검하고, 둘째, 구체적인 작품이나 갈래를 대상으로 그 작품을 분석하고 해석하거나 갈래의 미학적 특징을 규명하는 방법을 실습한다.");
            m1LectureIntro.add("본 과목의 목표는 학생들로 하여금 컴퓨터를 이용하여 한국어 관련 정보(또는 자료)를 적절히 추출하고 처리할 수 있는 기초적인 능력을 기르게 하는 것이다. 정보 관련 기술과 산업이 발전함에 따라 많은 학문분야의 연구 내용과 방법론도 달라지고 있는바, 이러한 변화에 부응하기 위해서 개설된 과목이 본 과목이다. 본 과목을 통하여 학생들은 말뭉치의 구축, 말뭉치 가공, 가공된 말뭉치로부터의 언어정보 추출, 추출된 언어정보의 통계적 분석, 언어정보의 데이터베이스화, 데이터베이스의 운용 및 유지 등에 관한 기초적인 방법론을 익히게 될 것이며, 한국어문학 나아가서는 인문학을 위한 컴퓨터 활용 능력을 키우게 될 것이다.");
            m1LectureIntro.add("이 과목은 한국어 방언의 체계적인 연구를 위한 방법의 습득에 그 목적을 둔다. 우선 방언학과 관련한 여러 이론들을 소개한다. 주로 지리방언을 위주로 살펴보며 구체적으로 방언자료집을 통해 각 방언간의 음운⋅문법⋅어휘적 특징을 알아본다. 그리고 방언 차이에 의해 방언구획 작업을 하고 나아가 방언지도를 작성해 본다. 물론 이런 방언비교 뿐만 아니라 어느 한 방언을 택하여 공시론적인 연구를 할 수도 있다.");
            m1LectureIntro.add("이 과목은 한국어에 대한 연구가 어떤 것들이 있었으며 그 경향의 변화 방향은 어떤지를 알아보는 것을 목적으로 한다. 먼저 한국어학사의 시대구분에 대해 알아본다. 그리고 각 시대별로 중요하다고 생각되는 학자들을 택하여 그 연구 성과가 어떠했는지 알아본다. 강의는 학생들이 한 학자씩 맡아 발표를 하고 이를 토론하는 형식으로 진행된다.");
            m1LectureIntro.add("언어를 음성과 의미의 결합이라고 볼 수 있는 점에서 의미는 언어에서 빼놓을 수 없는 중요한 요소이다. 의미론은 이러한 말의 의미를 다루는 분야이다. 이 과목은 한국어를 대상으로 의미의 의미, 단어 간의 의미 관계, 단어장과 성분분석, 의미의 변화 등을 살피고, 나아가 문장의 의미, 담화 의미 등에까지 관심을 넓힘으로써 한국어에 대한 이해의 폭을 넓히는 데 그 목적이 있다.");
            m1LectureIntro.add("설화, 민요, 전통극 등 구비문학에 대한 전반적인 이해를 바탕으로 구비문학의 문학적 특징과 그에 표현된 한국인의 생활 감정과 사상을 이해하고 작품을 분석하고 해석하는 연구의 방법을 체득하도록 한다. 이를 위하여 첫째, 작품 및 갈래 등에 대한 지금까지의 연구 성과를 점검하고, 둘째, 구체적인 작품이나 갈래를 대상으로 그 작품을 분석하고 해석하거나 갈래의 미학적 특징을 규명하는 방법을 실습한다.");
            m1LectureIntro.add("개화기에서 1970년대까지 발표된 소설 작품들 가운데서 문제작을 선정하여 올바로 읽고, 분석하고, 평가하도록 한다.");
            m1LectureIntro.add("문학비평의 유형들과 개별 방법론을 검토하고 한국현대문학 연구에 어떻게 적용할 수 있는가를 실제 연구논문을 작성하면서 확인한다. 이와 함께 개화기 이후 진행된 실제비평을 통해 이론의 정합성과 적용가능성 및 한계 등을 검증해 본다. 문학작품에 대한 가치 평가의 행위인 비평작업을 통해 문학작품의 의미와 존재의의를 확인하는 비평문학에 대한 이론적 탐색을 통해 한국문학연구의 이론적 토대를 마련하는 것을 궁극의 목적으로 한다.\n");
            m1LectureIntro.add("개화기의 여러 시가부터 최근의 시까지 1차 자료를 대상으로 하여 시 분석의 기초적인 능력을 배양한다. 발표 당시의 원문표기를 살린 자료를 대상으로 연구능력을 배양하는 훈련을 하며 동시에 시 분석을 위한 이론들을 재검토한다.");
            m1LectureIntro.add("본 강좌는 한국어의 어휘항목들(단어, 연어, 관용표현 등)이 이루는 어휘구조에 대한 이론적 이해를 목적으로 한다. 단어의 내부구조와 단어형성, 단어의 차용, 어휘의미와 그 변화, 단어간 관계, 어휘 체계, 어휘 분류, 어휘의 계량, 단어의 다양한 변종들, 사전편찬 등을 다룬다. 어휘에 대한 이론적 이해가 어휘력 증진, 문학작품에 대한 이해, 한국의 문화와 한국인의 사고방식에 대한 측면적 이해 등으로 확대될 수 있도록 유의한다.\n");
            m1LectureIntro.add("본 강좌는 한국 현대 영상예술의 흐름을 조망하고 학생들이 영상예술 작품을 분석하는 능력을 갖출 수 있도록 하여 영상예술 연구의 기초를 닦는 것을 목표로 한다. 이 강좌에서 학생들은 한국 영화 및 텔레비전 드라마 작품을 새로운 각도에서 해석함으로써 영상예술에 대한 심도 있는 이해를 얻을 수 있다. 또한 구체적인 작품에 대한 평가와 그 작품의 상영과 관련된 사회사적 맥락을 아울러 살펴봄으로써 개별 작품과 사회문화적인 맥락에 대한 균형 잡힌 이해를 도모할 것이다.");
            m1LectureIntro.add("한국어 자료를 표기, 문자, 음운, 문법, 어휘의 면에서 자세히 읽고, 분석함으로써 한국어의 실상에 대한 이해의 폭을 넓힌다. 또 한국어 자료에 대한 서지, 문헌학적 접근을 통해 역사적 자료를 다루는 방법과 절차를 익히도록 한다.");
            m1LectureIntro.add("대중음악, 드라마, 영화 등 한국 문화가 전 세계적으로 전파되고 있는 한류와 세계화 시대에 한국문학도 세계적인 인기를 얻고 있다. 이에 따라 해외에서 한국 문학이 어떻게 표현 및 해석, 이해되어 왔는지를 살펴보는 것이 중요한 과제라고 본다. 이런 문맥에서 이 강의는 고전으로부터 현대에 이르기까지의 한국 문학 전반에 있어서 영어권에서 영어로 번역된 작품과 그 작품을 어떻게 이해하고 해석하는 지를 살펴보는 것을 내용으로 한다. 이를 통해 학생들이 앞으로 한국 문학을 세계화하는 데에 기여할 수 있도록 새로운 시각에서 본 한국 문학에 대한 이해를 높이고자 한다.");
            m1LectureIntro.add("한국고전소설은 대부분 필사본으로 전한다. 따라서 감상하고 이해하고 연구하기 위해서는 기본적으로 작품을 읽을 수 있는 능력을 갖추어야 한다. 이 교과목은 고전소설 독법의 기초 능력을 높이는 데 목적을 둔다. 한문 판각본과 필사본, 한글 판각본과 필사본 등 몇 가지 유형의 작품들을 골고루 선정하여 강독하며, 강독한 작품들에 대해서는 구체적인 분석과 해석을 시도할 것이다.");
            m1LectureIntro.add("‘K-문학’이란 최근 세계에서 관심 대상으로 떠오르는 한국문학을 말한다. 2000년대 들어 한국문학은 세계의 관심 대상이 되었다. 작가 한강이 2016년 맨부커상 인터내셔널 부문을 수상했다. 2016년에는 작가 윤고은이 대거상을 수상하기도 했다. 소설뿐만 아니라 김소연, 김혜순 등 시인들도 세계의 주목을 받고 있다. 한국 영화가 세계의 각광을 받는 데는 시나리오의 힘이 아주 컸다고도 말할 수 있다. 이에 ‘K-문학’의 작가와 작품을 통하여 학생들이 동시대 한국문학을 넓게 이해할 수 있도록 한다.");

//          Lecture(String name,
//                  String intro,
//                  Integer credit,
//                  String day,
//                  String start,
//                  String end,
//                  Lecture_Type type,
//                  Professor professor,
//                  Integer maxCount,
//                  String semester,
//                  Integer grade,
//                  String plan,
//                  Major major,
//                  LectureRoom lectureRoom) {
            // 먼저 대학교 목록을 받아온다.
            List<College> collegeList = em.createQuery("select c from College c order by c.idx", College.class).getResultList();
            // 대학에서 보유 중인 강의실 목록을 불러온다. 아래 구문은 collegeList의 첫 대학으로 되어있다
            List<LectureRoom> c1LectureRoom = em.createQuery("select r from LectureRoom r where r.college.idx = :cidx order by r.idx", LectureRoom.class).setParameter("cidx", collegeList.get(0).getIdx()).getResultList();
            // 대학에 소속된 전공 목록을 불러온다. 아래 구문은 collegeList의 첫 대학으로 되어있다
            List<Major> c1Major = em.createQuery("select m from Major m where m.college.idx = :cidx order by m.idx", Major.class).setParameter("cidx", collegeList.get(0).getIdx()).getResultList();
            // 해당 전공의 교수 목록을 불러온다. 아래 구문은 collegeList의 첫 대학의 첫 전공으로 되어있다
            List<Professor> c1m1Professor = em.createQuery("select p from Professor p where p.major.idx = :midx", Professor.class).setParameter("midx", c1Major.get(0).getIdx()).getResultList();

            Random ran = new Random();

            for (int i = 0; i < m1LectureName.size(); i++) {
                int professorIndex = ran.nextInt(c1m1Professor.size());
                int lectureRoomIndex = ran.nextInt(c1LectureRoom.size());
                Lecture lectureTmp = new Lecture(m1LectureName.get(i), m1LectureIntro.get(i),
                                            3, "월, 화", "09:00", "11:00", Lecture_Type.전공필수,
                                                c1m1Professor.get(professorIndex), 30, "2024년 1학기",
                                                1, null, c1Major.get(0), c1LectureRoom.get(lectureRoomIndex));
            }
        }

//        // 이거 안씀
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
//            College college1 = new College("사범대학", "인문관1");
//            College college2 = new College("사회과학대학", "인문관2");
//            College college3 = new College("의과대학", "공학관");
//            em.persist(college1);
//            em.persist(college2);
//            em.persist(college3);
//
//            Major major1 = new Major("정치외교학과", 3500000, college1);
//            Major major2 = new Major("지리학과", 3200000, college1);
//            Major major3 = new Major("군사학과", 3300000, college1);
//            em.persist(major1);
//            em.persist(major2);
//            em.persist(major3);
//
//            Major major4 = new Major("수학교육과", 2900000, college2);
//            Major major5 = new Major("사회교육과", 2800000, college2);
//            Major major6 = new Major("국어교육과", 3000000, college2);
//            em.persist(major4);
//            em.persist(major5);
//            em.persist(major6);
//
//            Major major7 = new Major("신경과", 5500000, college3);
//            Major major9 = new Major("정신과", 5700000, college3);
//            Major major8 = new Major("마취과", 5200000, college3);
//            em.persist(major7);
//            em.persist(major8);
//            em.persist(major9);
//
//            LectureRoom lectureRoom1 = new LectureRoom("101", college1);
//            LectureRoom lectureRoom2 = new LectureRoom("102", college1);
//            LectureRoom lectureRoom3 = new LectureRoom("201", college2);
//            LectureRoom lectureRoom4 = new LectureRoom("202", college2);
//            LectureRoom lectureRoom5 = new LectureRoom("501", college3);
//            LectureRoom lectureRoom6 = new LectureRoom("502", college3);
//
//            em.persist(lectureRoom1);
//            em.persist(lectureRoom2);
//            em.persist(lectureRoom3);
//            em.persist(lectureRoom4);
//            em.persist(lectureRoom5);
//            em.persist(lectureRoom6);
//
//
//            User user1 = new User(hash, randomSalt, "정수용", "222222-2222222", "부산광역시 미남", "010-1234-1234", "tesrht2@naver.com", User_role.교수, sqlCreatedAt);
//            User user2 = new User(hash, randomSalt, "최유란", "111111-1111111", "울산광역시 남구", "010-1234-1234", "tesagat1@naver.com", User_role.교수, sqlCreatedAt);
//            User user3 = new User(hash, randomSalt, "이병길", "333333-3333333", "부산광역시 대연동", "010-1234-1234", "ahtest3@naver.com", User_role.교수, sqlCreatedAt);
//            em.persist(user1);
//            em.persist(user2);
//            em.persist(user3);
//
//            User user5 = new User(hash, randomSalt, "황민우", "555555-5555555", "서울특별시 강북", "010-1234-1234", "teulahst2@naver.com", User_role.교직원, sqlCreatedAt);
//            User user6 = new User(hash, randomSalt, "박소은", "666666-6666666", "부산광역시 수영구", "010-1234-1234", "teulhst3@naver.com", User_role.교직원, sqlCreatedAt);
//            User user4 = new User(hash, randomSalt, "안지혜", "444444-4444444", "서울특별시 강남", "010-1234-1234", "testuil1@naver.com", User_role.교직원, sqlCreatedAt);
//            em.persist(user4);
//            em.persist(user5);
//            em.persist(user6);
//
//            User user7 = new User(hash, randomSalt, "송근욱", "777777-7777777", "울산광역시 남구", "010-1234-1234", "tesryidt1@naver.com", User_role.학생, sqlCreatedAt);
//            User user8 = new User(hash, randomSalt, "이순신", "888888-8888888", "부산광역시 미남", "010-1234-1234", "tedlfyist2@naver.com", User_role.학생, sqlCreatedAt);
//            User user9 = new User(hash, randomSalt, "안중근", "999999-9999999", "부산광역시 대연동", "010-1234-1234", "tesilfyilt3@naver.com", User_role.학생, sqlCreatedAt);
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
//                SituationRecord record1 = new SituationRecord(Status_type.재학, student1, situation1.getStart_date(), null);
//                SituationRecord record2 = new SituationRecord(Status_type.재학, student2, situation2.getStart_date(), null);
//                SituationRecord record3 = new SituationRecord(Status_type.재학, student3, situation3.getStart_date(), null);
//
//                em.persist(record1);
//                em.persist(record2);
//                em.persist(record3);
//
//                Payments payments = new Payments(student1, "2023학년 1학기");
//                Payments payments2 = new Payments(student2, "2023학년 1학기");
//                Payments payments3 = new Payments(student3, "2023학년 1학기");
//
//                Payments payments4 = new Payments(student1, "2023학년 2학기");
//                Payments payments5 = new Payments(student2, "2023학년 2학기");
//                Payments payments6 = new Payments(student3, "2023학년 2학기");
//
//                Payments payments7 = new Payments(student1, "2024학년 1학기");
//                Payments payments8 = new Payments(student2, "2024학년 1학기");
//                Payments payments9 = new Payments(student3, "2024학년 1학기");
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
//                Scholarship_Award scholarshipAward1 = new Scholarship_Award(student1, scholarship1);
//                Scholarship_Award scholarshipAward2 = new Scholarship_Award(student1, scholarship2);
//                Scholarship_Award scholarshipAward3 = new Scholarship_Award(student1, scholarship3);
//                Scholarship_Award scholarshipAward4 = new Scholarship_Award(student2, scholarship1);
//                Scholarship_Award scholarshipAward5 = new Scholarship_Award(student2, scholarship2);
//                Scholarship_Award scholarshipAward6 = new Scholarship_Award(student3, scholarship1);
//                Scholarship_Award scholarshipAward7 = new Scholarship_Award(student1, scholarship4);
//                Scholarship_Award scholarshipAward8 = new Scholarship_Award(student1, scholarship5);
//                Scholarship_Award scholarshipAward9 = new Scholarship_Award(student1, scholarship6);
//                Scholarship_Award scholarshipAward10 = new Scholarship_Award(student2, scholarship4);
//                Scholarship_Award scholarshipAward11 = new Scholarship_Award(student2, scholarship5);
//                Scholarship_Award scholarshipAward12 = new Scholarship_Award(student3, scholarship6);
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
    }
}
