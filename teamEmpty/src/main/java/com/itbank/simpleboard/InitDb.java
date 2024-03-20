package com.itbank.simpleboard;

import com.itbank.simpleboard.component.HashComponent;
import com.itbank.simpleboard.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
            initService.addLecture();
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

            Major c1Major1 = new Major("국어국문학과", 3500000, college1);
            Major c1Major2 = new Major("중어중문학과", 2900000, college1);
            Major c1Major3 = new Major("영어영문학과", 3700000, college1);
            Major c1Major4 = new Major("불어불문학과", 3700000, college1);
            Major c1Major5 = new Major("독어독문학과", 3200000, college1);
            Major c1Major6 = new Major("노어노문학과", 3200000, college1);
            Major c1Major7 = new Major("서어서문학과", 3100000, college1);
            em.persist(c1Major1);
            em.persist(c1Major2);
            em.persist(c1Major3);
            em.persist(c1Major4);
            em.persist(c1Major5);
            em.persist(c1Major6);
            em.persist(c1Major7);


            Major c2Major1 = new Major("정치외교학부(정치학전공)", 2900000, college2);
            Major c2Major2 = new Major("정치외교학부(외교학전공)", 2800000, college2);
            Major c2Major3 = new Major("경제학부", 3000000, college2);
            Major c2Major4 = new Major("사회학과", 3100000, college2);
            Major c2Major5 = new Major("인류학과", 3200000, college2);
            em.persist(c2Major1);
            em.persist(c2Major2);
            em.persist(c2Major3);
            em.persist(c2Major4);
            em.persist(c2Major5);


            Major c3Major2 = new Major("통계학과", 5700000, college3);
            Major c3Major5 = new Major("화학부", 5200000, college3);
            Major c3Major6 = new Major("생명과학부", 5200000, college3);
            Major c3Major7 = new Major("지구환경과학부", 5200000, college3);
            em.persist(c3Major2);
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
            em.persist(c6Major1);
            em.persist(c6Major2);
            em.persist(c6Major3);
            em.persist(c6Major4);
            em.persist(c6Major5);
            em.persist(c6Major6);

            Major c7Major1 = new Major("식물생산과학부", 5100000, college7);
            Major c7Major2 = new Major("산림과학부", 5000000, college7);
            Major c7Major3 = new Major("응용생물화학부", 5100000, college7);
            Major c7Major4 = new Major("식품·동물생명공학부", 5200000, college7);
            em.persist(c7Major1);
            em.persist(c7Major2);
            em.persist(c7Major3);
            em.persist(c7Major4);

            Major c8Major1 = new Major("동양화과", 5200000, college8);
            Major c8Major2 = new Major("서양화과", 5100000, college8);
            Major c8Major3 = new Major("조소과", 4900000, college8);
            em.persist(c8Major1);
            em.persist(c8Major2);
            em.persist(c8Major3);

            Major c9Major1 = new Major("교육학과", 5200000, college9);
            Major c9Major2 = new Major("국어교육과", 4900000, college9);
            Major c9Major3 = new Major("영어교육과", 4800000, college9);
            Major c9Major4 = new Major("불어교육과", 3700000, college9);
            Major c9Major5 = new Major("독어교육과", 4900000, college9);
            Major c9Major6 = new Major("사회교육과", 3900000, college9);
            Major c9Major7 = new Major("역사교육과", 3900000, college9);
            Major c9Major8 = new Major("지리교육과", 4100000, college9);
            em.persist(c9Major1);
            em.persist(c9Major2);
            em.persist(c9Major3);
            em.persist(c9Major4);
            em.persist(c9Major5);
            em.persist(c9Major6);
            em.persist(c9Major7);
            em.persist(c9Major8);

            Major c10Major3 = new Major("식품영양학과", 4400000, college10);
            Major c10Major4 = new Major("의류학과", 4300000, college10);
            em.persist(c10Major3);
            em.persist(c10Major4);

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
        }

        private void makeLectureRoom(int dong, College college) {
            Random ran = new Random();
            String[] floor = {"1", "2", "3", "4", "5"};
            String[] roomNum = {"1", "2", "3", "4", "5", "6", "7"};
            ArrayList<String> makeRoom = new ArrayList<>();
            for (int i = 1; i <= dong; i++) {
                while (makeRoom.size() < 5) {
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

        public void addLecture() {
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
            System.out.println("collegeList = " + collegeList);
            // 대학에서 보유 중인 강의실 목록을 불러온다. 아래 구문은 collegeList의 첫 대학으로 되어있다
            List<LectureRoom> c1LectureRoom = em.createQuery("select r from LectureRoom r where r.college.idx = :cidx order by r.idx", LectureRoom.class).setParameter("cidx", collegeList.get(0).getIdx()).getResultList();
            System.out.println("c1LectureRoom = " + c1LectureRoom);
            // 대학에 소속된 전공 목록을 불러온다. 아래 구문은 collegeList의 첫 대학으로 되어있다
            List<Major> c1Major = em.createQuery("select m from Major m where m.college.idx = :cidx order by m.idx", Major.class).setParameter("cidx", collegeList.get(0).getIdx()).getResultList();
            System.out.println("c1Major = " + c1Major);
            // 해당 전공의 교수 목록을 불러온다. 아래 구문은 collegeList의 첫 대학의 첫 전공으로 되어있다
            List<Professor> c1m1Professor = em.createQuery("select p from Professor p where p.major.idx = :midx", Professor.class).setParameter("midx", c1Major.get(0).getIdx()).getResultList();
            System.out.println("c1m1Professor = " + c1m1Professor);

            Random ran = new Random();

            for (int i = 0; i < m1LectureName.size(); i++) {
                int professorIndex = ran.nextInt(c1m1Professor.size());
                int lectureRoomIndex = ran.nextInt(c1LectureRoom.size());
                Lecture lectureTmp = null;
                if (m1LectureName.get(i).contains("(필)")) {
                     lectureTmp = new Lecture(m1LectureName.get(i), m1LectureIntro.get(i),
                                                3, "월, 화", "09:00", "11:00", Lecture_Type.전공필수,
                                                    c1m1Professor.get(professorIndex), 30, "2024년 1학기",
                                                    1, null, c1Major.get(0), c1LectureRoom.get(lectureRoomIndex));
                } else {
                    lectureTmp = new Lecture(m1LectureName.get(i), m1LectureIntro.get(i),
                            3, "월, 화", "09:00", "11:00", Lecture_Type.전공선택,
                            c1m1Professor.get(professorIndex), 30, "2024년 1학기",
                            1, null, c1Major.get(0), c1LectureRoom.get(lectureRoomIndex));
                }
                em.persist(lectureTmp);
            }

            List<Professor> c1m2Professor = em.createQuery("select p from Professor p where p.major.idx = :midx", Professor.class).setParameter("midx", c1Major.get(1).getIdx()).getResultList();

            List<String> m2CultureName = new ArrayList<>();
            m2CultureName.add("초급한문1");
            m2CultureName.add("초급한문2");
            m2CultureName.add("중급한문");
            m2CultureName.add("한문명작읽기");
            m2CultureName.add("초급중국어1");
            m2CultureName.add("초급중국어2");
            m2CultureName.add("중급중국어1");
            m2CultureName.add("중급중국어2");
            m2CultureName.add("중국어회화1");
            m2CultureName.add("중국어회화2");
            m2CultureName.add("미디어중국어");
            m2CultureName.add("동양의 고전");
            m2CultureName.add("한자와 동양문화");
            m2CultureName.add("중국어권의 사회와 문화");
            m2CultureName.add("중국인의 언어와 문화");

            List<String> m2CultureIntro = new ArrayList<>();
            m2CultureIntro.add("본 교과목은 한자와 한문에 대해 거의 모르는 초보자를 위한 강좌로 한문에 대한 기초적인 이해와 지식의 배양에 중점을 둔다. 여기서는 한문의 품사와 기본 구조 등 문법에 대한 기초적인 이해를 기반으로 짧고 기본적인 구문이나 시가 등을 정밀하게 분석하고 감상한다. 이를 통해 기초적인 한문 독해력을 배양하며, 아울러 과제 등의 방식으로 일상생활에 쓰이는 한자어 실력도 증진시키고자 한다.");
            m2CultureIntro.add("본 교과목은 초급한문1에 이어진 기초 한문 강좌로 기존의 한문1과 한문2 사이의 수준차를 완화하고 효율적인 학습을 도모하기 위해 개설된 강좌이다. 초급한문1에서 학습한 지식을 바탕으로 한문의 주요한 문형과 허사 등에 대한 이해를 추가하여 한문 문법에 대한 이해를 심화하고 이를 기반으로 단문 독해 연습을 집중적으로 한다. 중국은 물론 우리나라의 다양한 문장과 시가를 분석하고 감상함으로써 기초적인 한문 독해력을 배양하고 아울러 선인들의 사상과 동양문화에 대한 이해를 증진시키는 계기를 제공하고자 한다. 본 강좌를 통해 초급 수준의 한문 실력을 충실히 배양하게 되면 중급 이상의 한문 강좌를 수강하는데 무리가 없을 것이다.");
            m2CultureIntro.add("본 교과목은 초급한문1과 초급한문2에 이은 중급수준의 한문 강좌이다. 초급 한문 과정에서 다져진 기초 실력을 바탕으로 한문에 대한 이해를 심화하고 보다 발전된 한문 독해 실력을 배양하는 과정이다. 여기에서는 보다 완결된 형태의 문학, 철학, 역사 등 다방면의 고전 산문 문장과 시가 작품들을 익힐 수 있다. 이를 통해 한문 문법 지식을 체화하고 구문에 익숙해지도록 함과 동시에 선현들의 사상과 인생관 등을 비판적으로 검토해봄으로써 인문학적인 소양을 늘리는 기회를 가질 수 있을 것이다. 본 교과목까지 수강하면 고급 한문 강좌나 한문 관련 전공과목을 수강하는 데에도 무리가 없는 수준의 한문 실력을 배양할 수 있을 것이다.");
            m2CultureIntro.add("본 교과목은 초급한문과 중급한문에 이은 고급 수준의 한문 명작에 대한 강좌이다. 초중급 한문 과정에서 다져진 실력을 바탕으로 고품격의 다양한 한문 문장을 정밀하게 읽어보는 과정이다. 초급한문과 중급한문에서는 주로 모범적이고 정격에 가까우며 비교적 평이한 문장을 다룬다. 그러다보니 유명하고 중요한 한문 문장임에도 초급자가 이해하기 쉽지 않다고 판단되면 배제되는 경우가 많았다. 본 강좌에서는 이러한 한문 문장을 경사자집(經史子集)의 다방면에 걸쳐 두루 선별하여 읽어보고자 한다. 본 교과목까지 수강하게 되면 정격적인 한문 문장뿐만 아니라 다소 파격적이고 난해한 문장에 대한 이해와 적응력을 높임으로써 한문을 깊이 배우거나 한문과 관련된 분야를 전공하려는 학문후속세대에게 자신감을 심어줄 수 있을 것이다.");
            m2CultureIntro.add("고등학교 과정에서 중국어를 배우지 않은 학생을 위한 초급 단계의 중국어 강의이다. 중국어 발음법을 정확하게 습득한 후 구문을 토대로 한 초급 문법을 학습하여 회화 및 독해와 작문의 기초를 확립하는 과정이다. 따라서 본 교과목은 이전 과정에서 중국어를 학습한 경험이 있는 학생의 수강은 권하지 않는다.");
            m2CultureIntro.add("고등학교 과정에서 2개 학기 이상 중국어를 배웠거나 중국어에 대한 초보적인 지식이 있는 학생을 위한 초급 두 번째 단계의 강의이다. 중국어 발음법을 숙지하고 문법과 작문에 기초 지식을 갖춘 학생이라면 누구나 들을 수 있다. 말하기, 듣기, 읽기, 쓰기와 문법 등 중국어 전반에 걸쳐 초급 단계의 학습을 완성하는 과정이다. 이 과목을 수강하면 기초 회화와 독해 능력을 갖출 수 있고, 향후 중급의 중국어를 학습할 수 있다.");
            m2CultureIntro.add("본 교과목은 초급중국어2 또는 고등학교에서 중국어를 4개 학기 이상 수강한 학생을 대상으로 말하기, 듣기, 읽기, 쓰기의 전 영역에 걸쳐 중급 수준의 중국어를 학습할 수 있도록 개설하였다. 초급중국어2를 이어 일상의 구어 듣기와 말하기, 중국의 고등학교 수준의 문장 읽기와 쓰기를 익힌다. 본 과목을 수강한 학생은 중국어를 모국어로 쓰는 사람들과 일상생활에서의 원활한 의사소통이 가능함은 물론 기본적인 문헌 해독에도 어려움이 없게 될 것이다. 나아가 중국어로 학문 연구 및 사회생활을 수행할 수 있는 고급 문헌 해독의 기초를 닦는 과정이므로 중국 관련 학문 및 직업에 종사하려는 학생은 반드시 수강해야 한다.");
            m2CultureIntro.add("본 교과목은 중급중국어1을 수강한 학생을 대상으로 중급중국어1을 이어 말하기, 듣기, 읽기, 쓰기 전 영역에 걸쳐 중급 수준의 중국어를 학습할 수 있도록 개설하였다. 수강생은 본 교과목을 수강한 후 중국어를 모국어로 쓰는 사람들과 일상생활에서의 원활한 의사소통이 가능함은 물론 중급 수준 이상의 문헌 해독에도 어려움이 없게 될 것이며, 또한 중어중문학과 전공과정에 개설된 현대 중국어 강독 교과목을 수강할 수 있다. 나아가 중국어로 학문 연구 및 사회생활을 수행할 수 있는 고급 문헌 해독의 기초를 닦는 과정이므로 중국 관련 학문 및 직업에 종사하려는 학생은 반드시 수강해야 한다.");
            m2CultureIntro.add("본 교과목은 중급중국어1 이상의 수준을 갖추고 있는 학생을 대상으로 회화에 중점을 두고 중급 이상의 중국어 말하기 듣기 훈련을 시킨다. 초급중국어 및 중급중국어1에서 익힌 중국어를 기반으로 한 본격적인 말하기와 듣기 훈련을 통해 수강생은 중국어를 모국어로 쓰는 사람들과 중급 이상의 원활한 의사소통이 가능하게 된다. 중국 현지에서 일상생활을 정상적으로 영위할 수 있는 수준의 회화 구사력을 갖출 수 있다. 이 과목에 이어 중국어회화2 교과목까지 수강한다면 고급의 회화 소통은 물론 중어중문학과 전공과정에 개설된 고급의 중국어문학 교과목을 수강하는 데에도 큰 어려움을 느끼지 않을 것이다.");
            m2CultureIntro.add("본 교과목은 중국어회화1 이상의 수준을 갖춘 학생을 대상으로 유창하고 우아한 소통에 중점을 두고 고급 중국어 말하기 듣기를 훈련시킨다. 따라서 본 교과목을 성실하게 수강한 학생은 중국어를 모국어로 쓰는 사람들, 특히 사회 지도층과 고급 중국어로 원활하고 우아하게 의사를 소통할 수 있게 된다. 또한 이 과목은 중어중문학과 전공과정에 개설된 고급의 중국어문학 과목을 수강하는 데에도 기초가 된다.");
            m2CultureIntro.add("본 과목은 중급 정도의 중국어 능력을 갖춘 학생들이 다양한 매체를 통해 입체적으로 중국 관련 정보와 지식을 습득 이해할 수 있게 만든다. 각종 미디어를 통해 제공되는 다양한 중국어 자료를 보고 듣고 분석함으로써 중국어 수준을 높임은 물론 중국어권 국가의 사회와 문화에 대한 이해를 넓히고 분석능력을 배양시킨다. 중국의 현실에 관심이 많고 장차 중국을 활동 영역으로 삼고자 하는 학생들에게 필수적인 과목이다.");
            m2CultureIntro.add("동양의 각 고전에는 우리나라의 고전과 중국의 경서, 제자집성, 교재류, 문학류 등이 포함되며, 연차적으로 일본, 인도 등의 고전이 포함될 것이며, 성경, 불경, 코란 등의 종교서도 포함될 것이다. 이 강의는 이상과 같은 동양의 각 고전에 나타나는 인간의 본질, 심성, 의무, 사고, 생활 등에 대한 견해를 분류 종합하고, 이에 대한 적절한 주석 및 번역을 가한 교재를 편찬한다. 교재에는 사고의 핵심 부분을 명시하여 학생들에게 이에 대한 생각을 하게 하고 이에 대한 리포트를 제출하게 하고 토론하게 한다. 이 강의는 학생들로 하여금 이 과정을 통하여 인간에 대한 신뢰와 사랑을 회복할 수 있기를 기대한다.");
            m2CultureIntro.add("한자는 갑골문부터만 계산해도 대략 3,500여 년의 역사를 가지고 있으며, 오랫동안 동양의 문화 전반에 걸쳐 큰 영향을 미쳐왔다. 특히 한중일 삼국은 한자를 매개로 한 문화적 공통점을 많이 가지고 있어 한자문화권에 속하는 것으로 불려진다. 본 교과목에서는 한자와 관련된 동양의 문화와 그 속에 담겨진 동양인의 사유 방식을 살펴보는 것을 그 목적으로 한다. 강의의 내용에는 다음과 같은 내용이 포함된다.\n" +
                    "1) 한자의 역사와 변천 과정\n" +
                    "2) 실생활에 많이 쓰이는 한자의 원래 의미\n" +
                    "3) 한자와 농경문화, 한자와 동양인의 인명, 한자와 동양인의 금기 등의 한자와 고대인의 생활\n" +
                    "4) 한자와 사군자, 한자와 서예처럼 한자와 현대인의 취미에 대한 부분\n" +
                    "5) 한자와 디자인 등 직접 현실에 이용 가능한 동양의 문화\n" +
                    "강의의 과정에서는 시청각 자료를 많이 활용하여 시각적 효과를 높이며, 실제 생활에 적용 가능한 문화적인 요소에 대하여 수강생 스스로 찾아오도록 유도할 것이다.");
            m2CultureIntro.add("이 강의는 갈수록 그 영향력이 증대되어 가는 현재의 중국(대륙) 및 중국어권(대만, 홍콩 그리고 세계 각 지역의 화교사회)의 사회, 문화적 상황 및 장래의 발전방향에 관심을 가진 학부생을 위해 개설되었다. 70년대 말 개혁개방을 시작한 이래 연평균 9%가 넘는 경제성장률을 이어온 중국의 발전상은 이제 미국과 어깨를 나란히 하는 글로벌 리더로 인정 받아가는 형국에 이르렀다. 중국이 이룬 급속한 발전의 배후에 대만, 홍콩 및 전 세계 화교네트워크와의 긴밀한 연계와 협력이 있었음은 물론이다. 본 강의에서는 오늘의 중국이 이룬 발전을 가능하게 한 요인들을 문화적, 역사적, 정치적 맥락에서 일별하는 동시에 중국 대륙과는 다른 역사적 경험(발전경로)을 거친 대만, 홍콩, 기타 화교권이 갖는 대륙과의 사회, 문화적 동질성과 차별성에 대한 이해를 통해 범중화권 사회에 대한 인식의 폭을 확장함으로써 미래의 변화에 능동적으로 대처할 역량을 키우는 데 그 목표를 둔다.");
            m2CultureIntro.add("본 강좌는 현재의 중국(대륙) 및 중국어권(대만, 홍콩 그리고 세계 각 지역의 화교사회)에 대해 그들의 언어와 문화적 특징을 살펴보고, 아울러 중국어와 계통상으로 유사하거나, 비록 계통은 같지 않지만 지대한 영향을 주고 받은 한국어, 일본어 등과 비교하는 것을 목적으로 한다. 한국을 포함해 전 세계에서 중국어에 대해 관심을 보이고 있다. 그렇지만 그러한 중국어에 대해 공시적, 통시적, 그리고 문화적 배경을 토대로 언어적 특징을 제시한 과목은 거의 존재하지 않았다. 이 때문에 중국어 학습은 그들의 깊고 풍부한 문화적 배경이 배제된 상황에서 이루어져 무미건조한 것이 되었다. 본 강좌는 이러한 점을 극복하고 중국인이라는 사람을 기준으로, 그들이 사용하고 있는 언어라는 면에 착안하여 사람이 살면서 보이는 문화적 특징을 가미하여 언어를 설명하고자 한다. 이를 위해 중국어의 기본적 특징을 우선 습득하고, 이를 토대로 중국내의 소수민족의 언어와의 비교, 표준어와 방언의 비교, 한장어계의 비교 및 한국인으로서 중국어를 배울 때 나타나는 특징 등을 골고루 적용하여 중국어에 대한 이해의 폭을 넓히고자 한다.");

            for (int i = 0; i < m2CultureName.size(); i++) {
                int professorIndex = ran.nextInt(c1m2Professor.size());
                int lectureRoomIndex = ran.nextInt(c1LectureRoom.size());
                Lecture lectureTmp = null;
                lectureTmp = new Lecture(m2CultureName.get(i), m2CultureIntro.get(i),
                        3, "월, 화", "09:00", "11:00", Lecture_Type.선택교양,
                        c1m2Professor.get(professorIndex), 30, "2024년 1학기",
                        1, null, c1Major.get(1), c1LectureRoom.get(lectureRoomIndex));
                em.persist(lectureTmp);
            }

            List<String> m2MajorName = new ArrayList<>();
            m2MajorName.add("중국어학의 이해");
            m2MajorName.add("중국의 대중문학");
            m2MajorName.add("중국현대명작의 세계");
            m2MajorName.add("중국고전문학탐색");
            m2MajorName.add("(필)한문강독1");
            m2MajorName.add("(필)중국문학사");
            m2MajorName.add("(필)고급중국어");
            m2MajorName.add("한자의 세계");
            m2MajorName.add("한문강독2");
            m2MajorName.add("중국역대시가강독1");
            m2MajorName.add("중국역대시가강독2");
            m2MajorName.add("한문문법");
            m2MajorName.add("현대중국소설");
            m2MajorName.add("중국소설과 문화");
            m2MajorName.add("중국어음성학");
            m2MajorName.add("중국어어휘론");
            m2MajorName.add("현대중국의 영화와 사회");
            m2MajorName.add("중국현대문학강독");
            m2MajorName.add("고대중국의 텍스트와 현대사회");
            m2MajorName.add("중국역대산문강독1");
            m2MajorName.add("중국역대산문강독2");
            m2MajorName.add("중국 전통문화의 의미와 현대 중국 (영어 강의)");
            m2MajorName.add("중국어문법");
            m2MajorName.add("중국어학특강");
            m2MajorName.add("중국어교육론");
            m2MajorName.add("중국어교재연구 및 지도법");
            m2MajorName.add("중국어교과논리 및 논술");
            m2MajorName.add("중국현대문학론");
            m2MajorName.add("중국사곡강독");
            m2MajorName.add("중국공연예술");
            m2MajorName.add("시경·초사");
            m2MajorName.add("중국사회문화론특강");
            m2MajorName.add("중국어글쓰기");
            m2MajorName.add("중국어발표와 토론");
            m2MajorName.add("중국어문학논문쓰기");

            List<String> m2MajorIntro = new ArrayList<>();
            m2MajorIntro.add("이 강의는 중국어의 언어학적 특성을 이해하고, 언어 현상과 자료를 분석하는 기초적 방법을 탐색한다. 수강생은 음운학과 음성학, 어휘론, 통사론, 화용론, 문자학, 역사와 방언 등 다양한 언어학적 관점에서 중국어를 고찰함으로써, 중국어학의 기초 지식을 배양할 수 있다.");
            m2MajorIntro.add("이 과목은 역대로 중국인들에게 친숙하게 읽혔던 대중문학 작품들을 대상으로 하여, 먼저 개론적인 이해를 위하여 대중문학의 의미 및 가치, 사회적 전후 상황 등에 대해 살펴볼 것이며, 이후 개별적인 작품에 대한 심화된 접근과 부분적 강독이 이루어진다. 다루어지는 작품은 주로 소설과 희곡 장르에서 선별된다. 이 과정에서 학생들은 중국문학의 주요한 면모의 일부를 심도 있게 학습하는 기초를 마련한다.");
            m2MajorIntro.add("이 과목은 20세기 이후 창작된 중국현대문학 작품 중에서 명작으로 널리 인정받는 시, 소설, 희곡 등의 작품들을 학생들이 직접 읽고 감상할 수 있도록 개론적인 설명과 강독의 기회를 제공한다. 이를 바탕으로 향후 학생들은 중국현대문학비평과 중국현대시사, 중국현대소설사 등을 심도 있게 학습하는 기초를 마련한다.");
            m2MajorIntro.add("이 과목은 중국고전문학의 개념과 그 연구대상 및 연구 방법 등에 대하여 알아봄으로써 학생들이 보다 친근감을 느끼면서 중국고전문학에 접근할 수 있도록 한다. 구체적으로 학생들은 중국고전문학의 개념과 범위, 장르, 역사적 전개 과정, 미학적 특성, 전반적인 작품의 면모 등을 체계적으로 고찰한다. 이를 통하여 학생들은 본격적인 중국고전문학 연구를 위한 기초를 다지게 된다.");
            m2MajorIntro.add("한문 자료의 해독 능력은 중국문학 전공자들에게 필수적으로 요구되는 과제이다. 고등학교 과정과 교양과정에서 배운 한문 지식을 보다 체계화하고 직접 한문 자료를 다룰 수 있는 수준으로 고양시키는 동시에 좋은 문장을 보다 풍부하게 접할 기회를 제공하는 것이 이 강의의 목표이다. 이 강의에서는 한문 학습 효과를 극대화시킬 수 있는, 좋은 문장의 전범으로 ‘맹자’를 선택하여 강독하는 것을 기본적인 과제로 한다. ‘맹자’ 강독을 통해 전공자들은 한문에 관한 체계적인 지식을 쌓아가는 동시에 한문 자료를 다루는 방법을 배울 수 있을 것이다. (한문강독 2)와 연계하여 두 학기에 걸쳐 ‘맹자’의 완독을 목표로 강의를 진행한다.");
            m2MajorIntro.add("이 강좌는 전근대시기 중국문학 전반을 심도 있게 소개하는 것을 목적으로 개설된다. 중국문학사는 질과 양 두 측면에서 세계적으로 그 유례를 찾아볼 수 없는 방대한 자료를 축적하고 있다. 이 강좌에서는 그 중 주요 문학사적 현상을 중심으로 중국문학 전반에 대한 이해를 도모한다. 이를 통해 수강생들은 문학 작품에 대한 심도 있는 분석과 함께 전근대시기 중국문학사에 대한 안목을 넓히고 중국, 중국문화, 인문적 앎 등에 대한 안목과 통찰력을 갖출 수 있을 것이다.");
            m2MajorIntro.add("(초급중국어 1·2)와 (중급중국어 1·2) 과목을 기 수강한 전공자들을 대상으로 고급 수준의 문법과 작문, 독해 능력을 기르는 것이 이 과목의 학습 목표이다. 또한 다양한 독해를 통해 중국 문화의 심층을 이해함과 동시에 언어사용에 개재된 중국인들의 사고 패턴을 이해함으로써 자연스런 회화 구사 능력의 배양에 비중을 둔다.");
            m2MajorIntro.add("한자는 중국에서 사용되는 문자이면서 동시에 중국의 전통과 현대를 아우르는 문화적 요소를 담고 있으며, 중국을 비롯한 동아시아 공통의 문화유산으로서의 지위도 가지고 있다. 따라서 한자에 대한 학습은 중국의 고대와 현대에 대한 이해는 물론 한자문화권의 역사와 문화를 이해하는 데 필수적인 요소라고 할 수 있다. 이 과목은 한자의 유래와 발전 과정, 그리고 현대적 변용을 포함한 한자를 둘러싼 여러 가지 사항들에 대해 학습하도록 설계되어 있다. 이 과정을 통해 수강생은 한자 자체에 대한 흥미를 높일 수 있으며, 더 많은 한자를 학습하게 됨은 물론 현대 사회에서 한자가 담당하고 있는 역할과 전망에 대해서도 이해하게 될 것이다.");
            m2MajorIntro.add("한문 자료의 해독 능력은 중국문학 전공자들에게 필수적으로 요구되는 과제이다. 고등학교 과정과 교양과정에서 배운 한문 지식을 보다 체계화하고 직접 한문 자료를 다룰 수 있는 수준으로 고양시키는 동시에 좋은 문장을 보다 풍부하게 접할 기회를 제공하는 것이 이 강의의 목표이다. 이 강의에서는 한문 학습 효과를 극대화시킬 수 있는, 좋은 문장의 전범으로 ‘맹자’를 선택하여 강독하는 것을 기본적인 과제로 한다. ‘맹자’ 강독을 통해 수강자들은 한문에 관한 체계적인 지식을 쌓아가는 동시에 한문 자료를 다루는 방법을 배울 수 있을 것이다. (한문강독 1)과 연계하여 두 학기에 걸쳐 ‘맹자’의 완독을 목표로 강의를 진행한다.");
            m2MajorIntro.add("중국문학 내부의 여러 가지 전공 분야 가운데에서도 시는 특별한 중요성을 갖는 분야이다. 이 과목은 중국의 시가 작품들 가운데서 명편을 뽑아 심층적으로 감상, 분석함으로써 중국 시가에 대한 이해의 지평을 넓히는 것을 목표로 한다. 다루어야 할 작품의 범위를 고려하여 이 과목은 (중국역대시가강독 2)와 연계하여 강의한다.");
            m2MajorIntro.add("중국문학 내부의 여러 가지 전공 분야 가운데에서도 시는 특별한 중요성을 갖는 분야이다. 이 과목은 중국의 시가 작품들 가운데서 명편을 뽑아 심층적으로 감상, 분석함으로써 중국 시가에 대한 이해의 지평을 넓히는 것을 목표로 한다. 다루어야 할 작품의 범위를 고려하여 이 과목은 (중국역대시가강독 1)과 연계하여 강의한다.");
            m2MajorIntro.add("한문문법의 학습은 교양과정과 전공과정의 한문 교육을 통해 어느 정도 이루어지고 있지만, 종합적이고 체계적인 학습에는 한계가 있다. 본 강좌는 한문 문장을 언어학적인 관점에 입각한 문법적인 분석을 함으로써 고급수준의 한문 문장 해독 능력 배양의 바탕을 제공하고자 하는 목적을 갖고 있다. 따라서 본 강좌는 중어중문학과의 전공 교과목으로 설계되었지만, 한문 해독 능력이 전공에서 중요한 여러 전공자에게 유익한 강좌가 될 것이다.");
            m2MajorIntro.add("현대중국은 개혁개방 30년을 거치는 동안 대단히 역동적인 변화를 보여주고 있다. 인류 역사상 유례를 찾기 어려울 정도의 이 같은 변화의 양상은 문학에도 깊은 영향을 미쳤으며, 특히 그 속성상 인정세태의 변화를 날카롭고 깊이 있게 반영해내는 소설 장르에 폭넓게 흔적을 남기고 있다. 본 과목은 중국현대소설 작품들에 대한 깊이 있는 독해를 통해 현대중국이 겪고 있는 사회문화적 변모양상에 대한 보다 폭넓은 이해에 도달하는 것을 목적으로 개설된다. 한국어로 번역된 19세기말에서 최근까지의 다양한 단·중·장편 소설들을 미리 읽고 강의시간에 감상(발표), 논평, 상호 토론하는 방식으로 진행되기에 중국어 원문해독능력이 없는 저학년 전공자들에게도 보다 넓게 문호가 개방되어 있다.");
            m2MajorIntro.add("중국의 소설은 근대 이전 명청(明淸) 시기에 이미 확고한 작자-독자의 시장 체계와 독서 환경의 중심에 있었을 뿐만 아니라, 소위 ‘사대기서(四大奇書)’ 혹은 ‘육대기서(六大奇書)’라 불리는 작품들을 통해 당시의 문화 및 문학적 역량의 총결 혹은 정점으로서의 획기적인 성과를 보여주었다. 이러한 성과로 인해 이 시기 대표적 작품들은 근대의 변혁을 거쳐 現當代에 이르기까지 중국 소설의 고전으로서 확고부동한 지위를 얻게 되었을 뿐만 아니라, 오늘날 영화와 드라마, 애니메이션과 같은 각종 매체의 유력한 콘텐츠로서 끊임없이 재창작·재해석되는 대상이 되었다. 이 과목은 이러한 명청 시기 소설 작품들을 통해 당시 사회의 모습과 문화를 이해하고, 아울러 전통시기에서 현재에 이르는 중국 문화의 연속성과 근원적 특성을 탐색하기 위해 개설되었다. 이 수업에서는 중국 소설의 역사적 중요성 및 현대적 수용에 대해 살펴볼 것이며, 학생들은 중국 소설에 대한 탐색과 이해를 통해 중국의 전통과 현대를 아우르는 포괄적인 시각을 갖게 될 것이다. 뿐만 아니라 오늘날 이 작품들이 향유되는 방식을 고찰함으로써 시대와 공간을 초월하는 고전의 보편성이 어느 지점에서 형성되고 의미화 되었는지를 파악할 수 있을 것이다.");
            m2MajorIntro.add("이 교과목은 중국어 말소리의 특성을 이해하고 분석하는 능력을 기르는 데에 목표를 둔다. 음성언어 연구에 필요한 기본 개념과 방법을 익히고, 표준중국어의 말소리 목록을 이해한다. 자음과 모음, 성조, 강세가 표준중국어에서 실현되는 양상을 탐색하고, 한국어와의 공통성과 차이를 논의한다. 이를 토대로 언어 비교, 발음 습득 및 음성 인식, 합성 등의 영역으로 지식을 확장할 수 있는 능력을 배양한다.");
            m2MajorIntro.add("본 강좌는 중국어의 어휘 체계와 특성에 대한 언어학적인 고찰과 이해를 도모한다. 학생들은 중국어 단어의 구조, 의미, 기능적 특성을 이해하고 어휘와 사회의 상호작용, 외래어의 수용, 관용어구의 유형과 특성 등을 살펴볼 것이며, 아울러 중국어 어휘의 통시적 발전 과정에 대해서 함께 고찰한다.");
            m2MajorIntro.add("이 과목은 중국 영화사를 대표하는 영화 작품과 현대 중국 사회의 중요한 문제들을 잘 보여주는 영화 작품을 선별하여 감상하고, 이를 통해 중국 사회에 대한 통찰력 있는 시각과 깊이 있는 미학적 인식을 얻게 하는 것을 목표로 한다. 중국어를 모르는 학생도 수강할 수 있도록, 선별된 작품의 한국어 자막을 제공한다.");
            m2MajorIntro.add("이 과목은 20세기에 씌어진 중국현대문학 작품들 가운데 중요한 작품들을 뽑아 원어로 강독함으로써 작품에 대한 충실한 이해와 감상 능력의 계발을 도모하기 위해 개설되었다. 주로 20세기의 산문·소설·시를 다루게 된다. 이 과목은 <현대중국의 문학과 사회>와 연계되어 강의가 진행된다.");
            m2MajorIntro.add("이 강좌에서는 고대 중국의 텍스트를 문학적, 지성사적 차원에서 분석하고 그 현재적 의미를 탐구한다. 주지하듯이 고대 중국의 텍스트는 양적으로 방대하며 질적으로도 높은 수준을 갖추고 있다. 이 강좌에서는 이들 중 경서나 제자백가서 같은 고전 급의 텍스트, 역대로 문학 고전으로 꼽혀온 텍스트를 대상으로, 그들이 현대사회에서도 여전히 유의미한 지식과 지혜를 담고 있는 고전인 까닭을 다면적으로 분석한다.");
            m2MajorIntro.add("중국문학의 전개 과정에 있어 산문은 시와 더불어 가장 중심적인 위치를 점하여 왔다. 이 과목은 ≪서경≫에서 비롯한 중국의 산문이 제자서와 사전문 등을 거쳐 당송의 고문과 변려문으로 발전해가는 과정을 이해하고 중국산문의 특징과 고유의 미학적 구조를 해명하는 데 그 목적이 있다. 이 강의는 (중국역대산문강독 2)와 연계되어 진행된다.");
            m2MajorIntro.add("중국문학의 전개 과정에 있어 산문은 시와 더불어 가장 중심적인 위치를 점하여 왔다. 이 과목은 ≪서경≫에서 비롯한 중국의 산문이 제자서와 사전문 등을 거쳐 당송의 고문과 변려문으로 발전해가는 과정을 이해하고 중국산문의 특징과 고유의 미학적 구조를 해명하는 데 그 목적이 있다. 이 강의는 (중국역대산문강독 1)과 연계되어 진행된다.");
            m2MajorIntro.add("중국의 전통 문화에 보이는 여러 특징적인 양상을 살펴본 뒤, 그것이 갖는 의미가 무엇인지를 논제로 하여 강의를 진행한다. 그리고 그 의미가 현대 사회에 나타난 발현을 통하여 중국 전통문화와 현대 사회가 어떠한 상관성이 있는지에 대해 학생들과 토론한다. 중국의 전통 문화를 살펴보기 위하여 문학, 역사, 철학 등과 관련된 텍스트를 검토할 뿐만 아니라, 고고학과 인류학 방면의 기존 성과도 참고하게 될 것이다.");
            m2MajorIntro.add("중국어문법은 교양 과정과 전공 기초 과정의 중국어 교육을 통해 어느 정도 이루어지고 있으나 단편적이라는 한계를 지닌다. 본 강좌는 중국어문법을 종합적, 체계적으로 고찰하고 엄밀한 언어학적 관점에서 중국어문법을 분석적으로 이해함으로써 고급 수준의 중국어 회화·작문 및 독해 능력 구비에 기초를 제공하는 데에 그 목적이 있다.");
            m2MajorIntro.add("중국어학특강은 중국어 음성학, 음운론, 문자학, 형태론, 의미론, 통사론, 화용론 등의 영역에서 중국어에 대한 언어학적 지식을 심화하여 습득할 수 있는 교과목이다. 강의 주제는 이론적, 실천적으로 중요성을 지니는 중국어의 현상 분석 및 관련 이론으로 구성된다. 이를 토대로 중국어 연구에 필요한 언어학 이론을 이해하며, 언어습득, 컴퓨터언어학, 코퍼스언어학, 기계번역, 인공언어 등의 응용언어학 영역으로 지식을 확장하는 방법을 탐구한다.");
            m2MajorIntro.add("이 과목은 교직에 진출할 전공자들을 위해 개설되는 교직 교과목으로 중국어 교육과 관련된 제반 문제를 학습하는 데 그 목적이 있다. 특히 한국인의 중국어 학습이 일반적으로 범하는 여러가지 오류와 그 문제를 효과적으로 극복할 수 있는 방법 등이 구체적인 사례를 중심으로 고찰될 것이다.");
            m2MajorIntro.add("이 과목은 교직 교과목으로, 중국어 학습에 필수적이라 할 수 있는 교재의 개발과 교재에 대한 교수자 입장에서의 심층적 이해, 효율적인 지도 방법 등을 토론하기 위해 개설된 과목이다. 현장에서 중국어 교육을 담당할 전공자들은 이 과목의 수강을 통해 중국어 교수법을 체득하는 기회를 가지게 될 것이다.");
            m2MajorIntro.add("이 과목은 교직에 진출할 전공자들을 위해 개설되는 교직 교과목으로, 중국어 교과의 논리 및 논술 교육을 위해 개설된 과목이다. 특히 교육 및 학습과 관련하여 창의성 발달 지도에 중점을 두며 교과과정, 평가방법, 교수법 등을 폭넓게 다룰 것이다.");
            m2MajorIntro.add("1919년 오사 운동 이후의 중국현대문학은 백화문학운동을 필두로 민족형식논쟁·문예대중화논쟁·국방문학논쟁 등 수많은 논쟁을 거치는 한편 현실주의·낭만주의·현대주의 등의 다기한 문예사조가 교차되면서 전개돼 나간다. 이러한 논쟁과 다양한 문예사조에 대해 문인들의 검토가 진행되면서 중국현대문학은 풍부한 문학논의로 채워지게 된다. 의 이 과목은 논쟁사와 사조사의 관점에서 중국현대문학의 다양한 면모를 고찰하는 데 그 목표가 있다.");
            m2MajorIntro.add("중국시가문학에 있어 사와 곡은 정통 장르로 분류되는 시에 비해 그 문학사적 의의가 충분히 인정되지 못하였으며 그에 대한 연구도 충분히 이루어지지 않은 편이다. 이 과목은 사와 산곡의 텍스트를 충실히 읽고 그 고유의 미학적 구조와 시가발전사상의 의의를 이해하는 데 그 목적이 있다.");
            m2MajorIntro.add("중국의 고대에서부터 발전하여 현재까지 창작과 공연이 지속되는 희곡(戱曲, opera)과 강창(講唱, oral performance arts)의 역사와 내용을 고찰한다. 대본 강독과 무대 상연 고찰을 병행하여 그 문학성과 예술성을 이해 분석한다. 잡극(雜劇), 전기(傳奇), 곤곡(崑曲), 경극(京劇), 탄사(彈詞), 고사(鼓詞), 설서(說書) 등의 대표 작가와 작품을 섭렵하면서 그 사회적 문화적 함의를 탐구한다.");
            m2MajorIntro.add("시경은 중국문학사에 있어 가장 오래된 텍스트이며 후대의 문학발전에 가장 큰 영향을 끼친 텍스트이다. 초사 역시 중국문학의 기원을 살펴볼 수 있는 중요한 문헌이다. 따라서 시경과 초사의 연구는 중국문학의 성격과 발전방향을 이해하는 데 필수적이다. 본 강좌는 이러한 요구에 부응하기 위해 시경과 초사의 원문을 충실히 강독하고 문학적 특징과 의의, 후대의 영향 그리고 서로간의 연계성을 살펴보는 데 그 목적이 있다.");
            m2MajorIntro.add("이 강좌에서는 중국의 중요한 사회문화적 주제를 선별하여 강의하고, 이를 통해 학생들에게 문학과 언어에서 나타나는 모습의 배경을 추론하도록 유도한다. 주제는 문화인류학, 사회경제학, 정치학 등의 다양한 분야에서 선택되며, 독서는 주로 사회과학적인 관점에서 쓰여 진 서적을 중심으로 한다. 또 강의 및 독서의 경험과 학생들이 다른 전공 강의에서 접하는 문학작품 및 언어 현상과의 접목을 시도하는 보고서의 작성을 통해 학생들의 시야를 확대시킨다.\n");
            m2MajorIntro.add("대학에서의 중국어 교육은 중국어 구사능력 그 자체의 향상이 아니라 보다 넓은 중국어 사용권 속에서 중국어를 수단으로 한 지적 활동 및 의사소통 능력을 갖추도록 하는 것을 최종적 목표로 삼는다. 이 과목은 교양단계와 전공단계를 거쳐 대학에서 3년 이상 체계적인 중국어 교육을 받은 전공자들이 중국어 글쓰기를 통해 지적인 의사소통 활동에 참여할 수 있도록 하기 위해 개설된다. 기초적 문법사항 및 기초 구문 연습에 중점을 둔 초중급단계의 중국어작문 과목과 달리 전공필수과목인 고급중국어 과목을 수강했거나 그에 준하는 중국어 능력을 갖춘 학생들을 수강대상으로 하며, 고급의 중국어글쓰기 능력을 갖춘 원어민 교사가 담당하는 것을 원칙으로 한다.");
            m2MajorIntro.add("이 과목은 중국어글쓰기와 더불어 중국어를 실질적 의사소통 및 업무처리의 수단으로 활용하고자 하는 학생들을 위해 개설된다. 따라서 대학에서 개설된 교양 및 전공과정의 단계별 중국어 과정을 마친 후 전공필수 과목인 고급중국어 과목을 수강했거나 그에 준하는 중국어구사능력을 갖춘 전공자들을 수강대상으로 한다. 최근 중국 사회의 주요 이슈들을 다루기에 중국어 구사능력 이외에도 최근 중국 사회의 변화상을 반영하는 폭넓은 사회, 문화적 이슈들에 대한 관심과 일정한 이해를 필요로 하며, 이 과목의 수강을 통해 동 주제에 대한 심화된 이해와 향후 변화에 대한 예측능력의 향상 또한 기대할 수 있다. 중국어글쓰기 과목과 마찬가지로 높은 수준의 중국어 사고능력 및 논리 구사능력을 갖춘 원어민 교사가 담당하는 것을 원칙으로 한다.");
            m2MajorIntro.add("이 강좌에서는 중국어문학 논문의 작성에 필요한 역량을 다룬다. 연구주제의 설정, 논문체제의 구성, 연구방법의 모색, 연구대상의 설정 및 분석 등 학술논문 작성에 필요한 기본적 소양과 실제적인 기술을 익힌다. 또한 사고를 논리적으로 전개하는 역량과 정합적으로 표현하는 역량을 배양한다.");

            for (int i = 0; i < m2MajorName.size(); i++) {
                int professorIndex = ran.nextInt(c1m2Professor.size());
                int lectureRoomIndex = ran.nextInt(c1LectureRoom.size());
                Lecture lectureTmp = null;
                if (m2MajorName.get(i).contains("(필)")) {
                    lectureTmp = new Lecture(m2MajorName.get(i), m2MajorIntro.get(i),
                            3, "월, 화", "09:00", "11:00", Lecture_Type.전공필수,
                            c1m2Professor.get(professorIndex), 30, "2024년 1학기",
                            1, null, c1Major.get(1), c1LectureRoom.get(lectureRoomIndex));
                } else {
                    lectureTmp = new Lecture(m2MajorName.get(i), m2MajorIntro.get(i),
                            3, "월, 화", "09:00", "11:00", Lecture_Type.전공선택,
                            c1m2Professor.get(professorIndex), 30, "2024년 1학기",
                            1, null, c1Major.get(1), c1LectureRoom.get(lectureRoomIndex));
                }
                em.persist(lectureTmp);
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
