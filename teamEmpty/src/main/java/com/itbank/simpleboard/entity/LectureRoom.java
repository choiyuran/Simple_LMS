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


}
