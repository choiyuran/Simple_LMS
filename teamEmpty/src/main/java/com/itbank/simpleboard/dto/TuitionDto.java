package com.itbank.simpleboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

    @Data
    @NoArgsConstructor
    public class TuitionDto {

        // 전공 이름
        private String name;

        // 학번
        private Integer student_num;

        // 납부일자
        private Date date;

        // 등록금
        private Integer tuition;

        // 장학금 금액
        private Integer price;

        @QueryProjection
        public TuitionDto(String name, Integer student_num, Date date, Integer tuition, Integer price) {
            this.name = name;
            this.student_num = student_num;
            this.date = date;
            this.tuition = tuition;
            this.price = price;
        }
    }

