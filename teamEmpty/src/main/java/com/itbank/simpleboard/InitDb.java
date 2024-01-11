package com.itbank.simpleboard;

import com.itbank.simpleboard.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit6();
        initService.dbInit4();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit4() {
            User user1 = new User("professor1", "1234", "1234", "교수1", "111111-1111111", "울산광역시 남구", "010-1234-1234", "test1@naver.com", User_role.교수);
            User user2 = new User("professor2", "1234", "1234", "교수2", "222222-2222222", "부산광역시 미남", "010-1234-1234", "test2@naver.com", User_role.교수);
            User user3 = new User("professor3", "1234", "1234", "교수3", "333333-3333333", "부산광역시 대연동", "010-1234-1234", "test3@naver.com", User_role.교수);
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);

            User user4 = new User("manager1", "1234", "1234", "교직원1", "444444-4444444", "서울특별시 강남", "010-1234-1234", "test1@naver.com", User_role.교직원);
            User user5 = new User("manager2", "1234", "1234", "교직원2", "555555-5555555", "서울특별시 강북", "010-1234-1234", "test2@naver.com", User_role.교직원);
            User user6 = new User("manager3", "1234", "1234", "교직원3", "666666-6666666", "부산광역시 수영구", "010-1234-1234", "test3@naver.com", User_role.교직원);
            em.persist(user4);
            em.persist(user5);
            em.persist(user6);

            User user7 = new User("24000001", "1234", "1234", "학생1", "777777-7777777", "울산광역시 남구", "010-1234-1234", "test1@naver.com", User_role.학생);
            User user8 = new User("24000002", "1234", "1234", "학생2", "888888-8888888", "부산광역시 미남", "010-1234-1234", "test2@naver.com", User_role.학생);
            User user9 = new User("24000003", "1234", "1234", "학생3", "999999-9999999", "부산광역시 대연동", "010-1234-1234", "test3@naver.com", User_role.학생);
            em.persist(user7);
            em.persist(user8);
            em.persist(user9);

            Major major1 = em.find(Major.class, 1L);
            Major major2 = em.find(Major.class, 2L);
            Major major3 = em.find(Major.class, 3L);

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date utilDate = sdf.parse("2024/11/02");
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                Professor professor1 = new Professor("hi", user1, major1, sqlDate);
                Professor professor2 = new Professor("hi", user2, major2, sqlDate);
                Professor professor3 = new Professor("hi", user2, major3, sqlDate);
                em.persist(professor1);
                em.persist(professor2);
                em.persist(professor3);

                Manager manager1 = new Manager("hi", user4, sqlDate);
                Manager manager2 = new Manager("hi", user5, sqlDate);
                Manager manager3 = new Manager("hi", user6, sqlDate);
                em.persist(manager1);
                em.persist(manager2);
                em.persist(manager3);

                Student student1 = new Student(24000001, 1, user7, professor1, major1, sqlDate);
                Student student2 = new Student(24000002, 2, user8, professor2, major2, sqlDate);
                Student student3 = new Student(24000003, 3, user9, professor3, major3, sqlDate);
                em.persist(student1);
                em.persist(student2);
                em.persist(student3);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
        public void dbInit6() {
            College college1 = new College("사범대학","인문관1");
            College college2 = new College("사회과학대학","인문관2");
            College college3 = new College("의과대학","공학관");
            em.persist(college1);
            em.persist(college2);
            em.persist(college3);

            Major major1 = new Major("정치외교학과",3500000,college1);
            Major major2 = new Major("지리학과",3200000,college1);
            Major major3 = new Major("군사학과",3300000,college1);
            em.persist(major1);
            em.persist(major2);
            em.persist(major3);

            Major major4 = new Major("수학교육과",2900000,college2);
            Major major5 = new Major("사회교육과",2800000,college2);
            Major major6 = new Major("국어교육과",3000000,college2);
            em.persist(major4);
            em.persist(major5);
            em.persist(major6);

            Major major7 = new Major("신경과",5500000,college2);
            Major major9 = new Major("정신과",5700000,college2);
            Major major8 = new Major("마취과",5200000,college2);
            em.persist(major7);
            em.persist(major8);
            em.persist(major9);
        }
    }
}
