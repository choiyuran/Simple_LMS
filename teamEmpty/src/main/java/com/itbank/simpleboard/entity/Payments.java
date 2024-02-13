package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Payments { // 등록금
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payments_idx")
    private Long idx;

    @Column(name = "payments_flag")
    @Enumerated(EnumType.STRING)
    private YesOrNo flag;

    @Column(name = "payments_date")
    private Date date;

    @PrePersist
    public void prePersist() {
        long millis = System.currentTimeMillis();
        this.date = new Date(millis);
        this.flag = YesOrNo.N;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_idx")
    private Student student;

    @Column(name = "payments_semester")
    private String semester;

    public Payments(Student student, String semester) {
        this.student = student;
        this.semester = semester;
    }
}
