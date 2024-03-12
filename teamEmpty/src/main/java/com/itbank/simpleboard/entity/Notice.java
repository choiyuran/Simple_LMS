package com.itbank.simpleboard.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notice_idx")
    private Long idx;

    @Column(name="notice_title")
    private String title;

    @Column(name="notice_content")
    @Lob
    private String content;

    @Column(name = "notice_date")
    private Date wdate;

    private Integer viewCount;

    @PrePersist
    public void prePersist() {
        long current = System.currentTimeMillis();
        this.wdate = new Date(current);
        this.viewCount = 0;
    }

    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
