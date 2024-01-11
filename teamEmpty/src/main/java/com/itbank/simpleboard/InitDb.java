package com.itbank.simpleboard;

import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.LectureRoom;
import com.itbank.simpleboard.entity.Major;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit1() {
            
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

            LectureRoom lectureRoom1 = new LectureRoom(101,college1);
            LectureRoom lectureRoom2 = new LectureRoom(102,college1);
            LectureRoom lectureRoom3 = new LectureRoom(201,college2);
            LectureRoom lectureRoom4 = new LectureRoom(202,college2);
            LectureRoom lectureRoom5 = new LectureRoom(501,college3);
            LectureRoom lectureRoom6 = new LectureRoom(502,college3);

            em.persist(lectureRoom1);
            em.persist(lectureRoom2);
            em.persist(lectureRoom3);
            em.persist(lectureRoom4);
            em.persist(lectureRoom5);
            em.persist(lectureRoom6);
        }

    }
}
