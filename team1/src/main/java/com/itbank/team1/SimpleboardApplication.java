package com.itbank.team1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 자동관리
public class SimpleboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleboardApplication.class, args);
    }

}
