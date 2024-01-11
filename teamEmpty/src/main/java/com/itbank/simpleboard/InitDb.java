package com.itbank.simpleboard;

import com.itbank.simpleboard.entity.AcademicCalendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init(){

        try {
            initService.insertCalendar();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;


        public void insertCalendar() throws Exception{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            Date startDate1 = sdf.parse("2024/01/01");
            Date endDate1 = sdf.parse("2024/01/31");

            AcademicCalendar calendar1 = new AcademicCalendar("학기 시작", startDate1, endDate1);
            em.persist(calendar1);

            Date startDate2 = sdf.parse("2024/06/01");
            Date endDate2 = sdf.parse("2024/06/30");

            AcademicCalendar calendar2 = new AcademicCalendar("여름 방학 시작", startDate2, endDate2);
            em.persist(calendar2);

            Date startDate3 = sdf.parse("2024/12/01");
            Date endDate3 = sdf.parse("2024/12/31");

            AcademicCalendar calendar3 = new AcademicCalendar("겨울 방학 시작", startDate3, endDate3);
            em.persist(calendar3);
        }
    }
}
