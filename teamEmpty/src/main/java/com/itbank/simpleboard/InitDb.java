//package com.itbank.simpleboard;
//
//import com.itbank.simpleboard.component.HashComponent;
//import com.itbank.simpleboard.entity.*;
//import lombok.RequiredArgsConstructor;
//import nonapi.io.github.classgraph.json.JSONUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//    private final InitService initService;
//    private static long count;
//
//    @PostConstruct
//    public void init() {
//        try {
//            initService.dbInit6(); // 1. 단과대학 + 강의실 + 학과
//            initService.generateDummyData(); // user (교수, 학생, 교직원) - 등록금
//            initService.insertCalendar(); // 학사 일정
//            initService.insertNotice(); // 공지사항
//            initService.c1addLecture();
//            /*            initService.dbInit4(); // 안씀; */
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//        private final EntityManager em;
//        private final HashComponent hashComponent;
//
//        public void dbInit6() throws Exception {
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
//
//            Major c1Major1 = new Major("국어국문학과", 3500000, college1);
//            Major c1Major2 = new Major("중어중문학과", 2900000, college1);
//            Major c1Major3 = new Major("영어영문학과", 3700000, college1);
//            Major c1Major4 = new Major("불어불문학과", 3700000, college1);
//            Major c1Major5 = new Major("언어학과", 3200000, college1);
//            Major c1Major6 = new Major("철학과", 3200000, college1);
//            em.persist(c1Major1);
//            em.persist(c1Major2);
//            em.persist(c1Major3);
//            em.persist(c1Major4);
//            em.persist(c1Major5);
//            em.persist(c1Major6);
//
//
//            Major c2Major1 = new Major("정치외교학부(정치학전공)", 2900000, college2);
//            Major c2Major2 = new Major("정치외교학부(외교학전공)", 2800000, college2);
//            Major c2Major3 = new Major("경제학부", 3000000, college2);
//            Major c2Major4 = new Major("사회학과", 3100000, college2);
//            Major c2Major5 = new Major("인류학과", 3200000, college2);
//            em.persist(c2Major1);
//            em.persist(c2Major2);
//            em.persist(c2Major3);
//            em.persist(c2Major4);
//            em.persist(c2Major5);
//
//            Major c3Major2 = new Major("통계학과", 5700000, college3);
//            Major c3Major5 = new Major("화학부", 5200000, college3);
//            Major c3Major6 = new Major("생명과학부", 5200000, college3);
//            Major c3Major7 = new Major("지구환경과학부", 5200000, college3);
//            em.persist(c3Major2);
//            em.persist(c3Major5);
//            em.persist(c3Major6);
//            em.persist(c3Major7);
//
//            Major c4Major1 = new Major("간호학과", 5200000, college4);
//            em.persist(c4Major1);
//
//            Major c5Major1 = new Major("경영학과", 5200000, college5);
//            em.persist(c5Major1);
//
//            Major c6Major1 = new Major("건설환경공학부", 5200000, college6);
//            Major c6Major2 = new Major("기계공학부", 5200000, college6);
//            Major c6Major3 = new Major("항공우주공학부", 5200000, college6);
//            Major c6Major4 = new Major("재료공학부", 5200000, college6);
//            Major c6Major5 = new Major("전기·정보공학부", 5200000, college6);
//            Major c6Major6 = new Major("컴퓨터공학부", 5200000, college6);
//            em.persist(c6Major1);
//            em.persist(c6Major2);
//            em.persist(c6Major3);
//            em.persist(c6Major4);
//            em.persist(c6Major5);
//            em.persist(c6Major6);
//
//            Major c7Major1 = new Major("식물생산과학부", 5100000, college7);
//            Major c7Major2 = new Major("산림과학부", 5000000, college7);
//            Major c7Major3 = new Major("응용생물화학부", 5100000, college7);
//            Major c7Major4 = new Major("식품·동물생명공학부", 5200000, college7);
//            em.persist(c7Major1);
//            em.persist(c7Major2);
//            em.persist(c7Major3);
//            em.persist(c7Major4);
//
//            Major c8Major1 = new Major("동양화과", 5200000, college8);
//            Major c8Major2 = new Major("서양화과", 5100000, college8);
//            Major c8Major3 = new Major("조소과", 4900000, college8);
//            em.persist(c8Major1);
//            em.persist(c8Major2);
//            em.persist(c8Major3);
//
//            Major c9Major1 = new Major("교육학과", 5200000, college9);
//            Major c9Major2 = new Major("국어교육과", 4900000, college9);
//            Major c9Major3 = new Major("영어교육과", 4800000, college9);
//            Major c9Major4 = new Major("불어교육과", 3700000, college9);
//            Major c9Major5 = new Major("독어교육과", 4900000, college9);
//            Major c9Major6 = new Major("사회교육과", 3900000, college9);
//            Major c9Major7 = new Major("역사교육과", 3900000, college9);
//            Major c9Major8 = new Major("지리교육과", 4100000, college9);
//            em.persist(c9Major1);
//            em.persist(c9Major2);
//            em.persist(c9Major3);
//            em.persist(c9Major4);
//            em.persist(c9Major5);
//            em.persist(c9Major6);
//            em.persist(c9Major7);
//            em.persist(c9Major8);
//
//            Major c10Major3 = new Major("식품영양학과", 4400000, college10);
//            Major c10Major4 = new Major("의류학과", 4300000, college10);
//            em.persist(c10Major3);
//            em.persist(c10Major4);
//
//            // makeLectureRoom 돌리면 1동 당 10개의 강의실 생김
//            // 계산 기준 학과 * 7 해서 반올림
//            // Map을 반환하는 거 삭제 => 강의에 사람도 넣어야 하는데 사람 만드는게 다른 메서드라서 어차피 메서드 분리해야했네
//
//            // c1 6개 학과 40개
//            makeLectureRoom(4, college1);
//            // c2 5개 학과 40개
//            makeLectureRoom(4, college2);
//            // c3 4개 학과 30개
//            makeLectureRoom(3, college3);
//            // c4 1개 학과 10개
//            makeLectureRoom(1, college4);
//            // c5 1개 학과 10개
//            makeLectureRoom(1, college5);
//            // c6 6개 학과 40개
//            makeLectureRoom(4, college6);
//            // c7 4개 학과 30개
//            makeLectureRoom(3, college7);
//            // c8 3개 학과 20개
//            makeLectureRoom(2, college8);
//            // c9 8개 학과 60개
//            makeLectureRoom(6, college9);
//            // c10 2개 학과 10개
//            makeLectureRoom(1, college10);
//        }
//
//        private void makeLectureRoom(int dong, College college) {
//            Random ran = new Random();
//            String[] floor = {"1", "2", "3", "4", "5"};
//            String[] roomNum = {"1", "2", "3", "4", "5", "6", "7"};
//            ArrayList<String> makeRoom = new ArrayList<>();
//            for (int i = 1; i <= dong; i++) {
//                while (makeRoom.size() < 10) {
//                    int floorRan = ran.nextInt(floor.length);
//                    int roomRan = ran.nextInt(roomNum.length);
//                    String room = i + " " + floor[floorRan] + "0" + roomNum[roomRan] + "호";
//                    if (!makeRoom.contains(room)) {
//                        makeRoom.add(room);
//                        LectureRoom lectureRoom = new LectureRoom(room, college);
//                        em.persist(lectureRoom);
//                    }
//                }
//                makeRoom.clear();
//            }
//        }
//
//        public void insertCalendar() throws Exception {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//
//            LocalDate startDate1 = LocalDate.parse("2024/03/01", formatter);
//            LocalDate endDate1 = LocalDate.parse("2024/03/04", formatter);
//
//            AcademicCalendar calendar1 = new AcademicCalendar("1학기 시작", startDate1, endDate1);
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
//
//            LocalDate startDate4 = LocalDate.parse("2024/04/13", formatter);
//            LocalDate endDate4 = LocalDate.parse("2025/04/17", formatter);
//
//            AcademicCalendar calendar4 = new AcademicCalendar("센텀 대학 축제", startDate4, endDate4);
//            em.persist(calendar4);
//        }
//
//        public void insertNotice() {
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
//                String randomResidentNumber1 = generateRandomResidentNumber();
//                String randomResidentNumber2 = generateRandomResidentNumber();
//                String randomResidentNumber3 = generateRandomResidentNumber();
//
//                String realPassword1 = randomResidentNumber1.split("-")[1]; // 주민 뒷자리
//                String realPassword2 = randomResidentNumber2.split("-")[1]; // 주민 뒷자리
//                String realPassword3 = randomResidentNumber3.split("-")[1]; // 주민 뒷자리
//
//                String randomSalt1 = hashComponent.getRandomSalt();
//                String randomSalt2 = hashComponent.getRandomSalt();
//                String randomSalt3 = hashComponent.getRandomSalt();
//
//
//                java.sql.Date sqlCreatedAt = java.sql.Date.valueOf(generateRandomDate());
//
//
//                User user1 = new User(hashComponent.getHash(realPassword1, randomSalt1), randomSalt1, generateRandomName(), randomResidentNumber1, generateRandomKoreanAddress(), generateRandomPhoneNumber(), generateRandomString(8) + i + "@example.com", User_role.교수, sqlCreatedAt);
//                em.persist(user1);
//
//                User user2 = new User(hashComponent.getHash(realPassword2, randomSalt2), randomSalt2, generateRandomName(), randomResidentNumber2, generateRandomKoreanAddress(), generateRandomPhoneNumber(), generateRandomString(8) + i + "@example.com", User_role.교직원, sqlCreatedAt);
//                em.persist(user2);
//
//                User user3 = new User(hashComponent.getHash(realPassword3, randomSalt3), randomSalt3, generateRandomName(), randomResidentNumber3, generateRandomKoreanAddress(), generateRandomPhoneNumber(), generateRandomString(8) + i + "@example.com", User_role.학생, sqlCreatedAt);
//                user3.setUser_id(studentNum + "");
//                em.persist(user3);
//
//
//                if (count == 0) {
//                    count = em.createQuery("SELECT COUNT(e) FROM Major e", Long.class).getSingleResult();
//                }
//                Random random = new Random();
//                Long majorId = Math.abs(random.nextLong()) % count + 1L; // 1부터 n까지의 랜덤값 생성
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
//            // 장학금
//            Random random = new Random();
//            for (int year = 2021; year <= 2024; year++) {   // 2021년 1학기~20242학기까지 반복 생성
//                for (int quarter = 1; quarter <= 2; quarter++) {
//                    // 내부 장학금 생성
//                    Scholarship scholarship1 = new Scholarship("내부", "성적우수장학금", 1000000, year, quarter);
//                    em.persist(scholarship1);
//                    Scholarship scholarship2 = new Scholarship("내부", "교내근로장학금", 240500, year, quarter);
//                    em.persist(scholarship2);
//                    Scholarship scholarship3 = new Scholarship("내부", "맞춤형장학금", 800000, year, quarter);
//                    em.persist(scholarship3);
//                    Scholarship scholarship4 = new Scholarship("내부", "생활지원근로장학금", 332400, year, quarter);
//                    em.persist(scholarship4);
//
//                    // 외부 장학금 생성
//                    Scholarship scholarship5 = new Scholarship("외부", "센텀대학교발전재단장학금", 2500000, year, quarter);
//                    em.persist(scholarship5);
//                    Scholarship scholarship6 = new Scholarship("외부", "선한인재장학금", 300000, year, quarter);
//                    em.persist(scholarship6);
//                    Scholarship scholarship7 = new Scholarship("외부", "해외수학장학금", 1500000, year, quarter);
//                    em.persist(scholarship7);
//                    Scholarship scholarship8 = new Scholarship("외부", "생활지원장학금", 2000000, year, quarter);
//                    em.persist(scholarship8);
//                    Scholarship scholarship9 = new Scholarship("외부", "국가장학금(유형1)", 3433000, year, quarter);
//                    em.persist(scholarship9);
//                    Scholarship scholarship10 = new Scholarship("외부", "국가장학금(지역인재)", 2250000, year, quarter);
//                    em.persist(scholarship10);
//                    Scholarship scholarship11 = new Scholarship("외부", "국가장학금(다자녀)", 2600000, year, quarter);
//                    em.persist(scholarship11);
//                    Scholarship scholarship12 = new Scholarship("외부", "교외장학금", 500000, year, quarter);
//                    em.persist(scholarship12);
//                    Scholarship scholarship13 = new Scholarship("외부", "학업장려금", 145000, year, quarter);
//                    em.persist(scholarship13);
//
//                    // 장학금 리스트
//                    List<Scholarship> scholarships = Arrays.asList(scholarship1, scholarship2, scholarship3, scholarship4,
//                            scholarship5, scholarship6, scholarship7, scholarship8, scholarship9, scholarship10,
//                            scholarship11, scholarship12, scholarship13);
//                    List<Student> students = new ArrayList<>();
//                    for (long i = 1L; i <= 100L; i++) {
//                        students.add(em.find(Student.class, i));
//                    }
//
//                    // 학생에게 장학금 랜덤하게 수여
//                    for (Student student : students) {
//                        // 장학금 랜덤 선택
//                        int randomIndex = random.nextInt(scholarships.size());
//                        Scholarship scholarship = scholarships.get(randomIndex);
//
//                        // 학생리스트에 속한 학생은 2021 1학기부터 2024 2학기까지 랜덤 장학금을 수여받음
//                        // 장학금 리스트 중 랜덤하게 장학금 선택하는 Scholarship_Award 생성 후 영속성 컨텍스트에 저장
//                        Scholarship_Award scholarshipAward = new Scholarship_Award(student, scholarship);
//                        em.persist(scholarshipAward);
//                    }
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
//
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
//        public void randomTimeLectureRoom(List<Professor> professorList, Major major, List<LectureRoom> lectureRoomList, List<String> lectureName, List<String> lectureIntro) {
//            Random ran = new Random();
//            for (int i = 0; i < lectureName.size(); i++) {
//                List<String> dayList = new ArrayList<>(List.of("월", "화", "수", "목", "금"));
//                List<String> startList = new ArrayList<>(List.of("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"));
//                List<String> endList = new ArrayList<>(List.of("11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"));
//
//                String randomDay = "";
//                String randomStart = "";
//                String randomEnd = "";
//                int iterran = ran.nextInt(2) + 2; // 2 또는 3
//                for (int j = 0; j < iterran; j++) {
//                    if (dayList.size() > 0 && startList.size() > 0 && endList.size() > 0) {
//                        StringBuilder sb = new StringBuilder(randomDay);
//                        StringBuilder sb2 = new StringBuilder(randomStart);
//                        StringBuilder sb3 = new StringBuilder(randomEnd);
//                        int randay = ran.nextInt(dayList.size());
//                        sb.append(dayList.get(randay) + ",");
//                        dayList.remove(randay);
//                        int randomIndex = ran.nextInt(startList.size());
//                        sb2.append(startList.get(randomIndex) + ",");
//                        sb3.append(endList.get(randomIndex) + ",");
//                        startList.remove(randomIndex);
//                        endList.remove(randomIndex);
//                        randomDay = sb.toString();
//                        randomStart = sb2.toString();
//                        randomEnd = sb3.toString();
//                    }
//                }
//
//                if (!randomDay.isEmpty()) randomDay = randomDay.substring(0, randomDay.length() - 1);
//                if (!randomStart.isEmpty()) randomStart = randomStart.substring(0, randomStart.length() - 1);
//                if (!randomEnd.isEmpty()) randomEnd = randomEnd.substring(0, randomEnd.length() - 1);
//
//                if (!professorList.isEmpty() && !lectureRoomList.isEmpty()) {
//                    int professorIndex = ran.nextInt(professorList.size());
//                    int lectureRoomIndex = ran.nextInt(lectureRoomList.size());
//                    Lecture lectureTmp = null;
//                    if (lectureName.get(i).contains("(탐)")) {
//                        String replace = lectureName.get(i).replace("(탐)", "");
//                        lectureTmp = new Lecture(replace, lectureIntro.get(i),
//                                ran.nextInt(3) + 1, randomDay, randomStart, randomEnd, Lecture_Type.전공탐색,
//                                professorList.get(professorIndex), 30, "2024년 1학기",
//                                ran.nextInt(3) + 1, null, major, lectureRoomList.get(lectureRoomIndex));
//                    } else if (lectureName.get(i).contains("(필)")) {
//                        String replace = lectureName.get(i).replace("(필)", "");
//                        lectureTmp = new Lecture(replace, lectureIntro.get(i),
//                                ran.nextInt(3) + 1, randomDay, randomStart, randomEnd, Lecture_Type.전공필수,
//                                professorList.get(professorIndex), 30, "2024년 1학기",
//                                ran.nextInt(3) + 1, null, major, lectureRoomList.get(lectureRoomIndex));
//                    } else {
//                        lectureTmp = new Lecture(lectureName.get(i), lectureIntro.get(i),
//                                ran.nextInt(3) + 1, randomDay, randomStart, randomEnd, Lecture_Type.전공선택,
//                                professorList.get(professorIndex), 30, "2024년 1학기",
//                                ran.nextInt(3) + 1, null, major, lectureRoomList.get(lectureRoomIndex));
//                    }
//                    em.persist(lectureTmp); // Lecture가 DB에 insert
//                }
//            }
//        }
//
//        public void randomTimeCultureRoom(List<Professor> professorList, Major major, List<LectureRoom> lectureRoomList, List<String> lectureName, List<String> lectureIntro) {
//            Random ran = new Random();
//            for (int i = 0; i < lectureName.size(); i++) {
//                List<String> dayList = new ArrayList<>(List.of("월", "화", "수", "목", "금"));
//                List<String> startList = new ArrayList<>(List.of("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"));
//                List<String> endList = new ArrayList<>(List.of("11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"));
//
//                String randomDay = "";
//                String randomStart = "";
//                String randomEnd = "";
//                int iterran = ran.nextInt(2) + 2; // 2 또는 3
//                for (int j = 0; j < iterran; j++) {
//                    if (dayList.size() > 0 && startList.size() > 0 && endList.size() > 0) {
//                        StringBuilder sb = new StringBuilder(randomDay);
//                        StringBuilder sb2 = new StringBuilder(randomStart);
//                        StringBuilder sb3 = new StringBuilder(randomEnd);
//                        int randay = ran.nextInt(dayList.size());
//                        sb.append(dayList.get(randay) + ",");
//                        dayList.remove(randay);
//                        int randomIndex = ran.nextInt(startList.size());
//                        sb2.append(startList.get(randomIndex) + ",");
//                        sb3.append(endList.get(randomIndex) + ",");
//                        startList.remove(randomIndex);
//                        endList.remove(randomIndex);
//                        randomDay = sb.toString();
//                        randomStart = sb2.toString();
//                        randomEnd = sb3.toString();
//                    }
//                }
//
//                if (!randomDay.isEmpty()) randomDay = randomDay.substring(0, randomDay.length() - 1);
//                if (!randomStart.isEmpty()) randomStart = randomStart.substring(0, randomStart.length() - 1);
//                if (!randomEnd.isEmpty()) randomEnd = randomEnd.substring(0, randomEnd.length() - 1);
//
//                if (!professorList.isEmpty() && !lectureRoomList.isEmpty()) {
//                    int professorIndex = ran.nextInt(professorList.size());
//                    int lectureRoomIndex = ran.nextInt(lectureRoomList.size());
//                    Lecture lectureTmp = null;
//
//                    String replace = lectureName.get(i);
//                    lectureTmp = new Lecture(replace, lectureIntro.get(i),
//                            ran.nextInt(3) + 1, randomDay, randomStart, randomEnd, Lecture_Type.교양,
//                            professorList.get(professorIndex), 30, "2024년 1학기",
//                            ran.nextInt(3) + 1, null, major, lectureRoomList.get(lectureRoomIndex));
//                    em.persist(lectureTmp); // Lecture가 DB에 insert
//                }
//            }
//        }
//
//        public void c1addLecture() {
//            // college1(인문대학)에 해당하는 학과의 강의 삽입
//            // college1에는 6개의 학과가 있음
//            // => 국어국문학과, 중어중문학과, 영어영문학과, 불어불문학과, 언어학과, 철학과
//
//            // Q1. LectureType의 필수교양과 선택교양을 "교양"으로 통일하는 건 어떤가?
//            // Q2. LectureType에 전공탐색을 추가하는 건 어떤가?
//            // M1. m3Major, m4Major, m5Major에서 어떤게 필수인지 모르겠음
//            // M2. m5부터는 반복문 안적어뒀음
//
//
//            // 먼저 대학교 목록을 받아온다.
//            List<College> collegeList = em.createQuery("select c from College c order by c.idx", College.class).getResultList();
//            // 대학에서 보유 중인 강의실 목록을 불러온다. 아래 구문은 collegeList의 첫 대학으로 되어있다
//            List<LectureRoom> c1LectureRoom = em.createQuery("select r from LectureRoom r where r.college.idx = :cidx order by r.idx", LectureRoom.class).setParameter("cidx", collegeList.get(0).getIdx()).getResultList();
//            // 대학에 소속된 전공 목록을 불러온다. 아래 구문은 collegeList의 첫 대학으로 되어있다
//            List<Major> c1Major = em.createQuery("select m from Major m where m.college.idx = :cidx order by m.idx", Major.class).setParameter("cidx", collegeList.get(0).getIdx()).getResultList();
//            // 해당 전공의 교수 목록을 불러온다. 아래 구문은 collegeList의 첫 대학의 첫 전공으로 되어있다
//            List<Professor> c1m1Professor = em.createQuery("select p from Professor p where p.major.idx = :midx", Professor.class).setParameter("midx", c1Major.get(0).getIdx()).getResultList();
//
//            // c1m1
//            List<String> m1LectureName = new ArrayList<>();
//            List<String> m1LectureIntro = new ArrayList<>();
//
//            m1LectureName.add("(필)한국어연구입문");
//            m1LectureIntro.add("이 과목은 한국어 연구, 즉 국어학은 어떠한 학문이며, 구체적으로 무엇을 연구하는지에 대한 정보를 제공하고, 동시에 국어가 어떠한 특징을 지닌 언어인지 알아봄으로써 학생들이 보다 쉽게 국어학에 접근할 수 있도록 한다. 구체적으로는 일반언어학과의 관계에서 정립되는 국어학의 위치, 국어학의 하위 분야들, 연구대상 및 범위, 국어의 역사적인 변화 양상, 현대국어의 음운, 문법, 어휘적 특징 등을 고찰한다. 이를 바탕으로 학생들이 더욱 깊이 있는 국어연구를 위한 기초를 다지도록 한다.");
//
//            m1LectureName.add("(탐)한국어문학연구입문");
//            m1LectureIntro.add("이 교과목은 한국문학의 연구 대상과 연구 방법은 무엇인가 하는 물음에 대해 충실한 답변을 제시함으로써 학생들이 우리 문학 전반에 대해 이해하고 우리 문학을 연구하는 데 필요한 기본 지식을 습득하도록 하는 데 목적을 둔다. 구체적으로는 한국문학의 개념과 범위, 갈래 체계와 역사적 전개 과정, 주제적, 미학적 특성, 전반적인 작품의 실상 등을 체계적으로 고찰한다. 이를 통해 학생들은 본격적인 한국문학 연구의 기초를 다지게 된다.");
//
//            m1LectureName.add("(탐)한국문학과 한국사회");
//            m1LectureIntro.add("이 교과목은 문학이 사회를 반영하면서 동시에 작품의 배경으로 삼기도 한다는 문학 일반의 원칙을 한국 문학 작품들을 통해 확인함으로써, 학생들이 문학과 실제 삶의 연관성을 이해하도록 하는 데 목적을 둔다. 구체적으로는 시, 소설, 평론 등으로 짜여진 한국 문학을 한국인의 삶의 현장인 한국사회의 관점에서 살펴보는 것을 주된 내용으로 삼는다. 이를 바탕으로 학생들은 본격적인 한국문학 연구를 위한 기초를 다지게 된다.");
//
//            m1LectureName.add("한국어음운론");
//            m1LectureIntro.add("이 과목은 음운론에 관한 일반적인 이론의 흐름을 개관하고 그것이 한국어에 어떻게 적용될 수 있는지 알아본다. 먼저 음소 설정 방법을 통해 한국어의 음소에는 어떤 것이 있는지 알아보고 그 음소들은 어떤 체계를 구성하고 있는지 알아본다. 나아가 음운규칙을 설정하고 어떤 음운과정이 있는지 살핀다. 이러한 공시음운론 외에 음운의 역사적 변화를 다루는 통시음운론도 여기서 다루어진다.");
//
//            m1LectureName.add("한국고전시가강독");
//            m1LectureIntro.add("고대가요에서부터 향가, 고려가요, 시조, 가사 등 고전시가 작품에 대한 전반적인 이해를 도모하고 문학 작품으로의 이해와 해석의 방법을 체득할 수 있도록 한다. 어법, 운율, 표현 방법 등에 유의하여 고전 시가 작품을 강독하면서 작품을 읽고 작품을 분석하고 그 의미를 해석하는 방법을 익히도록 한다.");
//
//            m1LectureName.add("한국현대희곡론");
//            m1LectureIntro.add("개화기 시기부터 최근의 희곡작품을 일차자료로 삼아 작품을 분석하는 능력을 배양하고 한국 현대 희곡의 흐름을 조망함으로써 희곡 연구의 기초를 닦는다. 구체적인 작품에 대한 평가와 그 작품의 공연과 관련된 사회사적 맥락을 아울러 살펴봄으로써 공연예술로서의 연극에 대한 미학적 이해를 아울러 시도한다.");
//
//            m1LectureName.add("(필)한국고전문학사");
//            m1LectureIntro.add("고대에서부터 구한말에 이르기까지 한국문학의 존재 양상과 생성⋅성장⋅소멸한 다양한 갈래의 존재 양상을 역사적으로 고찰함으로써 한국고전문학사의 전개의 양상과 원리를 찾아본다. 이를 위하여 첫째, 한국고전문학의 시대 구분, 갈래 체계, 작품에 대한 분석과 해석의 방법 등에 대한 기존의 연구와 학설을 점검하고, 둘째, 시대에 따른 작품의 실상을 살펴보게 될 것이다.");
//
//            m1LectureName.add("(필)한국현대문학사");
//            m1LectureIntro.add("개화기부터 1960년대까지 이르는 기간 동안 각 시기마다 문학사적으로 중심이 되는 비평, 소설, 시, 희곡 작품들을 대상으로 하여 현대한국문학사의 전개과정을 다룬다. 개화기의 신소설과 시가 문학에서 비롯, 이광수⋅최남선⋅김동인⋅염상섭 등에 의한 초기 신문학과 1920년대의 프로 문학, 1930년대의 사실주의와 모더니즘 문학, 해방 공간의 문학과 전후 문학 등을 거치는 한국현대문학사의 시각 속에서 작품들의 구체적인 위상을 파악한다.");
//
//            m1LectureName.add("한국고전산문강독");
//            m1LectureIntro.add("말과 문자로 이루어진 산문문학을 연구하기 위해서는 작품의 수집과 분류, 감상과 이해, 해석과 분석에 이르는 과정을 이해할 수 있어야 한다. 이 가운데 작품을 읽고 해석할 수 있는 능력은 그 기초가 되는 것으로, 고전산문이 갖는 독특한 독법을 체득하여 작품의 생생한 미감을 파악하도록 함으로써, 강독한 작품들에 대한 실제적인 분석을 시도하게 한다.");
//
//            m1LectureName.add("(필)한국어의역사");
//            m1LectureIntro.add("이 과목은 한국어가 고대에서부터 현대까지 변화해온 모습을 개관하는 데 목적을 둔다. 먼저 한국어의 계통과 형성에 대해 알아보고 한국어 역사의 시대구분 방법에 대해 논의한다. 이러한 시대구분이 이루어지면 표기법, 음운, 문법, 어휘체계 등으로 나누어 시대별 특징을 살펴본다. 그리고 그 특징을 비교함으로써 국어의 변화된 모습을 파악할 수 있도록 한다.\n");
//
//            m1LectureName.add("한국어문법론");
//            m1LectureIntro.add("한국어 문법론은 한국어 음운론과 함께 우리말의 구조를 다루는 한 분야이다. 음운론이 자음, 모음, 음절, 액센트 등 언어의 소리 쪽을 다룬다면 문법론은 형태소, 단어, 구, 문장 등 그 자체가 어떤 의미를 동반하고 있는 단위, 곧 문법 단위들을 다룬다. 이 과목은 현대국어를 대상으로 우리말의 다양한 문법 현상과 그 바탕에 깔려 있는 규칙들을 발견하고 이해해 나가는 데 그 목적이 있다.");
//
//            m1LectureName.add("한국고전소설론");
//            m1LectureIntro.add("고전소설 작품에 대한 전반적인 이해를 바탕으로, 고전소설의 미학적 특징과 그에 표현된 한국인의 생활 감정과 사상을 이해하고 작품을 분석하고 해석하는 연구의 방법을 체득하도록 한다. 이를 위하여 첫째, 작품 및 갈래 등에 대한 지금까지의 연구 성과를 점검하고, 둘째, 구체적인 작품이나 갈래를 대상으로 그 작품을 분석하고 해석하거나 갈래의 미학적 특징을 규명하는 방법을 실습한다.");
//
//            m1LectureName.add("한국현대시론");
//            m1LectureIntro.add("한국 현대시의 효과적인 이해를 위해서 시의 개별 요소에 대한 이론들을 일별하고 이론과 실제 창작 사이의 거리를 살펴본다. 아울러 그들이 한국시에 어떻게 적용․이해될 수 있는가를 설명, 이해시키고자 한다. 한국 현대시의 기능적 이해를 위한 이론을 익히고, 실제 작품 분석, 검토를 한다. 중요 내용은 시의 언어적 속성, 비유론, 상징론, 심상론, 운율, 형태론, 역설, 아이러니 등이다.");
//
//            m1LectureName.add("한국현대시인론");
//            m1LectureIntro.add("개화기 이후 1950년대까지의 한국 근대 시사에서 중요하다고 판단되는 시인을 선정하여 각 시인들의 연구사를 검토하고, 그 시인들에 부합되는 방법론을 채택하여 새로운 연구 방법을 제시하는 데까지 강의를 한다.");
//
//            m1LectureName.add("한국현대소설론");
//            m1LectureIntro.add("소설의 구조를 분석할 수 있는 일반이론을 소개하고 이론 자체의 계보적 특징을 습득한다. 그리하여 한국현대소설에 대한 엄정한 평가를 가능케 하는 이론적 기반을 마련하고, 내용과 형식의 연관을 통해 총체적으로 의미체를 파악하고 실증적으로 평가하는 태도를 기른다. 현대한국소설의 구조와 그 이론적 체계를 살펴보고, 현대소설의 전개과정에서 찾을 수 있는 문제점을 연구, 강독을 하는 강좌로서 현대한국소설이 지닌 특성과 서술기법, 구성의 조직, 작가의 작품 분석 방법론을 중심대상으로 한다.");
//
//            m1LectureName.add("한국현대작가론");
//            m1LectureIntro.add("작가론의 방법에 대한 이해를 토대로 하여 한국 현대 작가의 전반적 특징을 파악한 후 특정 작가의 작품세계를 파악하는 것을 목표로 한다. 현대 한국 작가를 대상으로 작가에 대한 연구사 검토와 작품 분석을 중심내용으로 하는 연구성과를 세미나 형식으로 검토함으로써 수강생들의 한국소설사에 대한 지식과 인식을 향상시키도록 한다.");
//
//            m1LectureName.add("한국한문학론");
//            m1LectureIntro.add("한국한문학에 대한 전반적인 이해를 바탕으로 한문학의 미학적 특징과 그에 표현된 한국인의 생활 감정과 사상을 이해하고 작품을 분석하고 해석하는 연구의 방법을 체득하도록 한다. 이를 위하여 첫째, 작품 및 갈래 등에 대한 지금까지의 연구 성과를 점검하고, 둘째, 구체적인 작품이나 갈래를 대상으로 그 작품을 분석하고 해석하거나 갈래의 미학적 특징을 규명하는 방법을 실습한다.");
//
//            m1LectureName.add("한국고전시가론");
//            m1LectureIntro.add("고대가요에서부터 향가, 고려가요, 시조, 가사 등 고전시가 작품에 대한 전반적인 이해를 바탕으로 고전시가의 미학적 특질과 그에 표현된 한국인의 생활 감정과 사상을 이해하고 작품을 분석하고 해석하는 연구의 방법을 체득하도록 한다. 이를 위하여 첫째, 작품 및 갈래 등에 대한 지금까지의 연구 성과를 점검하고, 둘째, 구체적인 작품이나 갈래를 대상으로 그 작품을 분석하고 해석하거나 갈래의 미학적 특징을 규명하는 방법을 실습한다.");
//
//            m1LectureName.add("한국어 정보의 전산처리");
//            m1LectureIntro.add("본 과목의 목표는 학생들로 하여금 컴퓨터를 이용하여 한국어 관련 정보(또는 자료)를 적절히 추출하고 처리할 수 있는 기초적인 능력을 기르게 하는 것이다. 정보 관련 기술과 산업이 발전함에 따라 많은 학문분야의 연구 내용과 방법론도 달라지고 있는바, 이러한 변화에 부응하기 위해서 개설된 과목이 본 과목이다. 본 과목을 통하여 학생들은 말뭉치의 구축, 말뭉치 가공, 가공된 말뭉치로부터의 언어정보 추출, 추출된 언어정보의 통계적 분석, 언어정보의 데이터베이스화, 데이터베이스의 운용 및 유지 등에 관한 기초적인 방법론을 익히게 될 것이며, 한국어문학 나아가서는 인문학을 위한 컴퓨터 활용 능력을 키우게 될 것이다.");
//
//            m1LectureName.add("한국어방언학");
//            m1LectureIntro.add("이 과목은 한국어 방언의 체계적인 연구를 위한 방법의 습득에 그 목적을 둔다. 우선 방언학과 관련한 여러 이론들을 소개한다. 주로 지리방언을 위주로 살펴보며 구체적으로 방언자료집을 통해 각 방언간의 음운⋅문법⋅어휘적 특징을 알아본다. 그리고 방언 차이에 의해 방언구획 작업을 하고 나아가 방언지도를 작성해 본다. 물론 이런 방언비교 뿐만 아니라 어느 한 방언을 택하여 공시론적인 연구를 할 수도 있다.");
//
//            m1LectureName.add("한국어학사");
//            m1LectureIntro.add("이 과목은 한국어에 대한 연구가 어떤 것들이 있었으며 그 경향의 변화 방향은 어떤지를 알아보는 것을 목적으로 한다. 먼저 한국어학사의 시대구분에 대해 알아본다. 그리고 각 시대별로 중요하다고 생각되는 학자들을 택하여 그 연구 성과가 어떠했는지 알아본다. 강의는 학생들이 한 학자씩 맡아 발표를 하고 이를 토론하는 형식으로 진행된다.");
//
//            m1LectureName.add("한국어의미론");
//            m1LectureIntro.add("언어를 음성과 의미의 결합이라고 볼 수 있는 점에서 의미는 언어에서 빼놓을 수 없는 중요한 요소이다. 의미론은 이러한 말의 의미를 다루는 분야이다. 이 과목은 한국어를 대상으로 의미의 의미, 단어 간의 의미 관계, 단어장과 성분분석, 의미의 변화 등을 살피고, 나아가 문장의 의미, 담화 의미 등에까지 관심을 넓힘으로써 한국어에 대한 이해의 폭을 넓히는 데 그 목적이 있다.");
//
//            m1LectureName.add("한국구비문학론");
//            m1LectureIntro.add("설화, 민요, 전통극 등 구비문학에 대한 전반적인 이해를 바탕으로 구비문학의 문학적 특징과 그에 표현된 한국인의 생활 감정과 사상을 이해하고 작품을 분석하고 해석하는 연구의 방법을 체득하도록 한다. 이를 위하여 첫째, 작품 및 갈래 등에 대한 지금까지의 연구 성과를 점검하고, 둘째, 구체적인 작품이나 갈래를 대상으로 그 작품을 분석하고 해석하거나 갈래의 미학적 특징을 규명하는 방법을 실습한다.");
//
//            m1LectureName.add("한국현대소설강독");
//            m1LectureIntro.add("개화기에서 1970년대까지 발표된 소설 작품들 가운데서 문제작을 선정하여 올바로 읽고, 분석하고, 평가하도록 한다.");
//
//            m1LectureName.add("한국현대문학비평");
//            m1LectureIntro.add("문학비평의 유형들과 개별 방법론을 검토하고 한국현대문학 연구에 어떻게 적용할 수 있는가를 실제 연구논문을 작성하면서 확인한다. 이와 함께 개화기 이후 진행된 실제비평을 통해 이론의 정합성과 적용가능성 및 한계 등을 검증해 본다. 문학작품에 대한 가치 평가의 행위인 비평작업을 통해 문학작품의 의미와 존재의의를 확인하는 비평문학에 대한 이론적 탐색을 통해 한국문학연구의 이론적 토대를 마련하는 것을 궁극의 목적으로 한다.\n");
//
//            m1LectureName.add("한국현대시강독");
//            m1LectureIntro.add("개화기의 여러 시가부터 최근의 시까지 1차 자료를 대상으로 하여 시 분석의 기초적인 능력을 배양한다. 발표 당시의 원문표기를 살린 자료를 대상으로 연구능력을 배양하는 훈련을 하며 동시에 시 분석을 위한 이론들을 재검토한다.");
//
//            m1LectureName.add("한국어어휘론");
//            m1LectureIntro.add("본 강좌는 한국어의 어휘항목들(단어, 연어, 관용표현 등)이 이루는 어휘구조에 대한 이론적 이해를 목적으로 한다. 단어의 내부구조와 단어형성, 단어의 차용, 어휘의미와 그 변화, 단어간 관계, 어휘 체계, 어휘 분류, 어휘의 계량, 단어의 다양한 변종들, 사전편찬 등을 다룬다. 어휘에 대한 이론적 이해가 어휘력 증진, 문학작품에 대한 이해, 한국의 문화와 한국인의 사고방식에 대한 측면적 이해 등으로 확대될 수 있도록 유의한다.\n");
//
//            m1LectureName.add("한국영상문학론");
//            m1LectureIntro.add("본 강좌는 한국 현대 영상예술의 흐름을 조망하고 학생들이 영상예술 작품을 분석하는 능력을 갖출 수 있도록 하여 영상예술 연구의 기초를 닦는 것을 목표로 한다. 이 강좌에서 학생들은 한국 영화 및 텔레비전 드라마 작품을 새로운 각도에서 해석함으로써 영상예술에 대한 심도 있는 이해를 얻을 수 있다. 또한 구체적인 작품에 대한 평가와 그 작품의 상영과 관련된 사회사적 맥락을 아울러 살펴봄으로써 개별 작품과 사회문화적인 맥락에 대한 균형 잡힌 이해를 도모할 것이다.");
//
//            m1LectureName.add("한국어학자료읽기");
//            m1LectureIntro.add("한국어 자료를 표기, 문자, 음운, 문법, 어휘의 면에서 자세히 읽고, 분석함으로써 한국어의 실상에 대한 이해의 폭을 넓힌다. 또 한국어 자료에 대한 서지, 문헌학적 접근을 통해 역사적 자료를 다루는 방법과 절차를 익히도록 한다.");
//
//            m1LectureName.add("세계 속의 한국문학");
//            m1LectureIntro.add("대중음악, 드라마, 영화 등 한국 문화가 전 세계적으로 전파되고 있는 한류와 세계화 시대에 한국문학도 세계적인 인기를 얻고 있다. 이에 따라 해외에서 한국 문학이 어떻게 표현 및 해석, 이해되어 왔는지를 살펴보는 것이 중요한 과제라고 본다. 이런 문맥에서 이 강의는 고전으로부터 현대에 이르기까지의 한국 문학 전반에 있어서 영어권에서 영어로 번역된 작품과 그 작품을 어떻게 이해하고 해석하는 지를 살펴보는 것을 내용으로 한다. 이를 통해 학생들이 앞으로 한국 문학을 세계화하는 데에 기여할 수 있도록 새로운 시각에서 본 한국 문학에 대한 이해를 높이고자 한다.");
//
//            m1LectureName.add("한국고전소설강독");
//            m1LectureIntro.add("한국고전소설은 대부분 필사본으로 전한다. 따라서 감상하고 이해하고 연구하기 위해서는 기본적으로 작품을 읽을 수 있는 능력을 갖추어야 한다. 이 교과목은 고전소설 독법의 기초 능력을 높이는 데 목적을 둔다. 한문 판각본과 필사본, 한글 판각본과 필사본 등 몇 가지 유형의 작품들을 골고루 선정하여 강독하며, 강독한 작품들에 대해서는 구체적인 분석과 해석을 시도할 것이다.");
//
//            m1LectureName.add("K문학의 이해");
//            m1LectureIntro.add("‘K-문학’이란 최근 세계에서 관심 대상으로 떠오르는 한국문학을 말한다. 2000년대 들어 한국문학은 세계의 관심 대상이 되었다. 작가 한강이 2016년 맨부커상 인터내셔널 부문을 수상했다. 2016년에는 작가 윤고은이 대거상을 수상하기도 했다. 소설뿐만 아니라 김소연, 김혜순 등 시인들도 세계의 주목을 받고 있다. 한국 영화가 세계의 각광을 받는 데는 시나리오의 힘이 아주 컸다고도 말할 수 있다. 이에 ‘K-문학’의 작가와 작품을 통하여 학생들이 동시대 한국문학을 넓게 이해할 수 있도록 한다.");
//
//            randomTimeLectureRoom(c1m1Professor, c1Major.get(0), c1LectureRoom, m1LectureName, m1LectureIntro);
////            for (int i = 0; i < m1LectureName.size(); i++) {
////                int professorIndex = ran.nextInt(c1m1Professor.size());
////                int lectureRoomIndex = ran.nextInt(c1LectureRoom.size());
////                Lecture lectureTmp = null;
////                if (m1LectureName.get(i).contains("(탐)")) {
////                    String replace = m1LectureName.get(i).replace("(탐)", "");
////                    lectureTmp = new Lecture(replace, m1LectureIntro.get(i),
////                            3, "월,화", "09:00,11:00", "11:00,13:00", Lecture_Type.전공탐색,
////                            c1m1Professor.get(professorIndex), 30, "2024년 1학기",
////                            1, null, c1Major.get(0), c1LectureRoom.get(lectureRoomIndex));
////                }
////                else if (m1LectureName.get(i).contains("(필)")) {
////                    String replace = m1LectureName.get(i).replace("(필)", "");
////                    lectureTmp = new Lecture(replace, m1LectureIntro.get(i),
////                            3, "월,화", "09:00,11:00", "11:00,13:00", Lecture_Type.전공필수,
////                            c1m1Professor.get(professorIndex), 30, "2024년 1학기",
////                            2, null, c1Major.get(0), c1LectureRoom.get(lectureRoomIndex));
////                } else {
////                    lectureTmp = new Lecture(m1LectureName.get(i), m1LectureIntro.get(i),
////                            3, "월,화", "09:00,11:00", "11:00,13:00", Lecture_Type.전공선택,
////                            c1m1Professor.get(professorIndex), 30, "2024년 1학기",
////                            2, null, c1Major.get(0), c1LectureRoom.get(lectureRoomIndex));
////                }
////                em.persist(lectureTmp);
////            }
//
//            // c1m2
//            List<Professor> c1m2Professor = em.createQuery("select p from Professor p where p.major.idx = :midx", Professor.class).setParameter("midx", c1Major.get(1).getIdx()).getResultList();
//
//            List<String> m2CultureName = new ArrayList<>();
//            List<String> m2CultureIntro = new ArrayList<>();
//
//            m2CultureName.add("초급한문1");
//            m2CultureIntro.add("본 교과목은 한자와 한문에 대해 거의 모르는 초보자를 위한 강좌로 한문에 대한 기초적인 이해와 지식의 배양에 중점을 둔다. 여기서는 한문의 품사와 기본 구조 등 문법에 대한 기초적인 이해를 기반으로 짧고 기본적인 구문이나 시가 등을 정밀하게 분석하고 감상한다. 이를 통해 기초적인 한문 독해력을 배양하며, 아울러 과제 등의 방식으로 일상생활에 쓰이는 한자어 실력도 증진시키고자 한다.");
//
//            m2CultureName.add("초급한문2");
//            m2CultureIntro.add("본 교과목은 초급한문1에 이어진 기초 한문 강좌로 기존의 한문1과 한문2 사이의 수준차를 완화하고 효율적인 학습을 도모하기 위해 개설된 강좌이다. 초급한문1에서 학습한 지식을 바탕으로 한문의 주요한 문형과 허사 등에 대한 이해를 추가하여 한문 문법에 대한 이해를 심화하고 이를 기반으로 단문 독해 연습을 집중적으로 한다. 중국은 물론 우리나라의 다양한 문장과 시가를 분석하고 감상함으로써 기초적인 한문 독해력을 배양하고 아울러 선인들의 사상과 동양문화에 대한 이해를 증진시키는 계기를 제공하고자 한다. 본 강좌를 통해 초급 수준의 한문 실력을 충실히 배양하게 되면 중급 이상의 한문 강좌를 수강하는데 무리가 없을 것이다.");
//
//            m2CultureName.add("중급한문");
//            m2CultureIntro.add("본 교과목은 초급한문1과 초급한문2에 이은 중급수준의 한문 강좌이다. 초급 한문 과정에서 다져진 기초 실력을 바탕으로 한문에 대한 이해를 심화하고 보다 발전된 한문 독해 실력을 배양하는 과정이다. 여기에서는 보다 완결된 형태의 문학, 철학, 역사 등 다방면의 고전 산문 문장과 시가 작품들을 익힐 수 있다. 이를 통해 한문 문법 지식을 체화하고 구문에 익숙해지도록 함과 동시에 선현들의 사상과 인생관 등을 비판적으로 검토해봄으로써 인문학적인 소양을 늘리는 기회를 가질 수 있을 것이다. 본 교과목까지 수강하면 고급 한문 강좌나 한문 관련 전공과목을 수강하는 데에도 무리가 없는 수준의 한문 실력을 배양할 수 있을 것이다.");
//
//            m2CultureName.add("한문명작읽기");
//            m2CultureIntro.add("본 교과목은 초급한문과 중급한문에 이은 고급 수준의 한문 명작에 대한 강좌이다. 초중급 한문 과정에서 다져진 실력을 바탕으로 고품격의 다양한 한문 문장을 정밀하게 읽어보는 과정이다. 초급한문과 중급한문에서는 주로 모범적이고 정격에 가까우며 비교적 평이한 문장을 다룬다. 그러다보니 유명하고 중요한 한문 문장임에도 초급자가 이해하기 쉽지 않다고 판단되면 배제되는 경우가 많았다. 본 강좌에서는 이러한 한문 문장을 경사자집(經史子集)의 다방면에 걸쳐 두루 선별하여 읽어보고자 한다. 본 교과목까지 수강하게 되면 정격적인 한문 문장뿐만 아니라 다소 파격적이고 난해한 문장에 대한 이해와 적응력을 높임으로써 한문을 깊이 배우거나 한문과 관련된 분야를 전공하려는 학문후속세대에게 자신감을 심어줄 수 있을 것이다.");
//
//            m2CultureName.add("초급중국어1");
//            m2CultureIntro.add("고등학교 과정에서 중국어를 배우지 않은 학생을 위한 초급 단계의 중국어 강의이다. 중국어 발음법을 정확하게 습득한 후 구문을 토대로 한 초급 문법을 학습하여 회화 및 독해와 작문의 기초를 확립하는 과정이다. 따라서 본 교과목은 이전 과정에서 중국어를 학습한 경험이 있는 학생의 수강은 권하지 않는다.");
//
//            m2CultureName.add("초급중국어2");
//            m2CultureIntro.add("고등학교 과정에서 2개 학기 이상 중국어를 배웠거나 중국어에 대한 초보적인 지식이 있는 학생을 위한 초급 두 번째 단계의 강의이다. 중국어 발음법을 숙지하고 문법과 작문에 기초 지식을 갖춘 학생이라면 누구나 들을 수 있다. 말하기, 듣기, 읽기, 쓰기와 문법 등 중국어 전반에 걸쳐 초급 단계의 학습을 완성하는 과정이다. 이 과목을 수강하면 기초 회화와 독해 능력을 갖출 수 있고, 향후 중급의 중국어를 학습할 수 있다.");
//
//            m2CultureName.add("중급중국어1");
//            m2CultureIntro.add("본 교과목은 초급중국어2 또는 고등학교에서 중국어를 4개 학기 이상 수강한 학생을 대상으로 말하기, 듣기, 읽기, 쓰기의 전 영역에 걸쳐 중급 수준의 중국어를 학습할 수 있도록 개설하였다. 초급중국어2를 이어 일상의 구어 듣기와 말하기, 중국의 고등학교 수준의 문장 읽기와 쓰기를 익힌다. 본 과목을 수강한 학생은 중국어를 모국어로 쓰는 사람들과 일상생활에서의 원활한 의사소통이 가능함은 물론 기본적인 문헌 해독에도 어려움이 없게 될 것이다. 나아가 중국어로 학문 연구 및 사회생활을 수행할 수 있는 고급 문헌 해독의 기초를 닦는 과정이므로 중국 관련 학문 및 직업에 종사하려는 학생은 반드시 수강해야 한다.");
//
//            m2CultureName.add("중급중국어2");
//            m2CultureIntro.add("본 교과목은 중급중국어1을 수강한 학생을 대상으로 중급중국어1을 이어 말하기, 듣기, 읽기, 쓰기 전 영역에 걸쳐 중급 수준의 중국어를 학습할 수 있도록 개설하였다. 수강생은 본 교과목을 수강한 후 중국어를 모국어로 쓰는 사람들과 일상생활에서의 원활한 의사소통이 가능함은 물론 중급 수준 이상의 문헌 해독에도 어려움이 없게 될 것이며, 또한 중어중문학과 전공과정에 개설된 현대 중국어 강독 교과목을 수강할 수 있다. 나아가 중국어로 학문 연구 및 사회생활을 수행할 수 있는 고급 문헌 해독의 기초를 닦는 과정이므로 중국 관련 학문 및 직업에 종사하려는 학생은 반드시 수강해야 한다.");
//
//            m2CultureName.add("중국어회화1");
//            m2CultureIntro.add("본 교과목은 중급중국어1 이상의 수준을 갖추고 있는 학생을 대상으로 회화에 중점을 두고 중급 이상의 중국어 말하기 듣기 훈련을 시킨다. 초급중국어 및 중급중국어1에서 익힌 중국어를 기반으로 한 본격적인 말하기와 듣기 훈련을 통해 수강생은 중국어를 모국어로 쓰는 사람들과 중급 이상의 원활한 의사소통이 가능하게 된다. 중국 현지에서 일상생활을 정상적으로 영위할 수 있는 수준의 회화 구사력을 갖출 수 있다. 이 과목에 이어 중국어회화2 교과목까지 수강한다면 고급의 회화 소통은 물론 중어중문학과 전공과정에 개설된 고급의 중국어문학 교과목을 수강하는 데에도 큰 어려움을 느끼지 않을 것이다.");
//
//            m2CultureName.add("중국어회화2");
//            m2CultureIntro.add("본 교과목은 중국어회화1 이상의 수준을 갖춘 학생을 대상으로 유창하고 우아한 소통에 중점을 두고 고급 중국어 말하기 듣기를 훈련시킨다. 따라서 본 교과목을 성실하게 수강한 학생은 중국어를 모국어로 쓰는 사람들, 특히 사회 지도층과 고급 중국어로 원활하고 우아하게 의사를 소통할 수 있게 된다. 또한 이 과목은 중어중문학과 전공과정에 개설된 고급의 중국어문학 과목을 수강하는 데에도 기초가 된다.");
//
//            m2CultureName.add("미디어중국어");
//            m2CultureIntro.add("본 과목은 중급 정도의 중국어 능력을 갖춘 학생들이 다양한 매체를 통해 입체적으로 중국 관련 정보와 지식을 습득 이해할 수 있게 만든다. 각종 미디어를 통해 제공되는 다양한 중국어 자료를 보고 듣고 분석함으로써 중국어 수준을 높임은 물론 중국어권 국가의 사회와 문화에 대한 이해를 넓히고 분석능력을 배양시킨다. 중국의 현실에 관심이 많고 장차 중국을 활동 영역으로 삼고자 하는 학생들에게 필수적인 과목이다.");
//
//            m2CultureName.add("동양의 고전");
//            m2CultureIntro.add("동양의 각 고전에는 우리나라의 고전과 중국의 경서, 제자집성, 교재류, 문학류 등이 포함되며, 연차적으로 일본, 인도 등의 고전이 포함될 것이며, 성경, 불경, 코란 등의 종교서도 포함될 것이다. 이 강의는 이상과 같은 동양의 각 고전에 나타나는 인간의 본질, 심성, 의무, 사고, 생활 등에 대한 견해를 분류 종합하고, 이에 대한 적절한 주석 및 번역을 가한 교재를 편찬한다. 교재에는 사고의 핵심 부분을 명시하여 학생들에게 이에 대한 생각을 하게 하고 이에 대한 리포트를 제출하게 하고 토론하게 한다. 이 강의는 학생들로 하여금 이 과정을 통하여 인간에 대한 신뢰와 사랑을 회복할 수 있기를 기대한다.");
//
//            m2CultureName.add("한자와 동양문화");
//            m2CultureIntro.add("한자는 갑골문부터만 계산해도 대략 3,500여 년의 역사를 가지고 있으며, 오랫동안 동양의 문화 전반에 걸쳐 큰 영향을 미쳐왔다. 특히 한중일 삼국은 한자를 매개로 한 문화적 공통점을 많이 가지고 있어 한자문화권에 속하는 것으로 불려진다. 본 교과목에서는 한자와 관련된 동양의 문화와 그 속에 담겨진 동양인의 사유 방식을 살펴보는 것을 그 목적으로 한다. 강의의 내용에는 다음과 같은 내용이 포함된다.\n" +
//                    "1) 한자의 역사와 변천 과정\n" +
//                    "2) 실생활에 많이 쓰이는 한자의 원래 의미\n" +
//                    "3) 한자와 농경문화, 한자와 동양인의 인명, 한자와 동양인의 금기 등의 한자와 고대인의 생활\n" +
//                    "4) 한자와 사군자, 한자와 서예처럼 한자와 현대인의 취미에 대한 부분\n" +
//                    "5) 한자와 디자인 등 직접 현실에 이용 가능한 동양의 문화\n" +
//                    "강의의 과정에서는 시청각 자료를 많이 활용하여 시각적 효과를 높이며, 실제 생활에 적용 가능한 문화적인 요소에 대하여 수강생 스스로 찾아오도록 유도할 것이다.");
//
//            m2CultureName.add("중국어권의 사회와 문화");
//            m2CultureIntro.add("이 강의는 갈수록 그 영향력이 증대되어 가는 현재의 중국(대륙) 및 중국어권(대만, 홍콩 그리고 세계 각 지역의 화교사회)의 사회, 문화적 상황 및 장래의 발전방향에 관심을 가진 학부생을 위해 개설되었다. 70년대 말 개혁개방을 시작한 이래 연평균 9%가 넘는 경제성장률을 이어온 중국의 발전상은 이제 미국과 어깨를 나란히 하는 글로벌 리더로 인정 받아가는 형국에 이르렀다. 중국이 이룬 급속한 발전의 배후에 대만, 홍콩 및 전 세계 화교네트워크와의 긴밀한 연계와 협력이 있었음은 물론이다. 본 강의에서는 오늘의 중국이 이룬 발전을 가능하게 한 요인들을 문화적, 역사적, 정치적 맥락에서 일별하는 동시에 중국 대륙과는 다른 역사적 경험(발전경로)을 거친 대만, 홍콩, 기타 화교권이 갖는 대륙과의 사회, 문화적 동질성과 차별성에 대한 이해를 통해 범중화권 사회에 대한 인식의 폭을 확장함으로써 미래의 변화에 능동적으로 대처할 역량을 키우는 데 그 목표를 둔다.");
//
//            m2CultureName.add("중국인의 언어와 문화");
//            m2CultureIntro.add("본 강좌는 현재의 중국(대륙) 및 중국어권(대만, 홍콩 그리고 세계 각 지역의 화교사회)에 대해 그들의 언어와 문화적 특징을 살펴보고, 아울러 중국어와 계통상으로 유사하거나, 비록 계통은 같지 않지만 지대한 영향을 주고 받은 한국어, 일본어 등과 비교하는 것을 목적으로 한다. 한국을 포함해 전 세계에서 중국어에 대해 관심을 보이고 있다. 그렇지만 그러한 중국어에 대해 공시적, 통시적, 그리고 문화적 배경을 토대로 언어적 특징을 제시한 과목은 거의 존재하지 않았다. 이 때문에 중국어 학습은 그들의 깊고 풍부한 문화적 배경이 배제된 상황에서 이루어져 무미건조한 것이 되었다. 본 강좌는 이러한 점을 극복하고 중국인이라는 사람을 기준으로, 그들이 사용하고 있는 언어라는 면에 착안하여 사람이 살면서 보이는 문화적 특징을 가미하여 언어를 설명하고자 한다. 이를 위해 중국어의 기본적 특징을 우선 습득하고, 이를 토대로 중국내의 소수민족의 언어와의 비교, 표준어와 방언의 비교, 한장어계의 비교 및 한국인으로서 중국어를 배울 때 나타나는 특징 등을 골고루 적용하여 중국어에 대한 이해의 폭을 넓히고자 한다.");
//
//            randomTimeCultureRoom(c1m2Professor, c1Major.get(1), c1LectureRoom, m2CultureName, m2CultureIntro);
//
//            List<String> m2MajorName = new ArrayList<>();
//            List<String> m2MajorIntro = new ArrayList<>();
//
//            m2MajorName.add("(탐)중국어학의 이해");
//            m2MajorIntro.add("이 강의는 중국어의 언어학적 특성을 이해하고, 언어 현상과 자료를 분석하는 기초적 방법을 탐색한다. 수강생은 음운학과 음성학, 어휘론, 통사론, 화용론, 문자학, 역사와 방언 등 다양한 언어학적 관점에서 중국어를 고찰함으로써, 중국어학의 기초 지식을 배양할 수 있다.");
//
//            m2MajorName.add("(탐)중국의 대중문학");
//            m2MajorIntro.add("이 과목은 역대로 중국인들에게 친숙하게 읽혔던 대중문학 작품들을 대상으로 하여, 먼저 개론적인 이해를 위하여 대중문학의 의미 및 가치, 사회적 전후 상황 등에 대해 살펴볼 것이며, 이후 개별적인 작품에 대한 심화된 접근과 부분적 강독이 이루어진다. 다루어지는 작품은 주로 소설과 희곡 장르에서 선별된다. 이 과정에서 학생들은 중국문학의 주요한 면모의 일부를 심도 있게 학습하는 기초를 마련한다.");
//
//            m2MajorName.add("(탐)중국현대명작의 세계");
//            m2MajorIntro.add("이 과목은 20세기 이후 창작된 중국현대문학 작품 중에서 명작으로 널리 인정받는 시, 소설, 희곡 등의 작품들을 학생들이 직접 읽고 감상할 수 있도록 개론적인 설명과 강독의 기회를 제공한다. 이를 바탕으로 향후 학생들은 중국현대문학비평과 중국현대시사, 중국현대소설사 등을 심도 있게 학습하는 기초를 마련한다.");
//
//            m2MajorName.add("(탐)중국고전문학탐색");
//            m2MajorIntro.add("이 과목은 중국고전문학의 개념과 그 연구대상 및 연구 방법 등에 대하여 알아봄으로써 학생들이 보다 친근감을 느끼면서 중국고전문학에 접근할 수 있도록 한다. 구체적으로 학생들은 중국고전문학의 개념과 범위, 장르, 역사적 전개 과정, 미학적 특성, 전반적인 작품의 면모 등을 체계적으로 고찰한다. 이를 통하여 학생들은 본격적인 중국고전문학 연구를 위한 기초를 다지게 된다.");
//
//            m2MajorName.add("(필)한문강독1");
//            m2MajorIntro.add("한문 자료의 해독 능력은 중국문학 전공자들에게 필수적으로 요구되는 과제이다. 고등학교 과정과 교양과정에서 배운 한문 지식을 보다 체계화하고 직접 한문 자료를 다룰 수 있는 수준으로 고양시키는 동시에 좋은 문장을 보다 풍부하게 접할 기회를 제공하는 것이 이 강의의 목표이다. 이 강의에서는 한문 학습 효과를 극대화시킬 수 있는, 좋은 문장의 전범으로 ‘맹자’를 선택하여 강독하는 것을 기본적인 과제로 한다. ‘맹자’ 강독을 통해 전공자들은 한문에 관한 체계적인 지식을 쌓아가는 동시에 한문 자료를 다루는 방법을 배울 수 있을 것이다. (한문강독 2)와 연계하여 두 학기에 걸쳐 ‘맹자’의 완독을 목표로 강의를 진행한다.");
//
//            m2MajorName.add("(필)중국문학사");
//            m2MajorIntro.add("이 강좌는 전근대시기 중국문학 전반을 심도 있게 소개하는 것을 목적으로 개설된다. 중국문학사는 질과 양 두 측면에서 세계적으로 그 유례를 찾아볼 수 없는 방대한 자료를 축적하고 있다. 이 강좌에서는 그 중 주요 문학사적 현상을 중심으로 중국문학 전반에 대한 이해를 도모한다. 이를 통해 수강생들은 문학 작품에 대한 심도 있는 분석과 함께 전근대시기 중국문학사에 대한 안목을 넓히고 중국, 중국문화, 인문적 앎 등에 대한 안목과 통찰력을 갖출 수 있을 것이다.");
//
//            m2MajorName.add("(필)고급중국어");
//            m2MajorIntro.add("(초급중국어 1·2)와 (중급중국어 1·2) 과목을 기 수강한 전공자들을 대상으로 고급 수준의 문법과 작문, 독해 능력을 기르는 것이 이 과목의 학습 목표이다. 또한 다양한 독해를 통해 중국 문화의 심층을 이해함과 동시에 언어사용에 개재된 중국인들의 사고 패턴을 이해함으로써 자연스런 회화 구사 능력의 배양에 비중을 둔다.");
//
//            m2MajorName.add("한자의 세계");
//            m2MajorIntro.add("한자는 중국에서 사용되는 문자이면서 동시에 중국의 전통과 현대를 아우르는 문화적 요소를 담고 있으며, 중국을 비롯한 동아시아 공통의 문화유산으로서의 지위도 가지고 있다. 따라서 한자에 대한 학습은 중국의 고대와 현대에 대한 이해는 물론 한자문화권의 역사와 문화를 이해하는 데 필수적인 요소라고 할 수 있다. 이 과목은 한자의 유래와 발전 과정, 그리고 현대적 변용을 포함한 한자를 둘러싼 여러 가지 사항들에 대해 학습하도록 설계되어 있다. 이 과정을 통해 수강생은 한자 자체에 대한 흥미를 높일 수 있으며, 더 많은 한자를 학습하게 됨은 물론 현대 사회에서 한자가 담당하고 있는 역할과 전망에 대해서도 이해하게 될 것이다.");
//
//            m2MajorName.add("한문강독2");
//            m2MajorIntro.add("한문 자료의 해독 능력은 중국문학 전공자들에게 필수적으로 요구되는 과제이다. 고등학교 과정과 교양과정에서 배운 한문 지식을 보다 체계화하고 직접 한문 자료를 다룰 수 있는 수준으로 고양시키는 동시에 좋은 문장을 보다 풍부하게 접할 기회를 제공하는 것이 이 강의의 목표이다. 이 강의에서는 한문 학습 효과를 극대화시킬 수 있는, 좋은 문장의 전범으로 ‘맹자’를 선택하여 강독하는 것을 기본적인 과제로 한다. ‘맹자’ 강독을 통해 수강자들은 한문에 관한 체계적인 지식을 쌓아가는 동시에 한문 자료를 다루는 방법을 배울 수 있을 것이다. (한문강독 1)과 연계하여 두 학기에 걸쳐 ‘맹자’의 완독을 목표로 강의를 진행한다.");
//
//            m2MajorName.add("중국역대시가강독1");
//            m2MajorIntro.add("중국문학 내부의 여러 가지 전공 분야 가운데에서도 시는 특별한 중요성을 갖는 분야이다. 이 과목은 중국의 시가 작품들 가운데서 명편을 뽑아 심층적으로 감상, 분석함으로써 중국 시가에 대한 이해의 지평을 넓히는 것을 목표로 한다. 다루어야 할 작품의 범위를 고려하여 이 과목은 (중국역대시가강독 2)와 연계하여 강의한다.");
//
//            m2MajorName.add("중국역대시가강독2");
//            m2MajorIntro.add("중국문학 내부의 여러 가지 전공 분야 가운데에서도 시는 특별한 중요성을 갖는 분야이다. 이 과목은 중국의 시가 작품들 가운데서 명편을 뽑아 심층적으로 감상, 분석함으로써 중국 시가에 대한 이해의 지평을 넓히는 것을 목표로 한다. 다루어야 할 작품의 범위를 고려하여 이 과목은 (중국역대시가강독 1)과 연계하여 강의한다.");
//
//            m2MajorName.add("한문문법");
//            m2MajorIntro.add("한문문법의 학습은 교양과정과 전공과정의 한문 교육을 통해 어느 정도 이루어지고 있지만, 종합적이고 체계적인 학습에는 한계가 있다. 본 강좌는 한문 문장을 언어학적인 관점에 입각한 문법적인 분석을 함으로써 고급수준의 한문 문장 해독 능력 배양의 바탕을 제공하고자 하는 목적을 갖고 있다. 따라서 본 강좌는 중어중문학과의 전공 교과목으로 설계되었지만, 한문 해독 능력이 전공에서 중요한 여러 전공자에게 유익한 강좌가 될 것이다.");
//
//            m2MajorName.add("현대중국소설");
//            m2MajorIntro.add("현대중국은 개혁개방 30년을 거치는 동안 대단히 역동적인 변화를 보여주고 있다. 인류 역사상 유례를 찾기 어려울 정도의 이 같은 변화의 양상은 문학에도 깊은 영향을 미쳤으며, 특히 그 속성상 인정세태의 변화를 날카롭고 깊이 있게 반영해내는 소설 장르에 폭넓게 흔적을 남기고 있다. 본 과목은 중국현대소설 작품들에 대한 깊이 있는 독해를 통해 현대중국이 겪고 있는 사회문화적 변모양상에 대한 보다 폭넓은 이해에 도달하는 것을 목적으로 개설된다. 한국어로 번역된 19세기말에서 최근까지의 다양한 단·중·장편 소설들을 미리 읽고 강의시간에 감상(발표), 논평, 상호 토론하는 방식으로 진행되기에 중국어 원문해독능력이 없는 저학년 전공자들에게도 보다 넓게 문호가 개방되어 있다.");
//
//            m2MajorName.add("중국소설과 문화");
//            m2MajorIntro.add("중국의 소설은 근대 이전 명청(明淸) 시기에 이미 확고한 작자-독자의 시장 체계와 독서 환경의 중심에 있었을 뿐만 아니라, 소위 ‘사대기서(四大奇書)’ 혹은 ‘육대기서(六大奇書)’라 불리는 작품들을 통해 당시의 문화 및 문학적 역량의 총결 혹은 정점으로서의 획기적인 성과를 보여주었다. 이러한 성과로 인해 이 시기 대표적 작품들은 근대의 변혁을 거쳐 現當代에 이르기까지 중국 소설의 고전으로서 확고부동한 지위를 얻게 되었을 뿐만 아니라, 오늘날 영화와 드라마, 애니메이션과 같은 각종 매체의 유력한 콘텐츠로서 끊임없이 재창작·재해석되는 대상이 되었다. 이 과목은 이러한 명청 시기 소설 작품들을 통해 당시 사회의 모습과 문화를 이해하고, 아울러 전통시기에서 현재에 이르는 중국 문화의 연속성과 근원적 특성을 탐색하기 위해 개설되었다. 이 수업에서는 중국 소설의 역사적 중요성 및 현대적 수용에 대해 살펴볼 것이며, 학생들은 중국 소설에 대한 탐색과 이해를 통해 중국의 전통과 현대를 아우르는 포괄적인 시각을 갖게 될 것이다. 뿐만 아니라 오늘날 이 작품들이 향유되는 방식을 고찰함으로써 시대와 공간을 초월하는 고전의 보편성이 어느 지점에서 형성되고 의미화 되었는지를 파악할 수 있을 것이다.");
//
//            m2MajorName.add("중국어음성학");
//            m2MajorIntro.add("이 교과목은 중국어 말소리의 특성을 이해하고 분석하는 능력을 기르는 데에 목표를 둔다. 음성언어 연구에 필요한 기본 개념과 방법을 익히고, 표준중국어의 말소리 목록을 이해한다. 자음과 모음, 성조, 강세가 표준중국어에서 실현되는 양상을 탐색하고, 한국어와의 공통성과 차이를 논의한다. 이를 토대로 언어 비교, 발음 습득 및 음성 인식, 합성 등의 영역으로 지식을 확장할 수 있는 능력을 배양한다.");
//
//            m2MajorName.add("중국어어휘론");
//            m2MajorIntro.add("본 강좌는 중국어의 어휘 체계와 특성에 대한 언어학적인 고찰과 이해를 도모한다. 학생들은 중국어 단어의 구조, 의미, 기능적 특성을 이해하고 어휘와 사회의 상호작용, 외래어의 수용, 관용어구의 유형과 특성 등을 살펴볼 것이며, 아울러 중국어 어휘의 통시적 발전 과정에 대해서 함께 고찰한다.");
//
//            m2MajorName.add("현대중국의 영화와 사회");
//            m2MajorIntro.add("이 과목은 중국 영화사를 대표하는 영화 작품과 현대 중국 사회의 중요한 문제들을 잘 보여주는 영화 작품을 선별하여 감상하고, 이를 통해 중국 사회에 대한 통찰력 있는 시각과 깊이 있는 미학적 인식을 얻게 하는 것을 목표로 한다. 중국어를 모르는 학생도 수강할 수 있도록, 선별된 작품의 한국어 자막을 제공한다.");
//
//            m2MajorName.add("중국현대문학강독");
//            m2MajorIntro.add("이 과목은 20세기에 씌어진 중국현대문학 작품들 가운데 중요한 작품들을 뽑아 원어로 강독함으로써 작품에 대한 충실한 이해와 감상 능력의 계발을 도모하기 위해 개설되었다. 주로 20세기의 산문·소설·시를 다루게 된다. 이 과목은 <현대중국의 문학과 사회>와 연계되어 강의가 진행된다.");
//
//            m2MajorName.add("고대중국의 텍스트와 현대사회");
//            m2MajorIntro.add("이 강좌에서는 고대 중국의 텍스트를 문학적, 지성사적 차원에서 분석하고 그 현재적 의미를 탐구한다. 주지하듯이 고대 중국의 텍스트는 양적으로 방대하며 질적으로도 높은 수준을 갖추고 있다. 이 강좌에서는 이들 중 경서나 제자백가서 같은 고전 급의 텍스트, 역대로 문학 고전으로 꼽혀온 텍스트를 대상으로, 그들이 현대사회에서도 여전히 유의미한 지식과 지혜를 담고 있는 고전인 까닭을 다면적으로 분석한다.");
//
//            m2MajorName.add("중국역대산문강독1");
//            m2MajorIntro.add("중국문학의 전개 과정에 있어 산문은 시와 더불어 가장 중심적인 위치를 점하여 왔다. 이 과목은 ≪서경≫에서 비롯한 중국의 산문이 제자서와 사전문 등을 거쳐 당송의 고문과 변려문으로 발전해가는 과정을 이해하고 중국산문의 특징과 고유의 미학적 구조를 해명하는 데 그 목적이 있다. 이 강의는 (중국역대산문강독 2)와 연계되어 진행된다.");
//
//            m2MajorName.add("중국역대산문강독2");
//            m2MajorIntro.add("중국문학의 전개 과정에 있어 산문은 시와 더불어 가장 중심적인 위치를 점하여 왔다. 이 과목은 ≪서경≫에서 비롯한 중국의 산문이 제자서와 사전문 등을 거쳐 당송의 고문과 변려문으로 발전해가는 과정을 이해하고 중국산문의 특징과 고유의 미학적 구조를 해명하는 데 그 목적이 있다. 이 강의는 (중국역대산문강독 1)과 연계되어 진행된다.");
//
//            m2MajorName.add("중국 전통문화의 의미와 현대 중국 (영어 강의)");
//            m2MajorIntro.add("중국의 전통 문화에 보이는 여러 특징적인 양상을 살펴본 뒤, 그것이 갖는 의미가 무엇인지를 논제로 하여 강의를 진행한다. 그리고 그 의미가 현대 사회에 나타난 발현을 통하여 중국 전통문화와 현대 사회가 어떠한 상관성이 있는지에 대해 학생들과 토론한다. 중국의 전통 문화를 살펴보기 위하여 문학, 역사, 철학 등과 관련된 텍스트를 검토할 뿐만 아니라, 고고학과 인류학 방면의 기존 성과도 참고하게 될 것이다.");
//
//            m2MajorName.add("중국어문법");
//            m2MajorIntro.add("중국어문법은 교양 과정과 전공 기초 과정의 중국어 교육을 통해 어느 정도 이루어지고 있으나 단편적이라는 한계를 지닌다. 본 강좌는 중국어문법을 종합적, 체계적으로 고찰하고 엄밀한 언어학적 관점에서 중국어문법을 분석적으로 이해함으로써 고급 수준의 중국어 회화·작문 및 독해 능력 구비에 기초를 제공하는 데에 그 목적이 있다.");
//
//            m2MajorName.add("중국어학특강");
//            m2MajorIntro.add("중국어학특강은 중국어 음성학, 음운론, 문자학, 형태론, 의미론, 통사론, 화용론 등의 영역에서 중국어에 대한 언어학적 지식을 심화하여 습득할 수 있는 교과목이다. 강의 주제는 이론적, 실천적으로 중요성을 지니는 중국어의 현상 분석 및 관련 이론으로 구성된다. 이를 토대로 중국어 연구에 필요한 언어학 이론을 이해하며, 언어습득, 컴퓨터언어학, 코퍼스언어학, 기계번역, 인공언어 등의 응용언어학 영역으로 지식을 확장하는 방법을 탐구한다.");
//
//            m2MajorName.add("중국어교육론");
//            m2MajorIntro.add("이 과목은 교직에 진출할 전공자들을 위해 개설되는 교직 교과목으로 중국어 교육과 관련된 제반 문제를 학습하는 데 그 목적이 있다. 특히 한국인의 중국어 학습이 일반적으로 범하는 여러가지 오류와 그 문제를 효과적으로 극복할 수 있는 방법 등이 구체적인 사례를 중심으로 고찰될 것이다.");
//
//            m2MajorName.add("중국어교재연구 및 지도법");
//            m2MajorIntro.add("이 과목은 교직 교과목으로, 중국어 학습에 필수적이라 할 수 있는 교재의 개발과 교재에 대한 교수자 입장에서의 심층적 이해, 효율적인 지도 방법 등을 토론하기 위해 개설된 과목이다. 현장에서 중국어 교육을 담당할 전공자들은 이 과목의 수강을 통해 중국어 교수법을 체득하는 기회를 가지게 될 것이다.");
//
//            m2MajorName.add("중국어교과논리 및 논술");
//            m2MajorIntro.add("이 과목은 교직에 진출할 전공자들을 위해 개설되는 교직 교과목으로, 중국어 교과의 논리 및 논술 교육을 위해 개설된 과목이다. 특히 교육 및 학습과 관련하여 창의성 발달 지도에 중점을 두며 교과과정, 평가방법, 교수법 등을 폭넓게 다룰 것이다.");
//
//            m2MajorName.add("중국현대문학론");
//            m2MajorIntro.add("1919년 오사 운동 이후의 중국현대문학은 백화문학운동을 필두로 민족형식논쟁·문예대중화논쟁·국방문학논쟁 등 수많은 논쟁을 거치는 한편 현실주의·낭만주의·현대주의 등의 다기한 문예사조가 교차되면서 전개돼 나간다. 이러한 논쟁과 다양한 문예사조에 대해 문인들의 검토가 진행되면서 중국현대문학은 풍부한 문학논의로 채워지게 된다. 의 이 과목은 논쟁사와 사조사의 관점에서 중국현대문학의 다양한 면모를 고찰하는 데 그 목표가 있다.");
//
//            m2MajorName.add("중국사곡강독");
//            m2MajorIntro.add("중국시가문학에 있어 사와 곡은 정통 장르로 분류되는 시에 비해 그 문학사적 의의가 충분히 인정되지 못하였으며 그에 대한 연구도 충분히 이루어지지 않은 편이다. 이 과목은 사와 산곡의 텍스트를 충실히 읽고 그 고유의 미학적 구조와 시가발전사상의 의의를 이해하는 데 그 목적이 있다.");
//
//            m2MajorName.add("중국공연예술");
//            m2MajorIntro.add("중국의 고대에서부터 발전하여 현재까지 창작과 공연이 지속되는 희곡(戱曲, opera)과 강창(講唱, oral performance arts)의 역사와 내용을 고찰한다. 대본 강독과 무대 상연 고찰을 병행하여 그 문학성과 예술성을 이해 분석한다. 잡극(雜劇), 전기(傳奇), 곤곡(崑曲), 경극(京劇), 탄사(彈詞), 고사(鼓詞), 설서(說書) 등의 대표 작가와 작품을 섭렵하면서 그 사회적 문화적 함의를 탐구한다.");
//
//            m2MajorName.add("시경·초사");
//            m2MajorIntro.add("시경은 중국문학사에 있어 가장 오래된 텍스트이며 후대의 문학발전에 가장 큰 영향을 끼친 텍스트이다. 초사 역시 중국문학의 기원을 살펴볼 수 있는 중요한 문헌이다. 따라서 시경과 초사의 연구는 중국문학의 성격과 발전방향을 이해하는 데 필수적이다. 본 강좌는 이러한 요구에 부응하기 위해 시경과 초사의 원문을 충실히 강독하고 문학적 특징과 의의, 후대의 영향 그리고 서로간의 연계성을 살펴보는 데 그 목적이 있다.");
//
//            m2MajorName.add("중국사회문화론특강");
//            m2MajorIntro.add("이 강좌에서는 중국의 중요한 사회문화적 주제를 선별하여 강의하고, 이를 통해 학생들에게 문학과 언어에서 나타나는 모습의 배경을 추론하도록 유도한다. 주제는 문화인류학, 사회경제학, 정치학 등의 다양한 분야에서 선택되며, 독서는 주로 사회과학적인 관점에서 쓰여 진 서적을 중심으로 한다. 또 강의 및 독서의 경험과 학생들이 다른 전공 강의에서 접하는 문학작품 및 언어 현상과의 접목을 시도하는 보고서의 작성을 통해 학생들의 시야를 확대시킨다.\n");
//
//            m2MajorName.add("중국어글쓰기");
//            m2MajorIntro.add("대학에서의 중국어 교육은 중국어 구사능력 그 자체의 향상이 아니라 보다 넓은 중국어 사용권 속에서 중국어를 수단으로 한 지적 활동 및 의사소통 능력을 갖추도록 하는 것을 최종적 목표로 삼는다. 이 과목은 교양단계와 전공단계를 거쳐 대학에서 3년 이상 체계적인 중국어 교육을 받은 전공자들이 중국어 글쓰기를 통해 지적인 의사소통 활동에 참여할 수 있도록 하기 위해 개설된다. 기초적 문법사항 및 기초 구문 연습에 중점을 둔 초중급단계의 중국어작문 과목과 달리 전공필수과목인 고급중국어 과목을 수강했거나 그에 준하는 중국어 능력을 갖춘 학생들을 수강대상으로 하며, 고급의 중국어글쓰기 능력을 갖춘 원어민 교사가 담당하는 것을 원칙으로 한다.");
//
//            m2MajorName.add("중국어발표와 토론");
//            m2MajorIntro.add("이 과목은 중국어글쓰기와 더불어 중국어를 실질적 의사소통 및 업무처리의 수단으로 활용하고자 하는 학생들을 위해 개설된다. 따라서 대학에서 개설된 교양 및 전공과정의 단계별 중국어 과정을 마친 후 전공필수 과목인 고급중국어 과목을 수강했거나 그에 준하는 중국어구사능력을 갖춘 전공자들을 수강대상으로 한다. 최근 중국 사회의 주요 이슈들을 다루기에 중국어 구사능력 이외에도 최근 중국 사회의 변화상을 반영하는 폭넓은 사회, 문화적 이슈들에 대한 관심과 일정한 이해를 필요로 하며, 이 과목의 수강을 통해 동 주제에 대한 심화된 이해와 향후 변화에 대한 예측능력의 향상 또한 기대할 수 있다. 중국어글쓰기 과목과 마찬가지로 높은 수준의 중국어 사고능력 및 논리 구사능력을 갖춘 원어민 교사가 담당하는 것을 원칙으로 한다.");
//
//            m2MajorName.add("중국어문학논문쓰기");
//            m2MajorIntro.add("이 강좌에서는 중국어문학 논문의 작성에 필요한 역량을 다룬다. 연구주제의 설정, 논문체제의 구성, 연구방법의 모색, 연구대상의 설정 및 분석 등 학술논문 작성에 필요한 기본적 소양과 실제적인 기술을 익힌다. 또한 사고를 논리적으로 전개하는 역량과 정합적으로 표현하는 역량을 배양한다.");
//
//            randomTimeLectureRoom(c1m2Professor, c1Major.get(1), c1LectureRoom, m2MajorName, m2MajorIntro);
//
//            // c1m3
//            List<Professor> c1m3Professor = em.createQuery("select p from Professor p where p.major.idx = :midx", Professor.class).setParameter("midx", c1Major.get(2).getIdx()).getResultList();
//
//            List<String> m3MajorName = new ArrayList<>();
//            List<String> m3MajorIntro = new ArrayList<>();
//
//            m3MajorName.add("(탐)영어학입문");
//            m3MajorIntro.add("영문과 전공탐색과목. 영어학의 여러 분야와 분야별 언어현상 및 탐구방법 등을 소개함으로써, 영어학이란 무엇인가에 대한 이해를 높이고, 영어학 연구의 내용, 방법, 활용 등에 대한 기본을 익히는 것을 목표로 한다. 전반부에는 주로 영어의 음성/음운 체계 및 어휘, 문장, 의미 구조와 관련한 다양한 현상을 다루고, 후반부에는 주로 영어사용의 화용적, 사회적 특성, 코퍼스 자료를 이용한 영어 연구, 영어습득 및 영어교육 등 응용분야를 다루되, 각 분야 별 특징적인 현상들에 초점을 맞춤으로써 영어학 연구에 대한 실질적인 이해를 돕는다.");
//
//            m3MajorName.add("(탐)영문학입문");
//            m3MajorIntro.add("영문과 전공 탐색 과목. 시, 소설, 드라마 등 각 장르에 걸쳐 작품들을 읽고 영문학 연구를 위한 기본 용어를 익히는 개론과목이다. 비교적 해독하기 쉽고 길지 않은 작품을 영미문학에서 고루 다룬다. 영문학의 기본 개념, 정의, 문학 언어의 성격, 표현양식, 비평의 방법, 시작법의 기본원리로서의 프로소디 등은 작품을 읽으면서 자연스럽게 습득하도록 한다. ");
//
//            m3MajorName.add("(탐)영미영작의 세계");
//            m3MajorIntro.add("영어영문학과 전공탐색과목. 영문학의 대표적인 고전들을 선별하여 읽으면서 문학 텍스트를 분석하는 법을 배운다. <영문학서설>에서보다 더 긴 작품들을 읽으며, 소설, 시, 희곡 중 두 장르 이상을 다루는 것을 원칙으로 한다.");
//
//            m3MajorName.add("영작문");
//            m3MajorIntro.add("영어영문학과 학생으로서 필요한 영어글쓰기 능력을 집중적으로 배양한다. 글쓰기 논리와 문체에 대해서는 물론 어법과 기술적인 면에 대해서도 체계적인 지도를 하며, 적절한 길이와 난이도의 영미문학 및 문화 텍스트를 분석대상으로 활용하여 읽기와 쓰기를 연결시킨다. 영어 글쓰기 능력에 있어 중·상급 이상의 학생을 대상으로 하는 교과목으로 수준 높은 문장 구사력과 논리적인 논지 전개 능력을 기르는데 주력한다.");
//
//            m3MajorName.add("영어음성학");
//            m3MajorIntro.add("이 강좌는 영어화자들이 사용하는 언어음의 조음적 특징을 살펴보고 이해하는 것을 주목적으로 한다. 언어음이 발화될 때 어떠한 조음기관이 사용되고 그 기관이 어떠한 모습을 보이는지를 알아보는 것이 조음적 특징을 이해하는 것이다. 영어의 변별적 음의 조음적 특징을 이해한 후, 다수의 음이 연쇄적으로 발화될 때 음들 상호간에 어떠한 현상이 나타나고 그것이 왜 발생하는지를 이해하는 것도 이 강좌가 추구하는 목적 중의 하나이다.");
//
//            m3MajorName.add("영어와 사회");
//            m3MajorIntro.add("이 과목에서는 영어를 사회언어학적 관점으로 관찰하는데 필요한 기본 용어 및 개념들을 알아보고 국가별 표준어, 지역방언, 사회적 방언, 레지스터 등을 포함하는 다양한 형태의 영어 변이형들을 탐구해 본다. 아울러 영어가 지구화 시대에 영어를 제 2 언어 혹은 외국어로 배우고 사용하는 국가들의 사회, 문화, 언어에 영어가 끼치는 영향에 대해서도 살펴본다.");
//
//            m3MajorName.add("(필)영국문학개관 1");
//            m3MajorIntro.add("앵글로색슨 시대부터 18세기 말까지의 영국문학을 조망하는 과목이다. 다양한 장르와 전통을 대표하는 작품을 선별해서 읽으며, 시대 간의 차이와 연속성에 특히 주목한다. 개별 텍스트를 사회문화적 맥락, 시대적 감수성과 연계해서 이해한다.");
//
//            m3MajorName.add("(필)미국문학개관");
//            m3MajorIntro.add("식민지시대초기부터 현재 시대까지의 미국문학을 개관하는 과목으로, 여러 장르에 걸쳐 주요작가들이 미국문학전통에 어떻게 기여했는지 살펴본다. 브래드스트리트, 프랭클린, 호손, 에머슨, 멜빌, 휘트먼, 포크너, 프로스트, 윌리엄스, 포크너, 로웰, 모리슨 등을 포함한 미국문학의 주요작가의 작품을 역사적, 사회적, 문화적 맥락에서 읽음으로써 미국문학사 전반에 대한 이해를 넓히도록 한다.");
//
//            m3MajorName.add("19세기 영국시");
//            m3MajorIntro.add("19세기 영국시를 프랑스혁명, 산업혁명, 빅토리아시대의 문화, 대영제국 확장의 맥락에서 선별적으로 읽는다. 바볼드, 샬럿 스미스, 블레이크, 워즈워스, 코울리지, 바이런, 셸리, 키츠와 같은 낭만주의 시인들과, 테니슨, 로버트와 엘리자베스 브라우닝, 크리스티나 로제티, 아놀드와 같은 빅토리아시대의 시인들을 함께 읽는다.");
//
//            m3MajorName.add("현대미국소설");
//            m3MajorIntro.add("20세기 이후 현대 미국소설의 주요 작품을 선별하여 읽는다. 20세기 전반 모더니즘 문학의 전체적 흐름을 파악하는 동시에 제2차 세계대전 이후 미국소설의 변화된 문학적 감수성과 형식, 주제, 문화적 맥락을 이해한다. 흑인문학을 비롯한 소수민족문학의 소설적 성과 또한 점검한다.");
//
//            m3MajorName.add("영어담화분석");
//            m3MajorIntro.add("응용언어학의 한 분야인 담화분석에 대해 소개하고 대화분석, 상호작용적 사회언어학, 비판적 담화분석 등 담화분석의 제반 이론 및 분석방법론을 살펴본다. 아울러 이들 방법론을 이용하여 다양한 장르의 영어 담화를 분석해 봄으로써 구체적 상황 맥락에서의 영어의 사용에 대한 이해를 돕고 언어의 기능을 분석하는 능력을 높인다.");
//
//            m3MajorName.add("코퍼스 영어학");
//            m3MajorIntro.add("이 과목에서는 전산 코퍼스에 기반한 영어 연구 이론, 연구 방법, 응용 기술 등을 습득한다. 코퍼스를 활용한 언어 분석의 기초를 익히고, 영어학 연구 제 분야에서 필요로 하는 영어 자료 관찰 및 분석 기술을 습득하고, 코퍼스 자료를 활용한 영어학 및 영어 교육 관련 논문을 읽는다. 컴퓨터로 영어 코퍼스를 언어학적으로 분석하고 연구하는 능력을 함양하여 궁극적으로 학부 졸업 논문 수준의 연구 논문을 완성하는 것을 목표로 한다.");
//
//            m3MajorName.add("(필)영국문학개관 2");
//            m3MajorIntro.add("19세기 초부터 20세기 말까지의 영국문학을 조망하는 과목이다. 다양한 장르와 전통을 대표하는 작품을 선별해서 읽으며, 시대 간의 차이와 연속성에 특히 주목한다. 개별 텍스트를 사회문화적 맥락, 시대적 감수성과 연계해서 이해한다.");
//
//            m3MajorName.add("셰익스피어");
//            m3MajorIntro.add("셰익스피어의 희곡을 집중적으로 강독한다. 오늘의 영어와 상이한 어법. 단어와 어귀의 의미를 정확하게 파악하고, 극적 아이러니와 심상 등 여러 가지 시적 요소와 플롯, 주제, 성격 등 여러 가지 극적 요소들의 분석을 통해서 셰익스피어의 극예술을 올바르게 이해하고 감상하도록 유의한다.");
//
//            m3MajorName.add("영문학과 대중문화");
//            m3MajorIntro.add("영화, TV, 공연, 그래픽 노블, 음악, 비디오 게임, 인터넷 등 다양한 형식과 매체를 통해 발달해온 대중문화와 영문학의 접점을 탐구하는 과목이다. 선별된 영문학 작품을 읽고 관련된 대중문화를 살펴봄으로써 영문학과 대중문화의 상호 영향 관계에 대한 심도 있는 이해를 도모한다.");
//
//            m3MajorName.add("현대영국소설");
//            m3MajorIntro.add("20세기 이후 현대 영국소설의 주요 작품을 선별하여 읽는다. 20세기 전반 모더니즘 문학의 전체적 흐름을 파악하는 동시에 제2차 세계대전 이후 영국소설의 변화된 문학적 감수성과 형식, 주제, 문화적 맥락을 이해하도록 한다. 최근 영미 외의 영어권에서 축적된 소설적 성과 또한 점검한다.");
//
//            m3MajorName.add("고급영문법");
//            m3MajorIntro.add("영문법 구조에 관한 지식을 실제 언어가 사용되는 다양한 상황이나 맥락 속에서 어떻게 적절히 사용할 수 있을 것인가에 초점을 맞추어 학생들로 하여금 영문법을 제대로 활용하는 방법을 익힐 수 있도록 하는 것을 목표로 한다. 코퍼스 자료, 신문기사, 뉴스, 영화, 시트콤, 광고, 스포츠 중계 등 다양한 자료를 활용하여, 구어/문어 담화나 장르 등에 따라 영문법 구조가 어떤 변이형들을 취하는지를 보여줌으로써 영문법에 대한 보다 폭 넓은 이해를 할 수 있도록 돕는다.");
//
//            m3MajorName.add("영어의미의 이해");
//            m3MajorIntro.add("영어표현의 의미와 실제 상황에서의 용법을 살펴보면서 영어 의미론과 화용론의 주요 개념과 연구방법들을 소개한다. 어휘의미, 의미합성, 지시, 의미역할, 상황유형, 정보구조, 전제, 대화함축, 화행 등과 관련된 다양한 영어자료를 분석해 봄으로써 영어 단어와 문장의 의미 해석과 인간의 인지에 대한 깊이 있는 이해에 도달하는 것을 목표로 한다.");
//
//            m3MajorName.add("르네상스 영시");
//            m3MajorIntro.add("16세기 초에서 17세기 중반까지의 영국시 고전들을 선별해서 읽는다. 중세 말기의 작품 또한 포함할 수 있다. 와이엇, 스펜서, 시드니, 셰익스피어, 던, 존슨, 허버트, 마블, 밀턴 등의 시인들을 다룰 수 있다.");
//
//            m3MajorName.add("르네상스 영국 드라마");
//            m3MajorIntro.add("셰익스피어를 제외한 16ㆍ17세기 영국 희곡의 고전들을 선별해서 읽는다. 중세 말기와 왕정복고기의 희곡을 포함할 수도 있다. 개별 작가ㆍ작품과 사회문화적인 맥락에 대한 균형 잡힌 이해를 도모한다.");
//
//            m3MajorName.add("현대영시");
//            m3MajorIntro.add("19세기 후반에 등장한 현대시의 선구자들을 비롯하여 20세기의 주요 영미시인들, 특히 모더니스트 시인들을 중점적으로 읽는다. 20세기 중반의 시인들과 20세기 후반의 다양한 유파의 시인들을 다룰 수도 있다.");
//
//            m3MajorName.add("현대영미드라마");
//            m3MajorIntro.add("19세기 말 이후 오늘에 이르기까지 영국과 미국의 희곡문학을 조망한다. 특히 입센 이후 현대희곡의 주요 경향 및 형식 실험에 주목한다. 시대별로 주요작품을 선별하여 정독함으로써 현대희곡의 큰 흐름을 파악하는 동시에 개별 작품에 대한 깊이 있는 이해를 도모한다.");
//
//            m3MajorName.add("19세기 영국소설");
//            m3MajorIntro.add("이 수업은 제인 오스틴, 에밀리 브론테, 샬럿 브론테, 찰스 디킨즈, 조지 엘리엇, 루이스 캐롤 등 19세기 영국소설의 고전을 선별하여 읽는다. 결혼, 가족, 교육, 이주, 공간, 제국주의 등의 주제를 중심으로 당대 영국사회를 관통한 역사적 변화가 어떻게 문학적으로 재현되는지 공부한다.");
//
//            m3MajorName.add("영어문장구조의 이해");
//            m3MajorIntro.add("영어 문장들이 단어나 구의 결합을 통해 형성되는 원리를 알아보고 다양한 영어 구문의 특성과 용법에 대해 살펴본다. 문장구조의 주요 원칙뿐 아니라 개별 구문에 나타나는 어휘적 특성, 어순, 구조적 특성 등 세부 현상들에 대한 체계적 설명 방법을 모색해보고 의미 해석 및 화용적 기능과는 어떻게 연결되는지 알아봄으로써 영어 문장 사용에 대한 심층적 이해를 돕는다. ");
//
//            m3MajorName.add("영어습득의 이해");
//            m3MajorIntro.add("이 과목은 영어학의 주요 분야 중의 하나인 영어습득에 대한 전반적인 이해를 돕는 과목이다. 영어습득 이론 및 습득 과정에 대한 이해와 습득에 관련된 다양한 현상의 분석을 통해 성인 영어학습자들이 궁극적으로 수준 높은 영어 수준에 도달할 수 있도록 돕는 것을 목적으로 한다.");
//
//            m3MajorName.add("중세영문학");
//            m3MajorIntro.add("앵글로색슨 시대에서 15세기 말까지의 중세영문학과 유럽문학 고전들을 선별해서 읽는다. 중세 텍스트, 장르, 주제, 기법 등이 현대문학과 대중매체에서 번안되고 변용되는 양상 또한 살펴볼 수 있다.");
//
//            m3MajorName.add("17∙18세기 영국시");
//            m3MajorIntro.add("17세기 중반부터 18세기말까지의 영국시를 선별하여 읽는다. 밀턴 이후 영국시 전통이 어떻게 발달하였는지 정치, 사회문화적 맥락과 연결해서 고찰하면서 서사시, 영웅시, 풍자시, 발라드, 풍경시 등의 장르가 이 시기에 어떻게 발달하였는지에 대한 문학사적 이해를 도모한다. 아울러 18세기 영국시 전통이 초기 낭만주의와 어떻게 연결되었는지를 다룬다. 밀턴, 드라이든, 포우프, 스위프트, 게이, 존슨, 그레이 등의 작품을 선별하여 읽을 수 있다.");
//
//            m3MajorName.add("미국시");
//            m3MajorIntro.add("17세기 식민지 시대로부터 현대에 이르기까지의 주요 시인들의 작품을 광범하게 읽고 미국시의 특성과 그 전통을 포괄적으로 이해한다. 동시대의 주요 영국시와의 비교연구, 그리고 미국시와 미국적 현실과의 관련 연구 역시 이 과목의 중요한 한 부분을 이룰 것이다. 브라이언트, 포우, 에머슨, 휘트먼, 디킨슨, 로빈슨, 프로스트, 샌드벅, 크레인, 윌리엄스, 스티븐스, 파운드, 휴즈 등을 주로 다룬다.");
//
//            m3MajorName.add("18세기 영국소설");
//            m3MajorIntro.add("17세기 후반의 산문을 포함하여 디포우부터 오스틴 이전에 이르는 18세기 “소설의 발생” 과정과 발전을 추적한다. 18세기 주요 작가의 작품을 읽음으로써 소설이라는 장르의 특수성과 문화사를 이해한다. 또한 보다 총체적 이해를 위해 작품뿐만 아니라 그 작가의 시대적 배경과 전기적 사실 등 작품 외적인 자료들도 취급할 수 있다.");
//
//            m3MajorName.add("19세기 미국소설");
//            m3MajorIntro.add("19세기 미국소설을 주요 작가의 대표작을 통하여 집중적으로 연구한다. 저명한 비평서와 개별 장편들에 대한 비평을 읽어 주제와 문체, 기교 등을 분석하는데 참고한다. 내용과 형식면에서 미국소설의 전통과 특징을 파악하는 것은 작품 자체를 읽음으로써만 이루어질 수 있으므로 비평서는 이차적인 중요성을 갖게 될 것이다. 미국소설에 대한 심미적인 접근도 시도한다.");
//
//            m3MajorName.add("영어발달사");
//            m3MajorIntro.add("영어의 변천 과정을 인구어에서부터 시작하여 고대영어, 중세영어, 근대영어에 이르는 시기를 대상으로 영어의 내적 역사–음운구조, 철자체계, 굴절체계, 통사구조, 의미–의 관점에서 다룬다. 언어변화는 필연적으로 외적 변화와 연결되어 있으므로 영어에서의 변화를 다루는 과정에서도 해당시기에 일어난 역사적인 사건들과 언어변화의 상관관계를 살핀다. 또한 시기별로 일어난 변화에 대한 이해와 더불어 변화의 원인, 양상, 방향에 대해서도 논의한다.");
//
//            m3MajorName.add("최근 영어권 소설");
//            m3MajorIntro.add("20세기 중반 이후 출판된 영어권 소설을 읽는다. 아체베, 앳우드, 쿳시, 파울즈, 이시구로, 레싱, 나보코프, 루시디, 스미스 등 영미 및 캐나다, 인도, 아프리카 출신 작가들의 작품을 선별하여 읽으며 해당 작품의 역사적, 문화적 맥락을 살펴본다.");
//
//            m3MajorName.add("소설의 이론과 서사 전통");
//            m3MajorIntro.add("소설을 중심으로 서사 전통과 서사형식에 대한 역사적, 비평적 이해를 도모하는 과목이다. 18세기 영국소설 발생기의 서사전통 및 작품과 미국의 로맨스장르와 같은 특정한 전통에 대한 논의를 포함할 수도 있다. 아울러 소설과 인접한 산문 장르의 서사 전통도 함께 다룰 수 있다.");
//
//            m3MajorName.add("비평이론");
//            m3MajorIntro.add("영미 문학이론을 비롯한 현대 비평이론의 다양한 흐름들을 체계적으로 배우는 과목이다. 특정한 비평이론에 집중하기보다 신비평에서부터 시작되는 비평이론의 역사를 개괄하거나 문학작품의 해석과 이해에 필요한 다양한 주제의 비평이론들을 섭렵한다.");
//
//            m3MajorName.add("영문학특강 1");
//            m3MajorIntro.add("영문학의 전통적인 장르 및 시대별 교과과정 내에 포함되지 않는 다양한 주제들을 매학기 강의한다. 매 학기 다른 강의 주제들이 제공될 것이며 학생들은 활발하고 밀도 높은 토론과 정교한 글쓰기 훈련을 통해 자신의 시각으로 새로운 주제를 발굴하고 분석하는 능력을 습득할 것이다.");
//
//            m3MajorName.add("영어학특강");
//            m3MajorIntro.add("<영어학특강>은 영어학과 관련된 특정주제를 선택하여 이를 심도있게 다룬다. 강의 주제는 특정 분야에서 최근 관심을 모으고 있는 이슈 중에서 선정되며, 수강생들은 이 과목을 통해 선정주제에 대한 이론적인 분석과 실증적인 분석을 검토하고 다양한 영어학관련 현상들을 체계적으로 분석하는 방법을 배우게 된다.");
//
//            m3MajorName.add("여성문학의 전통");
//            m3MajorIntro.add("여성작가들의 문학적 성취를 공부함으로써 영문학 전통의 의미를 확장하는 것이 이 수업의 목적이다. 영미권 여성작가의 시, 소설, 드라마, 에세이, 비평 등 장르와 시대를 아울러 다양한 작품을 골고루 읽으면서 여성과 문학이라는 주제를 탐구한다.");
//
//            m3MajorName.add("영문학특강 2");
//            m3MajorIntro.add("영문학에 대한 전통적 접근법으로 쉽게 포괄되지 않는 특정 주제, 쟁점, 방법론을 집중적으로 다룬다. 매 학기 다른 주제가 제공되며, 분석적/창의적 사고와 글쓰기에 중점을 둔다. 영어 전용 과목이다.");
//
//            randomTimeLectureRoom(c1m3Professor, c1Major.get(2), c1LectureRoom, m3MajorName, m3MajorIntro);
//
//            List<String> m3CultureName = new ArrayList<>();
//            List<String> m3CultureIntro = new ArrayList<>();
//
//            m3CultureName.add("현대사회와 국제어");
//            m3CultureIntro.add("‘현대사회와 국제어’는 “현대 사회에서 세계어는 필요한가”, “필요하다면 어떤 과정을 거쳐서 어떻게 선택되는가”, “세계어의 역할은 무엇이며 다양한 분야에서 어떻게 사용되는가” 등의 여러 문제에 대해 고찰해봄으로써, 학생들이 변화하는 국제 사회의 흐름을 이해하고 대처할 수 있도록 하는 것을 목표로 한다. 강의는 주로 지금 현재 세계어로서의 역할을 하는 영어를 매개로 하여 영어가 세계어로 형성된 배경과 역사, 영미 사회 및 문화를 반영하는 영어의 특성, 세계어로서의 영어의 다양한 기능 및 변이형들, 영어와 관련된 언어 정책 등에 대해 소개하고 여러 가지 문제점 등도 함께 다루어 세계어의 다양한 측면에 대한 올바른 이해를 돕게 될 것이다.");
//
//            m3CultureName.add("서양근대문학의 이해");
//            m3CultureIntro.add("서양이 비서양과의 만남을 통해 구성되었으며 그 양상이 서양근대문학에 나타난다는 전제 아래, 강의의 전반부에서는 서양 근대의 성격을 결정한 여러 사건 중 특히 ‘신세계’의 발견이 문학작품에 어떻게 분석되고 논의되며 형상화되는지 살펴보고, 후반부에서는 서양의 제국주의적 팽창과 근대 문학의 접점을 짚어보도록 한다.");
//
//            m3CultureName.add("문학과 정신분석");
//            m3CultureIntro.add("프로이트의 주요 저술들을 전체 또는 발췌로 읽으면서 정신분석이 전제하고 있는 인간관, 사회관, 예술관은 어떤 것인가를 검토하고, 정신분석이 설정하고 추구하는 “진실”이란 어떤 의의를 부여받을 수 있는가 고찰한다. 또한 문학작품의 정신분석학적 해석을 통해 어떤 의미를 읽어내는 것이 가능해지며 그것의 한계는 무엇일까 생각해 봄으로써, 정신분석적 “환원”을 넘어서는 정신분석적 문학비평은 어떤 것일까 모색한다. 이를 선정된 문학작품의 면밀한 분석을 통해 수행하도록 한다.");
//
//            m3CultureName.add("문학으로 읽는 서양문명");
//            m3CultureIntro.add("근대 초기 이전의 서양을 대표하는 문학 작품들을 우리말 또는 영어 번역으로 읽음으로써 서양 문명이 고대에서 중세를 거쳐 르네상스에 이르면서 진화한 과정을 살핀다. 호메로스, 베르길리우스, 오비디우스 등 고전 시대 시인들, 무훈시와 기사 로맨스를 포함하는 다양한 중세 문학 장르, 아리오스토, 세르반테스, 셰익스피어, 밀턴 등 초기 근대 작가들을 다룰 수 있으며, 개별 텍스트와 그 사회문화적 맥락뿐 아니라 텍스트와 텍스트, 문명과 문명 간의 역사적 변천 또한 주목한다.");
//
//            m3CultureName.add("문학과 철학의 대화");
//            m3CultureIntro.add("문학과 철학은 근원적으로 하나의 뿌리를 공유하고 있는 것으로, 그 근원에는 인간과 세계에 대한 이해라는 인문학적 문제가 걸려 있다. 동서양을 막론하고 이 두 인문학 분야는 동일한 문제를 탐구해 왔으며, 이로 인해 이 두 분야를 서로 견주어 조명하는 경우 인문학적 문제에 대한 보다 폭넓고 균형 잡힌 이해가 가능할 것이다. 요컨대, 본 강의의 주제는 문학과 철학의 만남에 초점을 맞추고, 나아가 양자에 대한 교차 조명을 통해 인문학의 근본적 문제들을 탐구하는 데 놓인다.\n" +
//                    "본 교과목에서는 먼저 문학과 철학 사이의 관계를 역사적으로 개관하고, 문학과 철학에서 공통으로 문제되는 ‘해석과 이해의 문제’를 몇 편의 영화를 통해 검토하기로 한다. 이어서 문학과 철학의 다양한 텍스트를 다루되, 철학적 사유를 유발하거나 철학적 상상과 깊은 관련을 맺고 있는 문학 텍스트 및 문학에 대한 다양한 사유를 담고 있는 철학적 텍스트를 꼼꼼하고 깊이 있게 읽어나감으로써, 문학에 대한 철학적 이해와 철학에 대한 문학적 이해의 폭을 넓히기로 한다.");
//
//            m3CultureName.add("영어 대중소설 읽기");
//            m3CultureIntro.add("영어로 쓰인 대중소설을 선별해서 읽음으로써 영어 읽기 능력을 향상시키고 영어권 문화에 대한 이해를 확장한다. 추리소설, 과학소설, 판타지, 아동/청소년 문학 등 다양한 대중문학 장르가 다루어질 수 있다.");
//
//            m3CultureName.add("영시의 이해");
//            m3CultureIntro.add("영시를 공부한 적이 없는 학생들을 위한 수업으로 영시를 어떻게 즐길 수 있는가에 중점을 둔다. 영시를 이해하고 즐길 수 있는 능력은 영어의 특성과 시 형식에 대한 올바른 이해에 기초하는 만큼, 이 강의는 영어의 음절(syllable)에 대한 기초적인 분석에서 시작하여 영시의 운율을 살펴보고 다양한 시 형식(발라드, 소네트, 만가 등)을 체험할 수 있는 기회를 학생들에게 제공한다. 이 수업을 통하여 학생들은 영시를 보다 깊이 음미하고 영시전통을 잘 이해하게 될 것이다.");
//
//            m3CultureName.add("영어로 읽는 세계문학");
//            m3CultureIntro.add("본 과목은 비영미권 (호주, 인도, 캐나다, 남아공 등) 출신으로 영어로 글을 쓰는 작가들과 (영미권의 혹은 영미 지역의) 소수문화 작가들이 쓴 문학작품을 읽음으로써 시대와 지역을 달리하는 작품들이 보여주는 상이한 입장과 관심사를 이해하는 것을 목표로 한다. 어떤 주제나 문학형식에 대한 이해를 돕기 위해 필요하다면 비영어권이라 하더라도 영어로 번역되어 널리 읽히는 작가의 작품도 다룰 수 있다.");
//
//            m3CultureName.add("미국문화와 현대사회의 이해");
//            m3CultureIntro.add("미국문화의 이해를 통한 현대사회의 이해를 목표로 하는 강의이다. 미국에 대한 폭넓고 깊이 있는 이해를 위해 제 2차 세계대전 이후의 다양한 역사, 철학, 대중문화, 문학 텍스트를 고찰하여 미국문화가 현대를 살아가는 우리와 어떤 관계를 맺고 있는지 다각적으로 점검하고자 한다. 이를 위해 현대 미국문화를 구성하며 끊임없이 교차하고 있는 주요한 사회적 흐름–미국의 예외주의, 소비주의, 세계화와 대중문화, 다문화주의, 생태주의–들을 공시적, 통시적으로 살펴볼 것이다.");
//
//            m3CultureName.add("영미문화 읽기");
//            m3CultureIntro.add("영미권의 대표적인 사상가, 문필가, 예술가들의 산문을 영어로 읽어보는 수업이다. 고전에서부터 최근 글에 이르기까지 정선된 명산문을 읽음으로써 영미 문화를 깊이있게 이해하는 것을 목표로 한다. 역사, 개인과 사회, 정의, 사랑, 죽음, 환경, 교육 등 중요한 주제들에 대해 다양한 시대에 걸쳐 어떤 생각들을 해 왔으며 이들이 현재와 어떤 관련성이 있는지를 고찰하게 될 것이다. 다양한 장르와 스타일의 글들을 꼼꼼히 읽어 영어 독해능력을 향상시키는 것도 이 강의의 목표이다.");
//
//            randomTimeCultureRoom(c1m3Professor, c1Major.get(2), c1LectureRoom, m3CultureName, m3CultureIntro);
//
//            // c1m4
//            List<Professor> c1m4Professor = em.createQuery("select p from Professor p where p.major.idx = :midx", Professor.class).setParameter("midx", c1Major.get(3).getIdx()).getResultList();
//
//            List<String> m4CultureName = new ArrayList<>();
//            List<String> m4CultureIntro = new ArrayList<>();
//
//            m4CultureName.add("초급프랑스어 1");
//            m4CultureIntro.add("본 교과목은 프랑스어를 처음 배우는 초심자를 위한 강좌이다. 프랑스어에 대한 사전 지식이 전혀 없는 수강생을 대상으로 하는 강좌인 만큼 프랑스어 알파벳, 철자에 대한 설명에서 시작하여 정확한 발음의 원칙을 숙지하도록 하고, 기본 어휘 및 기초적인 문법을 습득하도록 한다. 각 과의 본문은 주로 일상적인 상황에서 실제 사용될 수 있는 평이한 문장들로 구성함으로써 기초적인 회화를 습득함과 동시에 기본적인 독해력을 함양하도록 한다. 더불어 본문에서 학습한 기본 어휘, 문법, 표현들을 응용한 간단한 작문을 연습함으로써 초급 수준에서 말하기, 읽기, 쓰기의 능력을 단계적으로 고루 함양하는 것을 목적으로 하는 강좌이다. 아울러 프랑스의 사회와 문화, 혹은 프랑스인들의 정신성을 엿볼 수 있는 방식으로 본문 텍스트를 구성함으로써 프랑스어를 공부하면서 자연스럽게 프랑스의 생활과 문화에 접근할 수 있도록 한다.");
//
//            m4CultureName.add("초급프랑스어 2");
//            m4CultureIntro.add("본 교과목은 기초적인 프랑스어 지식을 지니고 있는 학생들을 대상으로 한 강좌이다. <초급 프랑스어 1>을 수강한 학생이나, 고등학교 및 기타 과정에서 프랑스어의 기초 지식을 습득한 학생들을 대상으로 하는 강좌이다. 본 강좌는 <초급 프랑스어 1>의 연속선상에서 초급 수준의 프랑스어 문법을 완성하고, 이를 활용한 독해 및 작문 능력을 함양하는 동시에 실제 말하기에 활용할 수 있는 다양한 표현들을 습득하도록 한다. <초급 프랑스어 1>의 본문이 일상적인 상황에 대화를 위주로 구성된 데 반해 <초급 프랑스어 2>의 본문은 프랑스어로 쓰인 각 분야 전공 문헌의 독해를 위한 기초적인 토대를 함양할 수 있도록 산문 텍스트를 일정 부분 활용하도록 한다. 이로써 실생활에 활용할 수 있는 생활 프랑스어 능력과 더불어 전공 문헌의 해독에 필요한 프랑스어의 토대를 마련하는 것이 본 강좌의 목표이다.");
//
//            m4CultureName.add("중급프랑스어 1");
//            m4CultureIntro.add("본 교과목은 초급 수준의 프랑스어 지식을 갖춘 학생들을 대상으로 하는 강좌이다. <초급 프랑스어 1, 2> 강좌를 이수한 학생, 고등학교 및 기타 과정에서 프랑스어 중급 과정을 이수한 학생들을 대상으로 하는 강좌이다. 본 강좌에서는 초급 강좌에서 습득한 기초 프랑스어 지식을 바탕으로 문장 구조 중심의 문법 지식, 문장 내에서 단어들의 상호 결합 관계나 쓰임새를 중심으로 한 문법 학습 등을 통해 보다 심화된 차원의 문법 지식을 습득하도록 한다. 이 같은 문법 지식을 기본 구문 형태로 제시하고 이를 활용한 다양한 용례를 제시하여 프랑스어로 의사소통을 해야 할 상황에서 즉각적으로 활용할 수 있게 유도함으로써 문법, 작문, 말하기 능력을 동시에 함양하도록 한다. 또한 본 강좌에서 습득한 중급 수준의 문법을 토대로 프랑스 문학 텍스트를 비롯한 다양한 분야의 텍스트에 대한 독해를 병행함으로써 프랑스어 능력을 증진시키도록 한다.");
//
//            m4CultureName.add("중급프랑스어 2");
//            m4CultureIntro.add("본 교과목은 <초급프랑스어 1, 2> 교과목과 <중급프랑스어 1> 강좌를 이수한 학생 및 프랑스어에 대한 체계적 지식을 습득한 학생들을 대상으로 하는 강좌이다. 본 강좌는 <중급 프랑스어 1> 강좌에서 습득한 중급 수준의 문법 지식을 바탕으로 이를 심화하여 프랑스 문학작품과 논설문 등을 독해하기 위한 고급 표현과 심화 문법 그리고 프랑스어의 다양하고 풍부한 표현법 등을 익힘으로써 중급 수준의 문법을 완성한다. 이를 통해 전문적인 분야의 프랑스어 텍스트의 이해를 도모하고 학습자의 견해를 프랑스어로 논리적이고 명료하며 정확하게 표현하는 능력을 기르도록 한다. 이를 위해 본 강좌에서는 다양한 장르의 프랑스 문학 텍스트를 비롯하여, 전문적인 논설문, 프랑스학 관련 자료 등 프랑스어 원전에서 엄선한 텍스트를 교육 자료로 활용함으로써 프랑스어에 대한높은 수준의 지식을 함양하도록 한다.");
//
//            m4CultureName.add("프랑스어 글쓰기");
//            m4CultureIntro.add("기본적인 불어작문을 습득한 학생들을 대상으로 하여, 쉬운 불어문장을 직접 작문하는 능력을 기르는 것을 목표로 한다. 이를 위하여 불어문장의 구조와 어휘의 통사적 역할 등에 대한 체계적인 교육을 함과 동시에 실제 불어 문장을 작문하고 이를 고쳐 주는 방식을 병행할 것이다.");
//
//            m4CultureName.add("프랑스어 말하기");
//            m4CultureIntro.add("본 과목은 보다 실용적인 강좌의 필요성이 강조되는 사회환경과 변화하는 학생들의 욕구에 보조를 맞추어, 문법, 독해가 아닌 회화위주로 진행되는 수업이다. 불어불문학과 전용 어학 실습실에서 진행되는 위 과목은 비디오 테이프, 카세트 테이프, CD ROM 타이틀 등 다양한 매체를 통한 프랑스어회화의 습득은 물론 언어를 통한 프랑스 사회 문화의 이해도 아울러 목표로 삼고 있다.");
//
//            m4CultureName.add("시사 프랑스어");
//            m4CultureIntro.add("<Le Monde>, <Le Point>의 사설, 한국 관계 기사 등을 주요 텍스트로 하여 세계에 대한 시사적인 관심과 현재에 대한 비판적 안목을 기르는데 주안점을 둔다. 강의는 주요 기사를 문법적 설명을 곁들여 강독하면서, 토론의 방식으로 진행한다.");
//
//            m4CultureName.add("문학 속 인간과 기계");
//            m4CultureIntro.add("본 교과목의 목적은 근대사회의 전개와 함께 본격적으로 사유되기 시작한 인간과 기계의 관계를 문학이 어떻게 포착하고 전개했는지 검토하는 것이다. 근대사회에서 기계론적이고 유물론적인 세계관의 확장은 인간에 대한 이해를 급격하게 변화시켰으며, 이에 따라 인간의 본질과 결부된 주변적 대상인 동물, 기계, 여성 등 여러 소수자적 존재를 새롭게 고찰할 필요성이 제기되었다. 그중에서도 인간과 기계의 관계는 현저하고 중요한 대상이며, 인공지능 등 현대사회의 기술적 심화와 함께 계속해서 그 의미가 탐색되고 있다. 본 수업에서는 인간과 기계의 관계를 근대문학이 내놓은 몇몇 중요한 텍스트를 통해 고찰한다. 따라서 본질적으로 여러 학문과 관점을 포괄하는 주제를 파악하고 종합하는 작업에서 문학 텍스트의 역할을 확인하는 것과, 문학 텍스트를 엄밀하게 분석하고 해석하는 방법들을 학습하는 것이 본 수업의 구체적인 활동이 될 것이다.");
//
//            m4CultureName.add("프랑스명작의 이해");
//            m4CultureIntro.add("프랑스 문학, 문화에 관심이 있는 학생들을 대상으로 하여, 프랑스 문학사상의 중요한 작가들, 혹은 우리에게 널리 알려진 작가들의 작품을 중심으로 프랑스 문학 및 문화에 대한 이해를 도모한다. 강의는 작품 해설, 독서 과제 부여, 토론 등의 형식으로 진행한다.");
//
//            m4CultureName.add("상상력과 문화");
//            m4CultureIntro.add("이 강의는 이미지와 상상력이 생성되고 발휘되는 상상계의 특성을 소개하는 것을 일차적인 목표로 한다. 따라서 이 강의는 상상력이 실질적으로 구현되고 있는 문화 현상의 각종 예들을 공시태와 통시태의 축을 따라 동서양의 지리적 역사적 맥락 속에서 소개함과 동시에, 분과 학문의 경계를 넘어 인간학 대상 전반에 걸쳐 행해지는 분석을 통해 상상력의 기능과 작용 원리에 대한 이해 의 틀을 제공함으로써 그 연구방법론을 도출하고, 새로운 인식론을 모색하는 것을 궁극적인 목표로 한다.");
//
//            m4CultureName.add("프랑스어권 문화의 이해");
//            m4CultureIntro.add("프랑스 및 프랑스어권 제 국가의 사회, 문화적 문제점을 다룬 저작들을 텍스트로 하여 프랑스어권에 대한 이해를 도모한다. 주제와 관련된 한국어 텍스트의 독서와 함께 진행되며 과제로 제시한다. 사회, 문화적 관심과 함께 프랑스어 학습도 겸하는 부수적 효과도 학생들은 기대할 수 있을 것이다.");
//
//            m4CultureName.add("문학과 공연예술");
//            m4CultureIntro.add("본 강의에서는 문학 작품과, 그것을 토대로 창작된 다양한 공연예술 작품에 대한 비판적 분석 및 이해를 도모하고자 한다. 문학 텍스트에 대한 분석을 토대로 오페라, 발레극, 뮤지컬 등 각 공연예술 장르의 특성에 따라 어떤 변용과 창작이 행해지는지를 살펴봄으로써 문학과 공연예술의 관계에 대한 총체적 이해, 문학 작품을 토대로 한 다양한 공연예술 창작의 가능성까지를 탐구해보고자 한다.");
//
//            randomTimeCultureRoom(c1m4Professor, c1Major.get(3), c1LectureRoom, m4CultureName, m4CultureIntro);
//
//            List<String> m4MajorName = new ArrayList<>();
//            List<String> m4MajorIntro = new ArrayList<>();
//
//            m4MajorName.add("(탐)세계속의 프랑스어");
//            m4MajorIntro.add("현대 유럽 문화의 한 중심에 프랑스가 위치하고 있다고 볼 때, 프랑스어가 유럽과 전 세계에 미친 정신적, 문화적 영향력은 엄청나다고 할 수 있다. 이 강좌는 유럽을 넘어 세계 속에서 프랑스어가 갖는 영향력을 유럽문화의 역사적 흐름과 확산이라는 관점에서 살펴보고 전 세계에서 프랑스어의 사용도와 인도 유럽어 내에서의 프랑스어의 계통을 중심으로 유럽 인근언어와 한국어 사이의 형태적, 구조적 차이를 조명한다.");
//
//            m4MajorName.add("(탐)프랑스문학과 예술의 흐름");
//            m4MajorIntro.add("프랑스의 지식인들이 상아탑에 안주하기 이전에 많은 문학가들은 작가인 동시에 음악이나 미술, 건축과 같은 분야의 비평가였고 심지어는 철학가이기도 했다. 이처럼 프랑스 문학은 예술의 전반적인 흐름과 분리해서 이해할 수 없는데, 우리는 중세부터 현대에 이르는 프랑스 문학 작품과 예술 작품을 당대의 사상사적 맥락에서 체계적으로 분석하고 그것을 우리 시대의 관점에서 재구성함으로써 프랑스 문학과 예술의 전반적인 흐름을 이해하고자 한다.");
//
//            m4MajorName.add("시청각프랑스어 연습");
//            m4MajorIntro.add("<초급·중급 프랑스어 1·2> 강좌에서 익힌 기본적인 프랑스어구사능력을 바탕으로 학술적 언어뿐 아니라 대학의 학문연구에 필요한 정도의 사회적, 철학적, 경제적인 언어도 단계적으로 이해할 수 있도록 하는 것을 목적으로 한다. 프랑스 사회 전반에 걸친 시사적인 텍스트를 선정하여 회화의 수준을 높여 나아가 학생들로 하여금 프랑스의 문물을 폭넓게 접할 수 있게 한다.");
//
//            m4MajorName.add("프랑스어권 문학강독");
//            m4MajorIntro.add("프랑스어는 단지 프랑스의 문학, 문화, 사상을 이해하기 위한 통로만이 아니다. 오늘날 벨기에 등 유럽 지역, 퀘벡을 비롯한 북미 지역, 카리브해 지역, 그리고 특히 아프리카에는 프랑스 국민보다도 더 많은 수의 사람들이 프랑스어로 말하고, 프랑스어로 쓰인 글을 읽고 있다. 이처럼 프랑스어는 세계의 거의 모든 지역의 다양한 문화에 접근할 수 있는 가능성을 제공한다. <프랑스어권 문학강독>은 두 개의 상이한 목표를 갖는다. 첫째 목표는 상대적으로 평이한 프랑스어로 쓰인 텍스트들의 꼼꼼한 독서를 통해 프랑스어 텍스트의 독해 능력을 기르는 것이다. 두 번째 목표는 프랑스어권 문학이라는 제목과 관계가 깊다. 20세기 이후 유럽 및 프랑스의 식민지라는 아픈 과거 경험을 아프리카 및 카리브해 지역의 작가들이 문학을 통해 어떻게 극복했으며, 또한 해방 후 포스트식민주의 상황에서 그들이 어떻게 문화적, 민족적 정체성을 찾아가는지 따라가 볼 수 있을 것이다. 이처럼 각 지역의 역사적 상황과 밀접하게 연결되어 있는 문학 텍스트를 읽으면서 우리는 문학과 사회, 문학과 문화정체성, 억압 상황과 문학 등의 관계 등에 대해 개관하며, 포스트식민주의의 기초 개념과도 만날 수 있을 것이다.");
//
//            m4MajorName.add("프랑스연극");
//            m4MajorIntro.add("17세기 고전주의 시대뿐만 아니라 그 이후 현대에 이르기까지의 프랑스문학사상 탁월한 희곡 작가의 작품들을 본 강좌에서 강독한다. 그리하여 대화체의 프랑스어 문장에 대한 독해력을 기르고, 프랑스단편 강독과 프랑스산문 강독과 더불어서 독해력의 완성을 기함과 동시에 희곡 장르에 대한 기본적인 이해를 가능하게 한다.");
//
//            m4MajorName.add("(필)프랑스어문법과 작문");
//            m4MajorIntro.add("프랑스어를 전공하는 학생들에게 요구되는 본 과목에서 학생들은 프랑스어문장을 작문하고 분석하는 방법을 습득하게 될 것이며, 인문학, 사회학, 자연과학 제 분야의 여러 텍스트를 읽게 될 것이다. 본 과목은 학생들로 하여금 프랑스어로 효율적인 의사소통을 가능하게 하는 것을 목표로 삼는다.");
//
//            m4MajorName.add("프랑스어학개론 1");
//            m4MajorIntro.add("중세 이후부터 현대까지의 프랑스어의 음운적, 형태적, 통사적, 어휘적 발달과정을 통시적으로 연구하고, 음성, 문자의 기호체계, 의미, 문법의 가치체계의 특징 및 가치체계와 기호체계의 관계를 공시적으로 연구하여, 프랑스어의 정체성과 독자성을 이해함을 목적으로 한다. 또한, 프랑스어의 전반적인 구조와 특징을 공시적 관점에서 고찰하고 특히 발음과 철자법의 상호 보완관계, 의미와 문법적인 메커니즘의 연관성 등과 이러한 각 요소와 언어의 심리적 구조의 상호경향을 주요 문제로 연구한다.");
//
//            m4MajorName.add("(필)프랑스어학개론 2");
//            m4MajorIntro.add("<프랑스어학개론 1>과 연결된 강좌로서 프랑스어의 전반적인 구조와 특징을 공시적 관점에서 고찰하고 특히 발음과 철자법의 상호보완관계, 의미와 문법적인 메커니즘의 연관성 및 이러한 각 요소와 언어의 심리적 구조의 상호 경향을 주요 문제로 연구한다.");
//
//            m4MajorName.add("18세기 프랑스문학");
//            m4MajorIntro.add("18세기 프랑스문학, 특히 계몽주의에 대한 깊이 있는 이해를 목표로 하는 본 과목은 이 시대의 전반적인 특징인 계몽주의 사상의 형성과정과 구체적인 면모를 몽테스키외, 볼테르, 디드로, 루소를 통하여 살피고 그들의 역할과 후세에 미친 영향을 고찰한다.");
//
//            m4MajorName.add("(필)프랑스문학개론 1");
//            m4MajorIntro.add("<프랑스문학개론 1>은 중세에서 현대까지의 프랑스 문학을 대상으로 삼는다. 한편으로는 여러 문예사조를 중심으로 문학사적 접근의 중요성과 의의를 배우며, 다른 한편으로는 대표적인 텍스트들을 읽고 해석하면서 텍스트 설명이라는 정치한 해석 방식의 기초를 배운다. 이를 통해 학생들은 문학사의 흐름, 문학과 사회의 관계, 개별 텍스트에 대한 이해를 심화할 수 있을 것이다. 특히 <프랑스문학개론 1>에서는 프랑스어가 문학의 언어로 사용되기 시작한 중세 텍스트들에서 출발하여, 르네상스 및 16세기의 작품들, 바로크 문학, 고전극으로 대표되는 17세기의 고전주의 문학, 그리고 18세기 계몽주의 문학 텍스트들을 다룰 것이다. 몽테뉴, 라블레, 라신, 코르네유, 볼테르, 루소처럼 프랑스 문화와 사상의 꽃을 피운 대가들의 작품을 통하여 우리는 전통 프랑스 문화의 핵심을 맛볼 수 있을 것이다.");
//
//            m4MajorName.add("19세기 프랑스소설");
//            m4MajorIntro.add("<프랑스문학개론 2> 강좌를 통하여 개괄적으로 파악한 것을 바탕으로 하여, 19세기의 사실주의와 자연주의를 보다 깊이 있게 고찰하는 것을 목표로 한다. ‘적과 흑’의 작가, 스탕달, 메리메, ‘인간희극’의 발자크 등 19세기 들어 꽃피기 시작한 소설이라는 장르가 갖는 문학사적 의의를 고찰하고 플로베르에게 이르러 사실주의가 어떠한 결실을 맺게 되는가를 살피고, 뒤이어 공꾸르 형제 및 졸라의 자연주의로 이행되는 과정을 그들의 작품을 통하여 고찰한다.");
//
//            m4MajorName.add("20세기 프랑스소설");
//            m4MajorIntro.add("본 과목은 1차대전 직전부터 전개된 다양한 소설적 시도들을 중심으로 현대 프랑스문학의 여러 면모를 이해하는 것을 목표로 하고 있다. 아나똘 프랑스, 부르제, 바레스 및 로멩 롤랑, 끌로델 등의 작품에 대한 이해에서 출발하여, 프루스트와 지드의 새로운 소설과 말로, 생-텍쥐페리의 행동문학, 모리악, 베르나노스, 그린의 기독교 문학, 까뮈, 사르트르의 실존주의 소설 및 누보 로망을 고찰한다.");
//
//            m4MajorName.add("프랑스어 문법과 텍스트");
//            m4MajorIntro.add("이미 여러 학기에 걸쳐 불어불문학과의 전공과목을 수학한 학생들에게 프랑스어 문장의 다양한 실제 상황을 경험하게 하여, 단순히 외국어 문장의 해독 차원을 넘어 학생들로 하여금 프랑스어의 원래 뜻에 가장 적합한 우리말 표현 능력을 습득할 수 있는 기회를 제공함은 물론, 실제 번역의 예를 통해 프랑스어 표현과 한국어 표현 사이의 차이가 단순히 언어적 측면에서의 이질성이 아니라 문화적 배경의 차이임을 인식케 할 수 있을 것이다.");
//
//            m4MajorName.add("현대프랑스문화분석");
//            m4MajorIntro.add("본 과목은 프랑스 문화 전반에 대한 기본적인 지식을 이미 획득한 학생들을 대상으로 하여 현대 프랑스 사회의 주요 이슈에 대한 이해도를 높이는 것을 목표로 한다. 해당 주제와 관련된 현대 프랑스 문화이론 소개와 더불어 학생들로 하여금 다양한 경향의 신문, 방송, 문학작품 등의 매체를 활용하여 다각도에서 해당 주제를 조명, 분석하도록 함으로써 프랑스 문화에 대해 분석적이며 균형 잡힌 시각을 갖도록 유도할 것이다.");
//
//            m4MajorName.add("고급프랑스어회화");
//            m4MajorIntro.add("본 과목은 대학의 정규 과정에서 제공할 수 있는 최고 수준의 회화 수업으로서 이미 상당한 회화 능력을 갖추고 있는 학생을 대상으로 한다. 반복적인 학습을 통하여 외국인과의 일상적인 대화를 가능하게 하고, 일정한 지적 능력을 요하는 깊이 있는 토론에 참여하여 자신의 견해를 충분히 피력할 수 있도록 훈련한다.");
//
//            m4MajorName.add("프랑스비평");
//            m4MajorIntro.add("본 강의의 목표는 ‘비평의 세기’인 20세기의 다양한 비평 이론들을 습득함으로써 20세기 프랑스 지성사의 중요한 흐름들을 그 역사적인 배경과의 관계 속에서 이해하는 한편, 타당한 비평적 관점을 가지고 문학 작품을 해석하는 방법을 배우는 데 있다. 아울러 수준 높은 비평 글들을 강독함으로써 프랑스어 독해 실력을 더욱 함양할 수 있다. 따라서 본 강의는 20세기 프랑스 문화, 사상에 대한 이해, 문학 작품에 대한 심화된 분석력, 고급 프랑스어 강독 능력 도모라는 세 가지 목적을 가지고 있어 대학에서 제공하는 가장 수준 높은 프랑스 문학 강의 중의 하나로서 정착할 것이다.");
//
//            m4MajorName.add("프랑스문화와 예술");
//            m4MajorIntro.add("프랑스어는 물론 프랑스 문화와 프랑스 문학 일반에 대한 전공과목을 이수한 학생들을 대상으로 하는 이 강의는, 미술이나 영화 등의 예술을 프랑스 문학, 문화와 연계된 시각에서 다루는 것을 목표로 한다. 디드로, 보들레르 등 대표적인 프랑스 작가들의 미술평론을 강독하면서 미술 작품에 대한 깊이 있는 이해를 도모하며, 문학 작품을 원작으로 하는 영상 예술 작품들의 분석을 통해 문학과 영화의 관계에 대한 폭넓은 이해 및 반성적인 사고를 도모할 수 있을 것이다. 문학과 다른 예술 분야를 동시에 다루는 이 과목을 통해 프랑스 문화에 대한 종합적이고 폭넓은 이해가 가능해질 것으로 기대된다.");
//
//            m4MajorName.add("현대프랑스언어학");
//            m4MajorIntro.add("이 과목은 언어학 일반에 관심이 있고 프랑스어학을 전공하고자 하는 학생들을 대상으로 개별언어로서의 프랑스어를 더욱 심도 깊게 연구하기 위해, 일반 언어학에 대한 기본 이론과 음성학, 음운론, 형태론, 통사론, 의미론, 화용론 등 언어학의 제분야를 소개하고, 구체적으로 각 언어별 특징을 비교하고 이를 프랑스어학에 적용시켜 보는 것을 목표로 한다.");
//
//            m4MajorName.add("프랑스언어학특강");
//            m4MajorIntro.add("<현대프랑스언어학>에서는 주로 언어 일반에 관한 이론의 소개에 중점을 두었다면, <프랑스언어학특강>에서는 이러한 기본 지식을 바탕으로 구체적으로 프랑스어의 역사와 각 연구 영역별 특징들, 또한 생성 문법 등 최근 이론들을 소개하고 학습하는 것을 목표로 한다.");
//
//            m4MajorName.add("현대 프랑스 문화현상과 이론");
//            m4MajorIntro.add("본 강의는 현대 프랑스 사회의 주요 문화현상 및 문화이론에 대한 심도 있는 이해를 목표로 하는 강의로서 프랑스 사회와 문화 전반에 대해 이미 기초적인 지식을 갖고 있는 학생들을 대상으로 한다. 인문학의 다양한 영역에 걸쳐 전개되고 있는 프랑스 문화연구의 최근 동향을 접하게 함으로써 프랑스를 위시한 유럽의 문화현상, 그리고 한발 더 나아가 한국의 문화현상을 바라보는 학생들의 시각을 보다 예리하게 만드는 것이 본 강의가 기대하는 바이다. 최근의 다양한 문화현상과 이론을 다루는 강의성격 상 그 구체적인 내용면에서 다소 변화가 있을 수는 있으나, 본 강의에서 지속적으로 주안점을 두고자 하는 네 가지 주제는 각각 1) 문화적 실천의 사회적 위계 문제, 2) 문화적 생산물의 수용방식, 3) 문화적 생산에 대한 사회제도의 영향 4) 예술가와 사회다. 본 강의에서는 문학, 영화 및 일상생활문화에서 나타나는 문화현상들을 분석대상으로 삼되 이와 관련하여 피에르 부르디외, 베아트리스 르 비타, 스테판 보, 미셀 세르토, 필립 푸아리에 등이 제시하고 있는 문화이론들을 집중적으로 살펴볼 것이다.");
//
//            m4MajorName.add("프랑스어권 문화 특강");
//            m4MajorIntro.add("본 강좌는 현장경험이 풍부한 프랑스어권 전문가들의 다양한 경험과 관점을 이론적 지식과 접목시킴으로써 수강생들이 프랑스어권 국가의 사회와 문화에 대해 보다 총체적인 이해를 할 수 있도록 하는 것을 목표로 한다. 구체적으로는 프랑스어권 국가를 프랑스, 캐나다 퀘벡, 프랑스어권 아프리카의 세 개 지역으로 나누고 각 지역을 역사, 정치, 경제, 사회, 문화 영역으로 다시 세분화하여 접근하고자 한다. 강의는 주제별로 담당교수 강의 1회와 전문가 특강 1회로 구성될 것이다.");
//
//            m4MajorName.add("프랑스문학특강");
//            m4MajorIntro.add("이 과목은 학부 졸업논문 지도를 위한 수업이다. 3, 4년 동안 다양한 전공 수업을 통해 프랑스문학과 문화를 배운 학생들이 형식적인 측면에서 참고문헌 이용 방법, 인용법 등의 논문작성법을 배우고, 각기 원하는 작가, 작품, 주제를 선택한 다음, 토론과 첨삭 지도를 통해 한 학기 동안 졸업논문을 완성하도록 하는 것이 본 강의의 목표이다. 이는 프랑스 문학과 문화를 전공한 사람으로서 갖추어야 할 텍스트의 분석력과 문학사적, 문화사적 맥락에 대한 인식, 이론적 독해력을 기반으로 하여 학생들이 구체적인 작품, 주제를 선택한 후 논리적인 글쓰기로 완성하게 함으로써 프랑스 문학과 문화에 대한 4년간의 교육을 갈무리하기 위한 과정이라 할 수 있겠다.");
//
//            m4MajorName.add("프랑스문학강독");
//            m4MajorIntro.add("이 교과목은 프랑스문학을 전공하는 2학년 학생들을 대상으로 다양한 프랑스문학 작품들에서 발췌한 중요한 대목을 함께 읽으면서, 학생들이 세기별로 진행될 프랑스문학 전공 수업을 듣기에 앞서 프랑스어로 된 문학텍스트를 보다 폭넓게 접할 수 있도록 하는 것을 목적으로 한다. 개별 텍스트가 담고 있는 사회문화적 맥락 및 문학적 감수성과 형식, 주제를 파악하는 방법을 배우는 동시에, 향후 프랑스어 텍스트를 중심을 이루어질 전공심화수업이 요구하는 프랑스어 독해 능력 및 문학텍스트의 실질적인 분석 능력을 함양하도록 하는 데에서 본 수업이 지닌 의의를 찾을 수 있다. 프랑스문학 전공자들이 듣게 되는 첫 번째 강독 수업인 만큼 난이도가 그리 높지 않은 문학텍스트를 선별해 수업을 진행하고자 한다.");
//
//            m4MajorName.add("프랑스시");
//            m4MajorIntro.add("본 과목의 목적은 19세기 프랑스시의 2대 주류인 낭만주의와 상징주의부터 20세기의 초현실주의를 거쳐 그 이후에 현대시까지 프랑스 시의 주요한 흐름을 살펴보고 그 배경이 되는 정신세계를 이해하는 데 있다. 19세기 시와 현대시의 비교를 통해 근현대 프랑스 시의 특징과 변화를 포괄적으로 바라볼 수 있게 될 것이다. 위고, 보들레르, 랭보, 브르똥, 아뽈리네르 등 프랑스 주요 시인들의 작품을 원문으로 읽는다.");
//
//            m4MajorName.add("중세프랑스문학");
//            m4MajorIntro.add("11세기부터 15세기 말까지의 중세불문학 작품을 선별하여 다룬다. 무훈시와 트루바두르의 시가, 소설, 연극 등 다양한 장르의 작품을 통해 중세불문학의 문학사적 의의 및 특성, 변화양상을 포괄적으로 이해하는 것을 목표로 한다. 텍스트는 중세프랑스어와 현대프랑스어 대역본을 사용한다.");
//
//            randomTimeLectureRoom(c1m4Professor, c1Major.get(3), c1LectureRoom, m4MajorName, m4MajorIntro);
//
//            // c1m5
//            List<Professor> c1m5Professor = em.createQuery("select p from Professor p where p.major.idx = :midx", Professor.class).setParameter("midx", c1Major.get(4).getIdx()).getResultList();
//
//            List<String> m5CultureName = new ArrayList<>();
//            List<String> m5CultureIntro = new ArrayList<>();
//
//            m5CultureName.add("이태리어 1");
//            m5CultureIntro.add("이태리어 1은 인구어에 속하는 언어로서, 문화, 예술 분야와도 관련이 깊은 언어이다. 이태리어 학습을 통해서 언어로서의 지식뿐만 아니라 서양문화를 이해하는데 필요한 지적 교양을 쌓는다.");
//
//            m5CultureName.add("이태리어 2");
//            m5CultureIntro.add("이 교과목은 ‘이태리어1 (II)’ (032.057)의 교과목 내용을 기초로 이론의 확대적용 및 발전을 좀 더 깊이 있게 모색한다.");
//
//            m5CultureName.add("스와힐리어 1");
//            m5CultureIntro.add("스와힐리어를 처음 접하는 학생들을 위해 스와힐리어의 문자와 발음 및 구문구조를 이해할 수 있는 기초적인 문법을 배우고 기본적인 회화 수업을 병행한다. 이를 통하여 스와힐리어의 기초가 되는 듣기, 읽기, 말하기가 가능하도록 한다.");
//
//            m5CultureName.add("스와힐리어 2");
//            m5CultureIntro.add("초급 스와힐리어를 이수한 학생들 혹은 그에 상응하는 기초 실력을 갖춘 학생들을 대상으로 하는 수업이다. 이 단계에서는 기초적인 독해와 작문 능력 향상에 초점을 맞추며, 듣기와 말하기 실습수업을 강화하여 학생들로 하여금 읽기, 쓰기, 듣기, 말하기의 종합적인 언어 능력을 배양하도록 한다.");
//
//            m5CultureName.add("몽골어 1");
//            m5CultureIntro.add("본 교과목은 몽골어의 읽기, 쓰기, 독해의 기초를 훈련하며, 이와 함께 몽골의 세계 문화사적 위치에 대한 기초적 시각을 제공한다. 따라서 단문 중심의 몽골어 텍스트 독해를 목표로 하며, 이와 함께 몽골어의 어휘, 텍스트에 반영된 몽골의 역사와 문화를 이해함으로써 몽골 민족의 언어와 문화의 관계를 탐구한다. 특히 역사적으로 몽골과 교류하였던 민족들의 언어가 몽골어에 어떤 영향을 주었는지를 탐구한다.");
//
//            m5CultureName.add("언어의 세계");
//            m5CultureIntro.add("언어는 어떻게 생겨났을까? 인간은 언어를 어떻게 습득하는가? 문자는 어떻게 발달했을까? 한글은 왜 위대한가? 말소리는 어떻게 발음되는가? 이 세상에는 몇 개의 언어가 있는가? 등등 사람들이 가지고 있는 언어에 관한 의문점들을 체계적으로 학습한다.");
//
//            m5CultureName.add("언어의 이해");
//            m5CultureIntro.add("인간과 불가분의 관계가 있는 언어의 본질과 기능 그리고 그 변화 등을 연구하는 학문인 언어학에 관한 개론을 강의하게 되는데, 우선 언어학의 연구대상과 연구방법을 소개하고, 구체적으로 언어의 본질인 음성, 문법, 의미를 음성학, 음운론, 문법론 등으로 나누어 강의하면서 아울러 언어의 분석 그리고 언어의 변천을 연구하는 역사언어학의 입문도 강의하게 된다.");
//
//            m5CultureName.add("알타이민족의 언어");
//            m5CultureIntro.add("알타이계 민족에 대한 전반적인 조망을 한 후 알타이계 언어의 3대파인 튀르크어파, 몽골어파, 만주퉁구스어파로 나누어서 각각에 대해서 이들 민족의 언어와 문자 사용 상황 그리고 언어 문화의 보존 유지 상황 등에 대해서 강의를 한다. 필요에 따라서 현지 조사에서 습득한 여러 자료와 영상 자료를 사용하여 좀 더 현실감 있게 이들 민족의 언어와 문화에 대해서 소개를 할 것이다. 아울러 이들의 언어와 한국어와의 관계에 대해서도 소개를 한다.");
//
//            m5CultureName.add("핀란드어 1");
//            m5CultureIntro.add("본 강좌는 핀란드어 초급과정으로 핀란드어의 발음, 기초 어휘, 기초 문법은 물론 다양한 문장 유형과 동사구문들을 학습하는 것을 목표로 한다. 또한 실제 생활에 필요한 기초적인 수준의 회화 학습을 병행한다.");
//
//            m5CultureName.add("히브리어 1");
//            m5CultureIntro.add("본 교과목은 히브리어의 읽기, 쓰기, 독해의 기초를 훈련하며, 이와 함께 중동지역의 세계 문화사적 위치에 대한 기초적 시각을 제공한다. 따라서 단문 중심의 히브리어 텍스트 독해를 목표로 하며, 이와 함께 히브리어의 어휘, 텍스트에 반영된 유대인의 역사와 문화를 이해함으로써 유대민족의 언어와 문화의 관계를 탐구한다. 특히 현대의 히브리어가 고전 히브리어로부터 어떤 영향을 받았는지를 탐구한다.");
//
//            m5CultureName.add("핀란드어 2");
//            m5CultureIntro.add("본 강좌는 핀란드어 중급과정으로 핀란드어의 고급 어휘와 문법은 물론 다양한 분야의 텍스트 분석과 이해를 목표로 한다. 또한 핀란드 사람들과 말과 글로 의사소통이 가능하도록 회화와 쓰기 학습을 병행한다.");
//
//            m5CultureName.add("이중언어사용");
//            m5CultureIntro.add("본 강좌는 이중언어사용 현상의 언어학, 심리언어학, 신경언어학, 사회언어학적, 교육적 측면을 소개하는 것을 목표로 한다. ‘이중언어사용’의 정의와 측정, 이중언어 아동의 발달, 이중언어사용자의 언어 접촉 현상, 이중언어사용의 심리언어학적·신경언어학적 기초 지식, 이중/다중언어 사회의 사회언어학적 양상, 이중언어사용자의 교육과 언어 정책의 쟁점 등을 다룬다.");
//
//            randomTimeCultureRoom(c1m5Professor, c1Major.get(4), c1LectureRoom, m5CultureName, m5CultureIntro);
//
//            List<String> m5MajorName = new ArrayList<>();
//            List<String> m5MajorIntro = new ArrayList<>();
//
//            m5MajorName.add("(탐)언어와 언어학");
//            m5MajorIntro.add("인문학의 관점에서 인간 언어의 구조와 언어능력을 이해하도록 하며, 이에 관한 기존의 이론적 연구와 실제적 적용을 소개한다. 언어표현의 형식과 의미의 관계에 대하여 다양한 시각에서 접근할 뿐만 아니라, 특히 현대 언어학이 철학, 문학, 심리학, 전산과학, 신경과학, 사회학, 수학 등과 어떠한 관계 속에서 연구되고 있는지를 살펴본다. 특히 최근의 언어학적 연구가 언어정보의 전산처리와 언어습득, 언어장애, 언어교육 등에 어떻게 실제로 적용되고 있는지를 소개하고, 미래의 새로운 언어학적 연구 분야를 탐색하게 한다.");
//
//            m5MajorName.add("(필)말소리의 세계");
//            m5MajorIntro.add("말소리를 어떻게 분류하고 어떻게 발음하는지 체계적으로 학습하고, 이를 토대로 한국어, 영어, 불어, 이태리어 등 여러 언어의 말소리를 분별해서 듣고 정확하게 발음할 수 있는 능력을 함양한다. 아울러 여러 언어의 강세, 리듬, 성조, 억양에 대해서도 체계적으로 학습한다.");
//
//            m5MajorName.add("(탐)언어와 컴퓨터");
//            m5MajorIntro.add("인간언어에 대한 연구가 여러 가지 정보축적과 정보소통의 문제와 어떤 관련을 맺고 있는지를 소개한다. 인간의 자연언어와 컴퓨터의 인공언어의 공통점과 차이점을 이해한다. 언어정보의 자동처리 방법과 응용을 소개한다. 인간 언어에 대한 기초연구가 어떻게 음성인식, 음성합성 등의 음성정보 처리와 구문 분석, 의미정보 처리에 응용되며, 현대 정보사회의 발달을 위한 정보검색, 요약, 필터링, 그리고 기계번역 등에 적용되는지를 소개한다.");
//
//            m5MajorName.add("(탐)세계의 언어");
//            m5MajorIntro.add("세계의 주요 언어들을 대상으로 계통론적 관점과 유형론적 관점에 입각하여 대조.비교함으로써, 언어의 보편적 특성과 개별적 특수성에 대한 새로운 인식을 높인다. 아울러 사회언어학적, 인류인어학적 관점에서 언어의 변화와 변이를 통한 언어의 다양성에 대해 이해한다.");
//
//            m5MajorName.add("만주어");
//            m5MajorIntro.add("이 교과목의 목표는 문어 만주어를 독해할 수 있는 기초적인 능력이 있는 수강자들이 문어 만주어의 독해 능력을 심화시키고, 만주어의 특징을 이해할 수 있도록 하는데 있다. 이를 위하여, 청나라 시기에 편찬된 만주어 문헌을 독해하고, 국내외에서 이루어진 만주어 및 퉁구스어학, 더 나아가 알타이어학의 연구 결과를 개괄한다.");
//
//            m5MajorName.add("언어학사");
//            m5MajorIntro.add("이 강좌는 언어학의 전체적 흐름을 파악하여 언어학에 대한 거시적 안목을 갖게 하는 것을 목표로 한다. 언어학이란 그 기원을 거슬러 올라가 보면 이미 희랍시대부터 연구가 행해져 왔던 학문이므로, 언어학의 초기단계였던 희랍시대부터 언어학이 독자적인 학문으로서의 위치를 차지하게 되는 19세기의 독일 젊은이 문법학파의 이론 및 현대의 기능구조주의 학파와 생성문법에 이르기까지 그 이론적 배경과 각 학파의 특색을 동서양의 문법이론을 소개하여 살펴본다.");
//
//            m5MajorName.add("형태론");
//            m5MajorIntro.add("이 강좌는 언어학의 전체적 흐름을 파악하여 언어학에 대한 거시적 안목을 갖게 하는 것을 목표로 한다. 언어학이란 그 기원을 거슬러 올라가 보면 이미 희랍시대부터 연구가 행해져 왔던 학문이므로, 언어학의 초기단계였던 희랍시대부터 언어학이 독자적인 학문으로서의 위치를 차지하게 되는 19세기의 독일 젊은이 문법학파의 이론 및 현대의 기능구조주의 학파와 생성문법에 이르기까지 그 이론적 배경과 각 학파의 특색을 동서양의 문법이론을 소개하여 살펴본다.");
//
//            m5MajorName.add("음운론");
//            m5MajorIntro.add("본 강좌에서는 음운론개론을 강의한다. 인간언어에서 관찰되는 여러가지 음운현상을 기술하고 분석하는 방법을 배우게 된다.");
//
//            m5MajorName.add("(필)역사비교언어학");
//            m5MajorIntro.add("19세기부터 발전하기 시작한 역사비교언어학의 기본 개념을 다루며 아울러 언어변화의 여러 유형들을 설명하고 역사비교언어학에서의 문제점들을 제시한다. 이 강좌는 4학년 과정에 개설되어 있는 알타이어학과 인구어학의 이수를 위학 기초 과목이다. 따라서, 이들 분야에 적용할 수 있는 초보적 방법론에 중점을 두고 강의한다. 또한 강의 후반부에서는 국어에 관련된 자료들을 직접 다루어 봄으로써 앞으로의 연구를 위한 기초를 다진다. 이 강좌를 이수하기 위한 기초 과목은 ‘음운론’이다.");
//
//            m5MajorName.add("(필)통사론");
//            m5MajorIntro.add("단어들의 결합에 의한 문장이나 구 절의 기능 및 구조를 분석하는 것을 목표로 한다. 이 강좌는 Chomsky를 중심으로 발전하고 있는 변형생성문법의 여러 이론들을 소개하고, 또한 여러 이론의 변화 배경 및 과정을 검토 비판한다. 최근에 와서는 통사론을 중심으로 문법을 기술하려는 주장이 강해지고 있는데, 이러한 여러 이론들의 특징과 장단점을 포괄적으로 살펴 본다. 또한 종래의 형태론에서 다루어 온 문제들을 통사론에서 어떻게 다루는지, 그리고 의미론과의 관계는 어떠한지를 함께 탐구한다.");
//
//            m5MajorName.add("의미론");
//            m5MajorIntro.add("언어의미를 과학적으로 분석하기 위한 기초적인 훈련을 쌓는다. 의미론의 영역, 위상, 방법에 관한 서론과 어휘의미론, 형식의미론 및 화용론의 기초 개념의 이해와 실제 언어사실에의 응용을 목표로 한다. 의미론은 최근 활발한 연구가 진행되고 있는 분야이므로 강의 내용은 주로 최근의 방법론이 위주가 되며, 이와 병행하여 전통적인 구조주의적 의미론을 소개함으로써 양자의 차이와 장단점을 이해할 수 있도록 한다. 이 강좌는 ‘언어와 언어학’, ‘형태론’, ‘통사론’을 기초로 한다.");
//
//            m5MajorName.add("심리언어학");
//            m5MajorIntro.add("이 강좌의 목적은 인간이 언어체계를 어떻게 사용하느냐, 언어를 사용하는 것을 어떻게 배우느냐를 연구하는데 있다. 따라서 이 강좌는 언어습득 및 현대 언어학 이론에서 제기된 언어능력이 실제적 토대를 가지고 있는가의 여부가 그 주된 내용이 되며, 발화의 산출과 지각에 있어서의 인간의 행동 방법, 기억과 지능의 언어수행에의 기여 여부, 언어와 사고 과정과의 상호 관련 방법 등도 이 강좌의 내용에 포함된다.");
//
//            m5MajorName.add("특수언어특강");
//            m5MajorIntro.add("어문계열의 언어학 분야 전공자들에게 역사언어학 및 공시언어학의 관점에서 학술적으로 상당한 가치를 지닌 다양한 특수외국어들을 습득할 기회를 제공한다. 몽골어와 터키어는 한국어와 여러 가지 문법적 혹은 형태적 유사성을 지닌 알타이제어 계통의 언어를 연구하는데 있어서 중요한 자료가 된다. 본 강좌는 알타이제어인 몽골어와 터키어를 비롯한 기타 특수 외국어의 구조, 형태, 음운적 특성에 포괄적으로 접근할 기회를 제공하는 것을 목표로 한다.");
//
//            m5MajorName.add("(필)컴퓨터언어학");
//            m5MajorIntro.add("이 강좌에서는 근래에 들어 언어학과 컴퓨터과학이 밀접한 관련을 맺으면서 확립되기 시작한 컴퓨터언어학의 기초적 지식을 소개하고, 그러한 컴퓨터과학의 관점에서 언어가 어떻게 연구될 수 있는가 하는 점이 모색한다. 따라서 이 강좌의 내용에는 단어 빈도 계산, 색인 작성, 기계 번역, 언어 인지, 언어 합성을 위한 언어처리의 방법론 등이 포함된다. 컴퓨터언어학의 수강을 위해서는 최근의 통사이론 및 의미이론에 대한 전반적인 지식이 먼저 요구된다.");
//
//            m5MajorName.add("언어학을 위한 통계");
//            m5MajorIntro.add("본 교과목은 학생들에게 언어 연구에 필요한 통계의 개념과 방법론에 대한 전반적인 지식을 제공하고, 통계 프로그램인 R을 소개하는 과목이다. 본 교과목은 초보자와 숙련자들에게 가장 널리 사용되는 무료 오픈 소스 프로그램인 R을 사용한다. 수강생들은 강의를 통하여 현대 언어학에서의 통계의 역할을 이해하고, 실습을 통하여 실제 언어 자료를 가공하고 분석하는 경험을 쌓는다. 이 교과목은 특정 연구 주제에 적용될 수 있는 통계 방법과 분석 전에 복잡한 자료를 가공하는 방법을 중점적으로 강의하는 <실험언어학>과 같은 학과의 고급 과정을 수강하기 위한 기초를 제공한다.");
//
//            m5MajorName.add("심화통사론");
//            m5MajorIntro.add("본 강의는 현대통사이론의 기초 개념과 주요 이슈를 세미나 형식으로 다루는 학부 수업이다. 문법 구성 체계, 구 구조, 논항 구조, 구조와 형태의 관계, 장거리 의존관계와 일치 현상, 이동과 의미해석 등 통사론의 주요 논제들을 최소주의 통사론의 관점에서 심도 있게 다룬다. 본 수업을 통해서 학생들이 현대통사이론의 기초를 탄탄히 쌓고 학습한 이론을 여러 언어의 통사 현상에 적용하고 분석하는 방법을 익히는 것을 목표로 한다.");
//
//            m5MajorName.add("심화음운론");
//            m5MajorIntro.add("본 교과목에서는 학생들에게 현대 음운론 연구에 대한 포괄적인 지식을 제공하고자 한다. 수강생들은 현대 음운론 이론, 실험 및 코퍼스를 통한 자료 수집 방법, 데이터 사이언스 기법을 활용한 자료 분석에 대해 배울 것이다. 이 교과목의 목적은 학생들로 하여금 최근의 전문적인 음운론 연구 이해하고, 또한 자신이 직접 전문적 수준의 연구를 수행할 능력을 갖추게 하는 것이다.");
//
//            m5MajorName.add("사회언어학");
//            m5MajorIntro.add("사회언어학은 언어의 사회적 문화적 기능과 관련된 언어구조 및 언어사용의 모든 면을 포함하는 분야로써 사회와 관련된 언어의 연구라고 정의할 수 있다. 따라서 이 강좌는 먼저 사회언어학의 성립 배경과 그 언어학적 의의를 살펴 보고, 지금까지 연구된 기존의 사회언어학적 연구 결과를 개괄적으로 다룬다. 그리하여 언어구조 및 언어사용이 화자 또한 그가 속해 있는 집단의 사회적 위치와 어떤 관계가 있으며, 또한 그러한 관계는 어떻게 체계화시킬 수 있는가를 모색한다.");
//
//            m5MajorName.add("언어조사 및 분석");
//            m5MajorIntro.add("실제 발화 현장에 나가 언어자료를 수집하고 분석함으로써 이론을 통해 습득한 언어 원리를 적용해보는 교과목이다. 이를 토대로 학생들은 방언학적, 역사–비교 언어학적 이론의 실재를 경험하게 된다.");
//
//            m5MajorName.add("인도유럽언어학");
//            m5MajorIntro.add("인구어학은 고도의 개연성을 지닌 가설로서 직관보다는 구체적인 언어 자료를 토대로 하여 F. Bopp에서 비롯되어 A.Schleicher에 의해서 체계화 되었고 젊은이 문법학파 시대에 절정을 이루었다. 인구어학의 주요과제는 비교의 토대기 되는 제3의 기준을 통하여 둘 또는 다수의 언어를 체계적으로 비교하는 작업인데 그 목적은 언어의 모든 체계를 비교하여 기술하는 데 있지만 실제로는 부분적인 영역만을 비교하게 된다. 인구어학(역사–비교언어학)의 가장 큰 특징을 언어를 역사적 창조물로 보고 언어의 동적인 면 즉 언어변화에 관한 연구에 역점을 둔다는 데 있다. 그 결과 주요 연구대상으로 초기에는 공통조어의 재구와 친족관계의 규명이었으나 후기에는 음성학과 형태론 등 언어의 형식적인 면이 부각되었으며 이러한 연구는 현상에 관한 관찰, 연구와 설명을 목적으로 하는 사회과학의 연구방법론에 위해서 수행된다.");
//
//            m5MajorName.add("언어와 정보처리");
//            m5MajorIntro.add("본 과목에서는 언어학의 정보처리 분야 응용에 대해서 인간과 컴퓨터 사이의 가장 자연스러운 의사소통 수단인 음성대화 인터페이스를 중심으로 배운다. 구체적으로는 음성학과 음운론, 형태론, 통사론, 의미론 등의 언어학 세부 분야의 기초이론과 음성 대화 시스템과의 관련성을 살펴보며, 음성인식과 음성합성, 음성언어 이해의 기본 개념과 언어처리 계산모델 및 소프트웨어 구현 방법을 소개한다.");
//
//            m5MajorName.add("실험언어학");
//            m5MajorIntro.add("본 교과목은 실험 방법론을 이용하여 인간의 언어를 연구하는 방법을 가르치는 강좌이다. 학생들에게 현대 언어학에서 중요하게 부상한 실험연구 방법론들을 소개하고, 랩 실습을 통하여 실제로 실험연구를 구현하고 통계적으로 분석해 보도록 지도한다. 본 교과목에서는 실험연구와 관련된 이론 수업과 실습수업을 병행하며, 언어학의 하위 분야(음성, 음운, 형태, 통사, 의미, 언어처리 및 발화)를 연구하는데 활용되는 다양한 실험연구 방법들을 소개한다. 본 강좌는 언어학 및 개별 어학에 관심이 있는 학생들에게 실험언어학의 기초 지식을 제공하고, 이를 개별 연구에 활용할 수 있도록 하는 능력을 함양하는 데 그 목표를 둔다.");
//
//            m5MajorName.add("언어습득");
//            m5MajorIntro.add("본 교과목은 인간의 언어 습득 능력을 설명하는 이론과 이와 관련된 논쟁을 소개한다. 영유아와 아동의 언어 습득 과정을, 소리, 어휘, 의미, 문법, 화용 습득으로 나누어 그 자세한 과정과 원리에 대해 탐구하며, 이와 더불어 이중 언어 발달에 대하여도 함께 다룬다. 또한 본 교과목에서는 언어 습득 연구에 사용되는 연구 방법을 탐구할 뿐만 아니라, 다양한 언어 습득 데이터를 직접 분석하면서 언어 습득 연구에 대한 실질적인 경험을 쌓는 것을 목표로 한다. 언어 습득의 세부 주제는 음성학, 음운론, 형태론, 의미론, 통사론 등 언어학의 주요 하위 분야와 밀접하게 관련되어 있으며, 언어 이론의 주요 쟁점들과 직접적으로 연관된다.");
//
//            m5MajorName.add("추론과 언어직관");
//            m5MajorIntro.add("본 강의는 자연언어 의미론과 추론심리학의 접점에 있는 연구주제들을 다룬다. 학생들은 명제논리, 술어논리, 양상논리, 유형 람다대수 등 자연언어의 이해에 필수적인 도구들에 대해 배우게 될 것이다. 또한 이들만으로는 설명할 수 없는 추론심리학의 발견들, 예를 들어 선언문과 양화사의 추론 오류와 대표성 휴리스틱에 대해 소개할 것이며, 추론심리학에서 이들 현상들을 기술하기 위해 도입한 기제들이 언어학 내에서 독립적으로 발전한 이론들로부터 설명적 타당성을 얻게 됨을 보일 것이다.");
//
//            m5MajorName.add("알타이언어학");
//            m5MajorIntro.add("알타이어학은 한국어의 계통론과 관련하여 중요한 분야이다. 이 과목은 역사비교언어학의 기초 지식을 전제로 하며 만주–퉁구스어, 몽고어, 터키어 등 각 알타이어의 개요, 이들의 비교 연구, 알타이 가설에 대한 고찰, 한국어와 알타이제어의 비교는 비교적 큰 비중을 두고 다루어지며 인구어 비교언어학에서 얻어진 방법론의 알타이제어에 대한 적용 가능성 및 타당성 여부도 중요한 문제로 다루어진다.");
//
//            m5MajorName.add("언어학연습 1");
//            m5MajorIntro.add("음운론(108.208)에서 배운 내용을 바탕으로 음성, 음운, 형태론의 주요쟁점을 선택하여 심도 있게 다룬다. 논문작성에 집중함으로써 졸업논문을 준비하는 수업이며 대학원 수준의 수업과 직접 연결될 수 있다.");
//
//            m5MajorName.add("언어장애 및 치료");
//            m5MajorIntro.add("언어병리학을 개관한다. 이를 위하여 우선 언어를 중심으로 한 정상적인 의사소통과, 장애가 있는 의사소통을 비교한다. 이러한 장애는 크게 두 가지로 분류할 수 있는데, 하나는 감각기관이나 발화기관과 같은 신체 외부상의 장애로 인한 경우와, 언어정보처리과정의 중심에 있는 뇌의 손상에 의한 경우가 그것이다. 이렇게 원인에 따라 다양하게 나타나는 증상과 함께 이에 대한 치료방법을 살펴보고, 언어학과 언어병리학의 상관관계에 대해서도 생각해 본다.");
//
//            m5MajorName.add("수어언어학");
//            m5MajorIntro.add("수어는 인간 언어 능력을 들여다볼 수 있는 독특한 창문 역할을 하므로 본 교과목은 한국수어와 세계수어의 비교를 통하여 소리언어의 청각과 음성 양식(auditory-vocal modality) 이외의 또 다른 인간 언어 양식인 수어의 시각과 몸짓 양식(visual-gestural modality)을 소개한다. 소리언어와의 비교에서 출발하여 수어의 음운, 형태, 통사, 의미 등의 언어학적인 특징과 세계수어와의 유형론적인 특징을 논의하고 자료 중심의 코퍼스 연구와 기계번역과 인공지능을 활용한 최근 수어연구를 소개한다.");
//
//            m5MajorName.add("응용음성학");
//            m5MajorIntro.add("이 강좌에서는 음성학적 지식이 어떤 분야에서 응용될 수 있는지 논의된다. 최근 들어 활발하게 논의되고 있는 한국어와 영어 표준발음 교육과 관련된 언어교수법, 언어활동의 장애(예를 들어 실어증과 같은) 요인과 그 치료를 연구하는 언어 치료 방법론 모색이 주요 강의 내용이 되며, 이밖에도 언어 정책이나 음성정보처리 분야에서 음성학적 지식을 어떻게 응용할 수 있는지 모색한다.");
//
//            m5MajorName.add("언어학연습 2");
//            m5MajorIntro.add("언어학의 주요 쟁점을 주제별로 심도 있게 다룬다. 이를 위해 기존 연구를 비교 검토하며, 실제 자료를 수집하고 분석하여 결론을 이끌어 내는 과학적 논증을 훈련한다.");
//
//            m5MajorName.add("화용론");
//            m5MajorIntro.add("언어와 그 사용 맥락의 상관성을 탐구한다. 의미론이 문장의 의미를 진리조건으로 다루는데 비해, 화용론은 문장의 의미와 맥락의 상호 작용을 설명한다. 담화 맥락은 (1) 담화상에 드러난 언어적 맥락, (2) 시공간의 물리적 맥락, 그리고 (3) 일반 지식 및 사회적 맥락을 포괄한다. 전제와 함축에 의한 추론의 설명, 발화 행위의 역학적 적정조건, 지시 표현의 맥락 해석, 대화 및 담화 구조, 언어표현에 나타난 사회–문화 구조적 해석, 대중 매체의 담화 해석 등이 화용론의 주요 주제들이다.");
//
//            m5MajorName.add("언어데이터과학");
//            m5MajorIntro.add("본 교과목에서는 언어학을 위한 데이터과학의 방법론과 기술을 학습한다. 언어 데이터를 수집, 정제, 정리, 저장, 관리, 요약, 분석하고 데이터 프로덕트를 만드는 방법론을 살펴봄으로써 데이터를 다루는 전과정을 이해할 수 있도록 한다. 이와 함께 실질적인 소프트웨어 도구와 기술을 익히고 예제를 통해 그 실제 응용을 경험함으로써 기술을 활용할 수 있는 능력을 배양한다. 언어학 이론과 컴퓨터 프로그래밍에 기본 지식을 갖춘 전공자들에게 고급 기법을 소개하고 종합적인 응용을 경험할 수 있도록 하는 것을 목표로 한다. 이 수업에서 학습하는 방법론과 기술은 컴퓨터언어학, 자연언어처리와 같은 직접 관련 분야에 한정되지 않고 언어학의 다양한 분야의 데이터 분석을 위해 유용한 것으로 선정될 것이다.");
//
//            randomTimeLectureRoom(c1m5Professor, c1Major.get(4), c1LectureRoom, m5MajorName, m5MajorIntro);
//
//            // c1m6
//            List<Professor> c1m6Professor = em.createQuery("select p from Professor p where p.major.idx = :midx", Professor.class).setParameter("midx", c1Major.get(5).getIdx()).getResultList();
//
//            List<String> m6CultureName = new ArrayList<>();
//            List<String> m6CultureIntro = new ArrayList<>();
//
//            m6CultureName.add("논리와 비판적 사고");
//            m6CultureIntro.add("철학적 사고, 비판적 사고의 핵심적 특성은 논리적이라는 것이다. 오늘날 그 중요성이 더욱 높아지고 있는 정보의 조직적 정리능력 및 합리적 사고능력의 개발에 있어서 논리적 사고의 훈련은 긴요하다. 본 과목은 그에 대한 초보적 훈련을 제공하는 것을 목적으로 한다. 그 다루는 내용은 일상언어의 기반 위에서 이루어진 전통적 아리스토텔레스 논리학에서부터 전통논리학의 새로운 해석과 기호화를 통해 이루어진 현대 기호논리학 즉 명제논리와 술어논리 등 기호이치논리학 전반에 이른다. 이 과정에서 논리학의 구문론적 접근과 의미론적 접근의 비교설명도 덧붙여진다.");
//
//            m6CultureName.add("서양철학의 이해");
//            m6CultureIntro.add("본 교과목의 목표는 철학적 문제의 본질적인 성격을 해명하고 중요한 철학적 주제들을 검토하면서 철학이 어떤 학문인가를 살펴보는데 있다. 본 교과목의 강의 내용은 크게 2부로 나뉘어진다. 강의의 서두에 해당하는 제1부에서는 철학에 대한 일반적이며 예비적인 고찰이 이루어진다. 강의의 제2부의 목표는 서양철학의 중요한 주제들을 검토하면서 제1부에서 이루어진 철학에 대한 일반적이며 예비적인 이해를 보다 더 구체화하고 심화시키는데 있다.");
//
//            m6CultureName.add("동양철학의 이해");
//            m6CultureIntro.add("전통적으로 동양철학의 범주에는 철학사상으로 불교, 유교, 도교가 포함되고 지역적으로는 남아시아(인도, 세일론), 동북아시아(중국, 일본, 한국), 동남아시아(미얀마, 태국 등)가 그 범위에 든다. 경우에 따라서는 아랍권인 서남아시아를 포함시키기도 한다. 본 과목은 교양과목으로서 학생들의 동양철학에 대한 기대를 충족시키기 위해 지역학적 특색을 강조하기보다는 문화, 사상적인 측면에 비중을 두는 것이 바람직하다. 따라서 크게 불교, 유교, 도교, 한국의 민간신앙에 나타난 삶과 죽음의 문제를 다루게 될 것이다.");
//
//            m6CultureName.add("현대사회와 윤리");
//            m6CultureIntro.add("이 강의에서는 과학 기술의 발달에 따라 현대사회에서 새로이 제기되는 주요한 윤리적 문제로서 생명·의료윤리, 정보윤리, 환경윤리 등에 대해 실천윤리(practical ethics)적 접근을 하고자 한다. 또한 전통과 근대성이 맞부딛히면서 공존하는 현대 한국 사회의 특수한 윤리적 상황을 고찰하는 장을 마련하여 지금까지 다룬 여러 이론적 성찰들을 매개로 수강생들이 직접 현재의 우리 모습에 대해 반성적으로 성찰해보는 기회를 갖고자 한다. 특히 현재 한국사회의 민주주의 구축과 관련해서 문제가 되고 있는 여러 주제들, 예컨대 집단주의, 신뢰성, 유교윤리 등을 다루고자 한다.");
//
//            m6CultureName.add("한국철학의 이해");
//            m6CultureIntro.add("한국에서 전개된 철학적 사유를 고대에서부터 근대에 이르기까지 개관한다. 먼저 유, 불, 도 3교의 한국적 전개에 따른 그 시대적 사상과 특징을 체계적으로 살펴본 다음 원효, 지눌, 퇴계, 율곡 등 주요 사상가의 철학을 집중적으로 검토할 것이다. 실학자들에게서 나타나는 세계관의 변화와 서양사상의 수용이 어떤 과정을 통해 이루어지나를 살펴봄으로써 현재의 한국철학이 성립된 역사적 과정을 알아보고 전통사상과 현대철학의 차이에 관해서도 토론해 보고자 한다.");
//
//            m6CultureName.add("철학개론");
//            m6CultureIntro.add("인간존재는 그 근본에 있어서 왜 철학을 외면할 수 없는 것인가, 철학이 제기하는 물음이란 주로 어떠한 성질의 것이며, 이러한 물음들에 대해 고래로 어떠한 해답들이 주어지고 있는가를 개관하고, 아울러 현재의 우리의 철학적 과제가 무엇인가에 대한 전망을 준다.");
//
//            m6CultureName.add("생명의료윤리");
//            m6CultureIntro.add("이 강좌에서는 오늘날 가장 중대한 개인적, 사회적 관심사의 하나로 부각되고 있는 생명의료문제를 다룬다. 임신중절, 안락사, 장기이식 문제 등에 대한 철학적 성찰과 논의를 통해 이러한 문제들에 대한 사회적, 개인적 의사결정 과정의 도덕적, 윤리적 근거를 탐구한다. 이 강좌의 내용을 통해서 수강학생들은 현대사회의 문제와 이러한 문제들을 둘러싼 여타의 근본적인 문제들에 대한 안목을 얻게 될 것이다.");
//
//            m6CultureName.add("사회철학의 이해");
//            m6CultureIntro.add("사회현실의 인식문제를 둘러싸고 일어난 방법논쟁에 관한 사적인 검토를 거쳐서, 사회인식의 이론정립에 있어서의 경험적, 분석적 방법의 타당성과 그 한계를 설명하며, 해석학과 변증법의 과학적인 성격을 밝힘으로써 이것들의 사회인식론으로서의 효용성을 연구하는 한편, 인간의 사회적 존재의 구조를 현대철학의 제이론을 통해 조명하여 인간의 공존재성의 특징을 규명한다.");
//
//            m6CultureName.add("동양철학의 고전");
//            m6CultureIntro.add("본 과목은 중국과 인도를 중심한 동양의 철학사상을 이해하기 쉽게 마련한 교양과목이다. 우선 중국의 선진시대 제자백가의 다채로운 학설을 소개하여 동양인의 정신적 지혜의 탁월성을 인식시키고 그 지적 유산의 사상적 영향과 확산현상을 소개한다. 인도사상 및 불교사상의 전개도 그 기본성격과 각 지역에서 전개 발달된 제특징을 학습함으로써 다양한 철학사상에 대한 인식을 도모한다.");
//
//            m6CultureName.add("과학과 비판적 사고");
//            m6CultureIntro.add("학생들이 일상 생활이나 미래의 전문적 활동에서 접하게 될 다양한 과학적 정보를 이해하고 비판적으로 평가하는 능력을 갖추도록 만드는 것이 이 과목의 목표이다. 이를 위해 여러 가지 유형의 과학적 가설들을 분석하고 평가할 수 있는 이론적 틀을 개발하고 다양한 사례들에 적용하는 과정을 밟는다. 또한 과학적 지식이 개인적 또는 공적 의사 결정에 영향을 미치는 여러 가지 형태를 이해하고 평가하는 방식을 다룬다.");
//
//            m6CultureName.add("과학의 철학적 이해");
//            m6CultureIntro.add("이 과목에서는 경험과학과 관련된 여러가지 철학적 물음들을 선택적으로 다루게 될 것이다. 과학의 목표는 무엇이며, 그것을 성취하기 위해 과학자들은 어떤 방법들을 사용하는가? 과학 활동은 세계에 대한 진리를 산출하는가? 과학은 과연 진보하는가? 과학적 활동은 흔히 이야기되는 것처럼 합리적인가? 과학은 가치중립적인가? 과학과 사이비과학의 구분은 어떻게 가능한가?");
//
//            m6CultureName.add("성의 철학과 성윤리");
//            m6CultureIntro.add("남녀 성 차이와 성 차별에 대한 이론들, 현대 여권주의의 다양한 주장 및 반론 등을 통해 인간과 성(gender)에 대한 철학적 이해를 도모하고 성(sex)과 결혼 및 사랑의 관계, 동성애와 이성애, 성적 도착과 변태, 포르노그라피 등 성 윤리와 관련된 갖가지 문제들을 논의하는 가운데 인간과 사랑 그리고 성에 대한 각자의 입장을 성찰하는데 도움을 주고자 한다.");
//
//            m6CultureName.add("논리학");
//            m6CultureIntro.add("논변에는 형식을 기반으로 하는 논변과 비형식, 즉 내용을 기반으로 하는 논변이 있는데, 이 과목은 주로 논변의 형식적 측면에 있어서 올바른 논변과 올바르지 못한 논변을 구분하는 방법과 원리를 알아보고, 이를 익히는 것을 목적으로 한다. 그 다루는 내용은 일상언어의 기반 위에서 이루어진 전통적 아리스토텔레스 논리학에서부터 전통논리학의 새로운 해석과 기호화를 통해 이루어진 현대기호논리학 즉 명제논리와 술어논리 등 기호이치논리학 전반에 이른다. 이 과정에서 논리학의 구문론적 접근과 의미론적 접근의 비교설명도 덧붙여진다.");
//
//            m6CultureName.add("중국고전과 중국사상");
//            m6CultureIntro.add("중국고전은 중국문화의 근원이자 핵심으로 중국의 가치관을 집중적으로 반영하고 있다. 수천 년의 역사상, 중국고전은 중국문화에 깊은 영향을 미쳤을 뿐만 아니라 세대를 거듭하면서 끊임없이 중국인의 인격과 정신을 형성하는 근원의 역할을 해 왔다. 이 강좌를 수강함으로써 학생들은 좀 더 심도 있게 중국전통문화와 중국역사를 이해할 수 있게 될 것이며, 또한 좀 더 생생하게 현대 중국인과 중국사회의 면모를 파악하게 될 것이다. 본 강의의 주요한 내용은 두 가지이다. 첫째, 중국고전을 가려 뽑아 읽고 그 의미를 탐구하는 것이다. 둘째, 중국고전을 단서로 하여 중국전통문화와 중국현대사회에 대해 진일보한 비교 분석을 수행하는 것이다. 이 강좌는 중국 역사상 가장 중요하면서도 영향력이 큰 8개의 고전을 중심으로 진행될 것이다. 이들 텍스트 중. 《상서》,《주역》은 공자가 편집 정리했다고 하는 6경 가운데 사상적 측면이 비교적 강한 고전이다. 《논어》,《대학》,《중용》,《맹자》는 이른바 4서로 유가의 핵심경전이다. 《노자》,《장자》는 도가의 핵심경전이다.");
//
//            m6CultureName.add("도덕적 추론");
//            m6CultureIntro.add("이 교과목은 가치다원적 사회에서 도덕적 불일치에 직면하여 우리가 합리적으로 구사할 수 있는 도덕적 추론을 교육한다. 이 교과목은 수강생들로 하여금 현실의 도덕적 문제에 부딪히거나 도덕적 불일치 상황에 직면하였을 때 어떤 태도를 갖추어야 하고 어떤 도덕적 요소를 고려하고 어떤 사고과정을 거쳐야 하는지 안내하며, 현실의 도덕적 이슈에 관한 토론을 통해 그 과정을 체득하게 하는 것을 목적으로 한다. 도덕적 사고의 특징, 주관주의적, 상대주의적 도덕에 대한 대응, 기본적인 도덕이론과 도덕원칙, 논증의 종류와 분석과 평가, 전략적 문제해결에 대조되는 것으로서의 도덕적 문제해결, 여러 현실 속의 도덕적 이슈들을 다룬다.");
//
//            m6CultureName.add("법과 가치");
//            m6CultureIntro.add("현재 한국사회의 논쟁적인 법적 사례 – 예를 들어, 연명치료 중단의 허용, 사형제·낙태죄·간통죄·자발적 성매매처벌·군내 동성간 성행위 처벌·제대군인가산점제도의 위헌성, 도박 및 흡연에 대한 개인과 국가의 책임 범위 – 들에 대한 헌법재판소와 대법원의 판결을 비판적으로 분석·검토하고, 이를 통해 자연스럽게 윤리학·정치철학 이론들의 핵심 아이디어를 소개하며, 학생들의 토론을 통해 일견 대립하는 가치들에 대해 합리적으로 성찰하는 능력을 기르는 것을 목표로 한다.");
//
//            m6CultureName.add("불교철학의 이해");
//            m6CultureIntro.add("일반 교양강좌로 제공하는 불교철학 입문과정이다. 석가세존의 생애와 사상을 시발로, 인도 대승불교 철학의 두 체계와 중국종파불교의 전개 및 한국불교의 역사와 사상 그리고 현대사회의 여러 문제를 불교철학적 관점에서 어떻게 논의할 수 있는지를 다룰 것이다.");
//
//            m6CultureName.add("인공지능과 철학");
//            m6CultureIntro.add("이 과목은 인공지능의 중요한 철학적, 인문학적 쟁점들을 고찰한다. 기계는 생각할 수 있는지, 인공지능은 가능한지, 기계가 감정이나 의식을 가질 수 있는지 등의 존재론적 물음뿐 아니라 인공지능의 도덕적 지위와 로봇 윤리의 문제, 그리고 인공초지능과 실존적 위협에 관해 다룬다.");
//
//            m6CultureName.add("서양철학의 고전");
//            m6CultureIntro.add("이 과목은 세계, 자연, 인간, 사회를 바라보는 포괄적이고 근본적인 관점을 제시하는 서양철학의 대표적인 고전 작품들을 다루며, 스스로의 힘으로 철학적 고전을 읽고 서양철학의 주요한 논제들과 개념들을 이해할 수 있는 수강생들의 역량을 함양하는 데에 목적을 둔다. 학생들은 철학적 고전 텍스트의 의미와 가치를 탐구함으로써, 어떻게 철학적 성찰과 논쟁을 통해 일상생활 속의 다양한 문제들에 비판적으로 참여할 수 있는지를 배우게 될 것이다.");
//
//            randomTimeCultureRoom(c1m6Professor, c1Major.get(5), c1LectureRoom, m6CultureName, m6CultureIntro);
//
//            List<String> m6MajorName = new ArrayList<>();
//            List<String> m6MajorIntro = new ArrayList<>();
//
//            m6MajorName.add("(탐)인도불교철학");
//            m6MajorIntro.add("이 교과목은 인도철학과 불교철학 일반을 다룬다. 인도에서 나타난 여러 철학 학파들의 형성과 발달을 역사적으로 고찰하고, 인도의 문화적 역사적 배경 속에서 탄생하여 아시아의 여러 문화권에서 다양한 사상적 전통을 형성한 불교에 대해 고찰한다.");
//
//            m6MajorName.add("(탐)서양고대철학");
//            m6MajorIntro.add("서양 철학의 고중세 시기에 어떤 문제들이 어떤 방식으로 제기되고 제기된 문제들의 해결을 위해 도입된 개념과 논변들이 어떤 것인지를 당시 작품의 강독을 통해 검토한다. 플라톤, 아리스토텔레스, 아우구스티누스, 토마스 아퀴나스의 문헌이 일차적인 선택 범위에 들며, 철학적 분석과 문헌학적 역사적 접근방법을 통해 학생들에게 서양 고중세의 철학적 문헌을 학적으로 접근하는 방식이 어떤 것인지를 이해하도록 한다.");
//
//            m6MajorName.add("중국고대철학");
//            m6MajorIntro.add("이 교과목은 전한 시대 이전에 활발하게 전개된 유가, 도가, 법가, 묵가 등 제자백가들의 다양한 철학적 논의를 당대 사회의 변화․발전과 연관시켜 비교 연구한다. 또한 동증서의 사상 등 전한시대 철학에 대해서도 다룬다.");
//
//            m6MajorName.add("(필)한국철학사");
//            m6MajorIntro.add("한국의 역사적 문화와 學으로서의 철학을 한데 엮는 방법론의 모색을 출발점으로 해서 우선 한국철학의 출발점의 문제 및 원시사상의 근본가정을 살펴본 다음, 불교, 유교, 기독교 등 종교사상을 근간으로 하는 외래사조가 한국적으로 소화, 흡수되어 변용 및 창조적으로 재구성되는 과정을 역사적인 조감법으로 개괄한다.");
//
//            m6MajorName.add("(필)기호논리학");
//            m6MajorIntro.add("논변에 대한 분석에 수학적 기법을 도입함으로써 그 엄밀성과 명료함에 있어서 비약적인 발전을 이룬 현대 기호논리학의 내용을 개괄한다. 기호논리학의 주요 개념들을 알아보고, 일상언어로 표현된 논변들을 명제논리 및 술어논리의 문장들로 기호화할 수 있는 형식 언어를 소개한다. 또한 기호화된 문장들로 구성된 논변들의 타당성을 증명할 수 있는 논리체계를 고찰하고, 이 논리체계의 건전성과 완전성을 비롯한 몇가지 메타논리학적 결과들을 검토한다.");
//
//            m6MajorName.add("철학 교육을 위한 논리학");
//            m6MajorIntro.add("고등학교 철학 교육의 주된 목표는 학생들의 논리적이고 비판적인 사고 능력을 증진시키는 것이다. 본 과목에서는 이를 위해 고등학교 철학 교육에서 형식 논리학과 비형식 논리학을 효과적으로 활용하는 법을 배운다. 먼저 과목 전반부에서는 형식 논리학과 비형식 논리학의 기본적 내용을 개괄한다. 과목 후반부에서는 고등학교 철학 교과서 및 관련 문헌들의 내용을 실제로 분석하고 평가하는데 있어 논리학적 지식을 어떻게 활용할 수 있는지 검토한다.");
//
//            m6MajorName.add("철학교육론");
//            m6MajorIntro.add("고등학교에서의 철학교육의 목표는 각 피교육자로 하여금 자율적인 사고, 비판적인 사고, 반성적인 사고 등을 함양토록 함으로써 건전한 상식과 도덕감을 갖춘 민주사회의 한 창조적 역군이 되도록 하는 데에 있다. 이 목표를 위해서 철학교육의 내용은 어떠해야 하며, 또 그 내용을 어떻게 가르치는 것이 효과적일지를 집중적으로 검토, 논의한다.");
//
//            m6MajorName.add("철학교재연구 및 지도법");
//            m6MajorIntro.add("중 고등학교 교육 과정에서 사용되는 『철학』, 『논리학』 교과서와 교사 지침서를 분석하면서 그 활용법을 익히고, 참고 교재 개발 방법, 교안 작성법, 교수법, 학생평가방법 등을 강론하며, 마지막에는 실습 기회를 부여하여 교육 현장에 대한 적응력을 함양한다.");
//
//            m6MajorName.add("(필)윤리학");
//            m6MajorIntro.add("19세기 이전의 서양 윤리학에서 제기된 주요문제와 이 문제에 대한 여러 학설들 중에서 중요한 것을 유형에 따라 소개함으로써 고전적 윤리학의 기본개념들을 이해하도록 하는데 역점을 둔다. 먼저 윤리학의 기본 문제들을 제시하고 이 문제에 대한 아리스토텔레스, 스피노자, 칸트, 흄, 밀 등의 접근을 소개하면서 그들의 학설에 대해 간략하게 비판적으로 고찰할 것이다.");
//
//            m6MajorName.add("사회철학");
//            m6MajorIntro.add("사회현실의 인식문제를 둘러싸고 일어날 방법논쟁에 관한 사적인 검토를 거쳐서, 사회인식의 이론 정립에 있어서의 경험적, 분석적 방법의 타당성과 그 한계를 설명하며 인간의 사회적 존재의 구조를 현대철학의 제 이론을 통해 조명하여 인간의 공존재성의 특징을 규명한다.");
//
//            m6MajorName.add("인식론");
//            m6MajorIntro.add("인식론이란 앎의 철학적 근거가 무엇인지를 따져보는 철학분야이다. \"앎의 본성은 무엇인가?\", \"어떤 조건이 갖추어져야 앎이 성립하는가?\", \"안다는 것과 단순히 믿는다는 것은 어떻게 다른가?\" 등이 인식론의 주된 물음들이다. 이 과목은 이러한 물음에 대한 대표적인 철학적 논의들을 훑어보는 순서로 진행될 것이다.");
//
//            m6MajorName.add("한국불교철학");
//            m6MajorIntro.add("이 교과목은 불교 전반에 대한 기본적 소양을 갖춘 수강생을 대상으로 한국불교의 역사와 철학을 다룬다. 구체적으로 원효(元曉), 지눌(知訥), 의천(義天), 휴정(休靜), 보우(普愚) 등 대표적 고승들의 사상을 학습하고, 한국 불교의 대종인 선불교의 철학적 성찰을 시도한다.");
//
//            m6MajorName.add("서양근대철학");
//            m6MajorIntro.add("서양 근대의 주요 철학활동의 의의를 밝히고 철학사상의 큰 흐름을 따라 先哲을 追체험하며, 서양근세철학 문화가 현대문화에 미친 영향을 분별한다. 또한 서양근대철학이 제기했던 문제를 학생들 스스로 탐구해 봄으로써 이 문제들이 역사적인 것에 그치지 않고 오늘날의 우리에게도 철학적 과제로 다가올 수 있음을 체험하게 한다. 이성론(데카르트, 스피노자, 라이프니츠), 경험론(로크, 버클리, 흄), 비판철학(칸트), 독일이상주의(피히테, 셸링, 헤겔)의 형성배경과 중심내용 및 의미 천착이 이 교과목의 주요 부분을 구성한다.");
//
//            m6MajorName.add("서양중세철학");
//            m6MajorIntro.add("서양 기독교철학의 형성과 전개과정을 교부철학, 스콜라철학의 주요문헌들을 통해 개관하여 서양철학의 중세적 전통을 이해시키는 것이 이 강의의 목표이다. 희랍철학과의 만남을 통해 서양에 유입된 기독교가 어떤 새로운 문제에 부딪치며 이 문제들을 어떤 개념들을 통해 체계화해 가는지를 검토함으로써 서양철학의 중세적 토대를 이해하게 하고 아울러 서양 근대철학의 형성에 미친 중세의 영향을 이해하도록 한다.");
//
//            m6MajorName.add("언어철학");
//            m6MajorIntro.add("현대 언어철학의 주요 이론들과 논의들을 개괄한다. 언어가 어떻게 의미를 가지는가라는 문제와 관련하여, 현대 철학의 대표적 이론들인 사용 이론, 의도 이론, 검증주의 이론, 진리조건 의미론, 가능세계 의미론을 소개한다. 이들 이론을 소개하면서, 의미와 사용, 의미와 사고, 의미와 지시, 의미와 참간의 관계 문제, 그리고 의미론과 화용론의 구분 문제에 대해 논의한다. 또한 언어적 의미의 존재 자체를 부정하는 의미 회의론에 대해 검토한다.0");
//
//            m6MajorName.add("실존철학");
//            m6MajorIntro.add("이 강의에서는 실존철학의 대표적인 철학자들인 키르케고르, 니체, 야스퍼스, 하이데거, 사르트르, 카뮈 등의 사상을 살펴볼 것이다.");
//
//            m6MajorName.add("현대프랑스철학");
//            m6MajorIntro.add("20세기 프랑스철학사를 소개하는 과목이다. 과학철학(베르그손, 바슐라르, 시몽동), 실존주의(사르트르, 메를로퐁티, 레비나스), 구조주의(라캉, 알튀세르, 푸코), 탈구조주의(데리다, 들뢰즈, 바디우) 등으로 이어지는 현대프랑스철학의 흐름을 강의하고 탈구조주의와 포스트모더니즘(리오타르, 보드리야르)의 관련성을 설명한다. 강의의 초점은 푸코, 데리다, 들뢰즈 등과 같은 구조주의 전후의 철학자에 놓일 것이나 사정에 따라 달라질 수 있다.");
//
//            m6MajorName.add("현상학");
//            m6MajorIntro.add("본 교과목은 현대철학의 핵심적인 사조 중의 하나인 현상학의 근본 문제들을 검토함을 목표로 한다. 이러한 목표를 위해 우선 현상학의 창시자인 후설의 현상학을 중심으로 현대의 위기, 실증주의 비판, 엄밀학으로서의 현상학의 이념, 영역적 존재론, 형식적 존재론, 현상학적 심리학, 초월론적 현상학, 지향성, 초월론적 주관, 생활세계, 노에시스-노에마 상관관계, 현상학적 환원, 질적연구 등의 문제들을 살펴본 후 셸러, 하이데거, 인가르덴, 사르트르, 가다머, 메를로-퐁티, 레비나스, 리쾨르, 뒤프렌느, 슈츠, 거비치 등이 발전시킨 다양한 유형의 현상학의 근본 문제들을 살펴본다.");
//
//            m6MajorName.add("불교철학특강");
//            m6MajorIntro.add("인도 불교와 동아시아 불교를 폭넓게 조망하면서 인도와 동아시아에서 다양하게 전개된 불교철학을 주제별로 특화해서 공부한다. 불교의 철학적 사유 전통에 대해 통시적이고 공시적인 관점에서 접근한다. 학기에 따라 불교 인식론, 불교 윤리학 등의 다양한 주제가 소개될 것이다.");
//
//            m6MajorName.add("제자백가철학특강");
//            m6MajorIntro.add("제자백가는 다양한 철학적 문제들을 제기했지만, 이들은 동일한 철학적 배경을 공유하고 자신들의 문제의식을 전개해 나갔다. 이 수업에서는 이들이 공유한 철학적 배경, 제자백가들 자신의 주장, 그리고 그들 사이의 논쟁을 검토한다.");
//
//            m6MajorName.add("신유학특강");
//            m6MajorIntro.add("신유학의 철학적 의미를 탐구하고 그 특징을 파악해 본다. 이를 위해 한국과 중국의 대표적인 신유학자들의 저술을 읽고 토론한다.");
//
//            m6MajorName.add("중국근현대철학");
//            m6MajorIntro.add("이 교과목은 청대고증학과 그 이후 중국 현대 신유학에 이르기까지, 서양사상의 충격과 영향 하에서 중국철학의 자체문제를 19세기 이후 어떻게 주체적으로 형성하고, 전통철학의 정당성을 어떻게 발전시켜 왔는지를 살핀다. 특히 5.4운동 이후 문화보수주의 학자들의 전통사상 긍정론 주장에 주목하며, 중국 근대철학의 강점과 현실성을 파악한다.");
//
//            m6MajorName.add("송명대 신유학");
//            m6MajorIntro.add("이 교과목은 주희가 성리학을 종합한 과정을 검토하기 위해 주희 이전의 주돈이, 소옹, 장재, 정호, 정이 등의 사상을 검토하고, 주희 사상의 형성과 그 체계를 전체적으로 조망한다. 또한 주희 사상과 큰 차이를 보이는 명대 왕양명의 철학을 다룬다.");
//
//            m6MajorName.add("서양고중세철학특강");
//            m6MajorIntro.add("서양 철학의 고중세 시기에 어떤 문제들이 어떤 방식으로 제기되고 제기된 문제들의 해결을 위해 도입된 개념과 논변들이 어떤 것인지를 당시 작품의 강독을 통해 검토한다. 플라톤, 아리스토텔레스, 아우구스티누스, 토마스 아퀴나스의 문헌이 일차적인 선택 범위에 들며, 철학적 분석과 문헌학적 역사적 접근방법을 통해 학생들에게 서양 고중세의 철학적 문헌을 학적으로 접근하는 방식이 어떤 것인지를 이해하도록 한다.");
//
//            m6MajorName.add("서양현대철학특강");
//            m6MajorIntro.add("현대 서양의 주요한 철학사조들을 대표하는 고전적인 저작들에 대해서 강독한다. 이를 통해 현대 서양철학에 대한 이해를 심화하는 한편 철학서를 치밀하게 읽고 소화하는 능력을 함양한다.");
//
//            m6MajorName.add("과학철학");
//            m6MajorIntro.add("과학과 관련된 주된 철학적 주제들을 선택하여, 현대의 대표적 견해들을 소개하고 비판적으로 검토하는 방식으로 다룰 것이다. 선택 가능한 주제들로는, 과학의 목표와 방법, 과학 이론의 구성과 역할, 과학적 설명, 실재론/반실재론 논쟁, 이론간 환원, 과학의 합리성 및 객관성, 과학과 사이비 과학의 구분, 자연 법칙 등이 있다.");
//
//            m6MajorName.add("심리철학");
//            m6MajorIntro.add("'마음의 본성은 무엇인가'라는 것은 古代로부터 이어지는 중요한 철학적 문제 중의 하나이다. 이 과목에서는 '마음과 신체의 관계는 무엇인가'라는 존재론적인 문제와, '심리용어의 의미는 어떤 근거에서 가능한가'라는 의미론적인 문제, '자신의 마음과 他人의 마음은 어떻게 인식될 수 있는가'하는 인식론적인 문제 등이 다루어진다. 이 과목은 위의 주제에 관한 기초적인 내용들을 강의한 후 이 강의내용과 강의시간에 제시된 문헌의 내용을 중심으로 토론을 곁들이는 방식으로 진행된다.");
//
//            m6MajorName.add("사회철학특강");
//            m6MajorIntro.add("이 과목은 인간이 독립된 개체가 아니라 사회적 존재라는 전체를 바탕으로 인간의 자기 인식, 인간과 인간 간의 바람직한 관계 그리고 인간과 사회 변화과정의 상관관계를 탐구하는 것을 과제로 한다. 이러한 탐구를 위해 올바른 방법론을 모색하고 더 나아가서 인간 존재의 근본 양식에 대해 철학적으로 규명해 본다.");
//
//            m6MajorName.add("형이상학");
//            m6MajorIntro.add("이 과목은 세계 전체의 구조와 구조원리, 세계 내에서의 인간의 지위와 인생의 의미, 세계의 존재근거와 신의 존재여부, 세계와 신 그리고 인간의 상호관계와 같은 근본적인 문제들을 고찰한다. 수업은 각 주제들에 대한 강의 및 토론 그리고 각 주제와 관련된 고전들에 대한 강독으로 진행된다.");
//
//            m6MajorName.add("윤리학특강");
//            m6MajorIntro.add("이 과목에서는 규범윤리, 응용윤리, 메타윤리, 윤리학사 등 윤리학의 여러 탐구 영역에서 제기되는, 이론적이거나 실천적인 주요 주제나 특정한 문제들을 집중적으로 다룬다. 이에 따라 이 과목은 수강생들에게 현재 사회적으로 현안이 되는 실천적 문제들을 윤리학적 관점에서 규명하거나, 현재 학계에서 논의의 초점에 있는 윤리학적 주제들을 심층적으로 고찰하는 기회를 제공하게 될 것이다.");
//
//            m6MajorName.add("서양근대철학특강");
//            m6MajorIntro.add("데카르트로부터 발단하여 스피노자, 라이프니츠를 통해 전개된 서양 근대 초 유럽대륙의 이성주의와 베이컨, 홉스, 로크, 버클리, 흄으로 이어진 영국의 경험주의의 합류 지점에서 ‘이성 비판’이라는 방법을 통해 형성된 칸트의 철학을 중심에 두고, 피히테, 셸링, 헤겔의 독일이상주의 철학의 핵심적 주제를 함께 강론한다. 인간의 역사는 다름 아닌 인간의 ‘동물성’과 ‘이성성’의 갈등과 화해의 과정이고, 저러한 근대사상의 가닥들은 근원적으로는 ‘이성적 동물’인 인간의 이중성의 발로임을 성찰하면서, 그러한 문제상황에서 서양근대철학의 제학파가 인간 문화의 최고의 가치인 진(眞) ․ 선(善) ․ 미(美) ․ 성(聖) ․ 화(和)의 원리를 어떻게 해명하려 했는가를 고찰한다.");
//
//            m6MajorName.add("문화철학");
//            m6MajorIntro.add("이 강의에서는 문화에 대한 주요한 철학적 질문과 입장을 소개하고, 이를 통해 문화현상의 철학적 함축과 문화변동의 논리적 구조를 검토한다. 비코에서 헤겔에 이르는 근대 문화철학의 입장을 개괄하고, 구조주의 이후의 현대 프랑스철학과 프랑크푸르트학파의 문화비평, 정신분석 등의 현대 문화이론을 참고자료로 제시할 것이다. 세계화, 정보화 사회의 도래, 영상의 확산, 페미니즘과 생태문제 등 이 시대의 문화적 정체성을 결정하는 주요 현상을 토론하며, 궁극적으로는 동서문화를 포괄할 수 있는 철학적 입지점을 모색한다.");
//
//            m6MajorName.add("도가철학");
//            m6MajorIntro.add("노자의 <도덕경>과 장자의 <장자>를 선독하면서 도가의 주요 개념을 살펴보고 도가의 주요 이론들과 삶에 대한 태도를 배우게 될 것이다. 아울러 유가, 묵가, 명가 등 당대 다른 사상가들과의 비교 검토 역시 함께 진행할 것이다.");
//
//            m6MajorName.add("역사철학");
//            m6MajorIntro.add("역사가 철학적 관심의 대상이 된 것은 오래이나, 특히 근대 이후 역사가 고유한 의미에서 철학적 관심의 대상이 되며 동시에 철학은 역사화 된다. 이러한 인식에 입각하여 이 교과목은 역사에 대한 철학적 접근유형을 체계적으로 고찰하고 역사를 파악하는 다양한 방법론의 특성을 탐구함으로써 인간 존재와 세계의 근본적인 역사적 성격을 해명한다.");
//
//            randomTimeLectureRoom(c1m6Professor, c1Major.get(5), c1LectureRoom, m6MajorName, m6MajorIntro);
//
//        }
//
//
////        // 이거 안씀
////        public void dbInit4() {
////            String randomSalt = hashComponent.getRandomSalt();
////            String hash = hashComponent.getHash("1234", randomSalt);
////
////            // 날짜 형식 지정
////            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
////
////            // 2024년 2월 25일로 설정
////            LocalDate createdAt = LocalDate.parse("2024-02-25", formatter);
////            java.sql.Date sqlCreatedAt = java.sql.Date.valueOf(createdAt);
////
////            College college1 = new College("사범대학", "인문관1");
////            College college2 = new College("사회과학대학", "인문관2");
////            College college3 = new College("의과대학", "공학관");
////            em.persist(college1);
////            em.persist(college2);
////            em.persist(college3);
////
////            Major major1 = new Major("정치외교학과", 3500000, college1);
////            Major major2 = new Major("지리학과", 3200000, college1);
////            Major major3 = new Major("군사학과", 3300000, college1);
////            em.persist(major1);
////            em.persist(major2);
////            em.persist(major3);
////
////            Major major4 = new Major("수학교육과", 2900000, college2);
////            Major major5 = new Major("사회교육과", 2800000, college2);
////            Major major6 = new Major("국어교육과", 3000000, college2);
////            em.persist(major4);
////            em.persist(major5);
////            em.persist(major6);
////
////            Major major7 = new Major("신경과", 5500000, college3);
////            Major major9 = new Major("정신과", 5700000, college3);
////            Major major8 = new Major("마취과", 5200000, college3);
////            em.persist(major7);
////            em.persist(major8);
////            em.persist(major9);
////
////            LectureRoom lectureRoom1 = new LectureRoom("101", college1);
////            LectureRoom lectureRoom2 = new LectureRoom("102", college1);
////            LectureRoom lectureRoom3 = new LectureRoom("201", college2);
////            LectureRoom lectureRoom4 = new LectureRoom("202", college2);
////            LectureRoom lectureRoom5 = new LectureRoom("501", college3);
////            LectureRoom lectureRoom6 = new LectureRoom("502", college3);
////
////            em.persist(lectureRoom1);
////            em.persist(lectureRoom2);
////            em.persist(lectureRoom3);
////            em.persist(lectureRoom4);
////            em.persist(lectureRoom5);
////            em.persist(lectureRoom6);
////
////
////            User user1 = new User(hash, randomSalt, "정수용", "222222-2222222", "부산광역시 미남", "010-1234-1234", "tesrht2@naver.com", User_role.교수, sqlCreatedAt);
////            User user2 = new User(hash, randomSalt, "최유란", "111111-1111111", "울산광역시 남구", "010-1234-1234", "tesagat1@naver.com", User_role.교수, sqlCreatedAt);
////            User user3 = new User(hash, randomSalt, "이병길", "333333-3333333", "부산광역시 대연동", "010-1234-1234", "ahtest3@naver.com", User_role.교수, sqlCreatedAt);
////            em.persist(user1);
////            em.persist(user2);
////            em.persist(user3);
////
////            User user5 = new User(hash, randomSalt, "황민우", "555555-5555555", "서울특별시 강북", "010-1234-1234", "teulahst2@naver.com", User_role.교직원, sqlCreatedAt);
////            User user6 = new User(hash, randomSalt, "박소은", "666666-6666666", "부산광역시 수영구", "010-1234-1234", "teulhst3@naver.com", User_role.교직원, sqlCreatedAt);
////            User user4 = new User(hash, randomSalt, "안지혜", "444444-4444444", "서울특별시 강남", "010-1234-1234", "testuil1@naver.com", User_role.교직원, sqlCreatedAt);
////            em.persist(user4);
////            em.persist(user5);
////            em.persist(user6);
////
////            User user7 = new User(hash, randomSalt, "송근욱", "777777-7777777", "울산광역시 남구", "010-1234-1234", "tesryidt1@naver.com", User_role.학생, sqlCreatedAt);
////            User user8 = new User(hash, randomSalt, "이순신", "888888-8888888", "부산광역시 미남", "010-1234-1234", "tedlfyist2@naver.com", User_role.학생, sqlCreatedAt);
////            User user9 = new User(hash, randomSalt, "안중근", "999999-9999999", "부산광역시 대연동", "010-1234-1234", "tesilfyilt3@naver.com", User_role.학생, sqlCreatedAt);
////            em.persist(user7);
////            em.persist(user8);
////            em.persist(user9);
////
////
////            try {
////                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
////                Date utilDate = sdf.parse("2024/11/02");
////                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
////
////                Professor professor1 = new Professor("hi", user1, major1, sqlDate);
////                Professor professor2 = new Professor("hi", user2, major2, sqlDate);
////                Professor professor3 = new Professor("hi", user3, major3, sqlDate);
////                em.persist(professor1);
////                em.persist(professor2);
////                em.persist(professor3);
////
////                Manager manager1 = new Manager("hi", user4, sqlDate);
////                Manager manager2 = new Manager("hi", user5, sqlDate);
////                Manager manager3 = new Manager("hi", user6, sqlDate);
////                em.persist(manager1);
////                em.persist(manager2);
////                em.persist(manager3);
////
////                Student student1 = new Student(24000001, 3, user7, professor1, major1, sqlDate);
////                Student student2 = new Student(24000002, 3, user8, professor2, major2, sqlDate);
////                Student student3 = new Student(24000003, 3, user9, professor3, major3, sqlDate);
////                em.persist(student1);
////                em.persist(student2);
////                em.persist(student3);
////
////                Situation situation1 = new Situation(student1, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
////                Situation situation2 = new Situation(student2, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
////                Situation situation3 = new Situation(student3, Status_type.재학, new java.sql.Date(new Date().getTime()), null);
////
////                em.persist(situation1);
////                em.persist(situation2);
////                em.persist(situation3);
////
////                SituationRecord record1 = new SituationRecord(Status_type.재학, student1, situation1.getStart_date(), null);
////                SituationRecord record2 = new SituationRecord(Status_type.재학, student2, situation2.getStart_date(), null);
////                SituationRecord record3 = new SituationRecord(Status_type.재학, student3, situation3.getStart_date(), null);
////
////                em.persist(record1);
////                em.persist(record2);
////                em.persist(record3);
////
////                Payments payments = new Payments(student1, "2023학년 1학기");
////                Payments payments2 = new Payments(student2, "2023학년 1학기");
////                Payments payments3 = new Payments(student3, "2023학년 1학기");
////
////                Payments payments4 = new Payments(student1, "2023학년 2학기");
////                Payments payments5 = new Payments(student2, "2023학년 2학기");
////                Payments payments6 = new Payments(student3, "2023학년 2학기");
////
////                Payments payments7 = new Payments(student1, "2024학년 1학기");
////                Payments payments8 = new Payments(student2, "2024학년 1학기");
////                Payments payments9 = new Payments(student3, "2024학년 1학기");
////
////
////                em.persist(payments);
////                em.persist(payments2);
////                em.persist(payments3);
////                em.persist(payments4);
////                em.persist(payments5);
////                em.persist(payments6);
////                em.persist(payments7);
////                em.persist(payments8);
////                em.persist(payments9);
////
////
////                Scholarship scholarship1 = new Scholarship("내부", "성적우수장학금", 1000000, 2023, 1);
////                em.persist(scholarship1);
////                Scholarship scholarship2 = new Scholarship("내부", "근로장학금", 2000000, 2023, 2);
////                em.persist(scholarship2);
////                Scholarship scholarship3 = new Scholarship("외부", "국가장학금", 300000, 2023, 3);
////                em.persist(scholarship3);
////                Scholarship scholarship4 = new Scholarship("내부", "성적우수장학금", 1000000, 2024, 1);
////                em.persist(scholarship4);
////                Scholarship scholarship5 = new Scholarship("내부", "근로장학금", 2000000, 2024, 2);
////                em.persist(scholarship5);
////                Scholarship scholarship6 = new Scholarship("외부", "국가장학금", 300000, 2024, 3);
////                em.persist(scholarship6);
////
////
////                Scholarship_Award scholarshipAward1 = new Scholarship_Award(student1, scholarship1);
////                Scholarship_Award scholarshipAward2 = new Scholarship_Award(student1, scholarship2);
////                Scholarship_Award scholarshipAward3 = new Scholarship_Award(student1, scholarship3);
////                Scholarship_Award scholarshipAward4 = new Scholarship_Award(student2, scholarship1);
////                Scholarship_Award scholarshipAward5 = new Scholarship_Award(student2, scholarship2);
////                Scholarship_Award scholarshipAward6 = new Scholarship_Award(student3, scholarship1);
////                Scholarship_Award scholarshipAward7 = new Scholarship_Award(student1, scholarship4);
////                Scholarship_Award scholarshipAward8 = new Scholarship_Award(student1, scholarship5);
////                Scholarship_Award scholarshipAward9 = new Scholarship_Award(student1, scholarship6);
////                Scholarship_Award scholarshipAward10 = new Scholarship_Award(student2, scholarship4);
////                Scholarship_Award scholarshipAward11 = new Scholarship_Award(student2, scholarship5);
////                Scholarship_Award scholarshipAward12 = new Scholarship_Award(student3, scholarship6);
////
////                em.persist(scholarshipAward1);
////                em.persist(scholarshipAward2);
////                em.persist(scholarshipAward3);
////                em.persist(scholarshipAward4);
////                em.persist(scholarshipAward5);
////                em.persist(scholarshipAward6);
////                em.persist(scholarshipAward7);
////                em.persist(scholarshipAward8);
////                em.persist(scholarshipAward9);
////                em.persist(scholarshipAward10);
////                em.persist(scholarshipAward11);
////                em.persist(scholarshipAward12);
////
////
////            } catch (ParseException e) {
////                throw new RuntimeException(e);
////            }
////
////        }
//    }
//}
