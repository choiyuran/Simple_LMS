package com.itbank.simpleboard.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="lecture_room")
@Entity
public class LectureRoom {
    // 강의실 번호
    @Id
    @Column(name="lectureroom_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // 강의실_호실
    @Column(name="class_room")
    private Integer room;

    // 단과대학 번호를 외래키로 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="college_idx")
    private College college;

    public LectureRoom(Integer room, College college) {
        this.room = room;
        this.college = college;
    }
}
