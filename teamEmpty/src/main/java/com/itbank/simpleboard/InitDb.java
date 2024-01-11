package com.itbank.simpleboard;

import com.itbank.simpleboard.entity.Scholarship;
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
        initService.dbInit3();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit3() {
            Scholarship scholarship1 = new Scholarship("내부", "성적우수장학금",1000000,2024,1);
            em.persist(scholarship1);
            Scholarship scholarship2 = new Scholarship("내부", "근로장학금",2000000,2024,2);
            em.persist(scholarship2);
            Scholarship scholarship3 = new Scholarship("외부", "국가장학금",300000,2024,3);
            em.persist(scholarship3);
        }
    }
}
