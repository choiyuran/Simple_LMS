package com.itbank.simpleboard.dto;

import com.itbank.simpleboard.entity.Lecture_Type;
import com.itbank.simpleboard.entity.YesOrNo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class RegisterlectureDto {
    private Long idx;
    private String name;
    private String intro;
    private Integer credit;
    private String[] day;
    private String[] start;
    private String[] end;
    private Lecture_Type type;
    private Integer max_count;
    private Integer current_count;
    private String semester;
    private Integer grade;
    private Long professor_idx;
    private Long major_idx;
    private Long lectureRoom_idx;
    private YesOrNo visible;
    private String plan;

    public RegisterlectureDto(String name, String intro, Integer credit, String[] day, String[] start, String[] end, Lecture_Type type, Integer max_count, Integer current_count, String semester, Integer grade, Long professor_idx, Long major_idx, Long lectureRoom_idx, YesOrNo visible, String plan) {
        this.name = name;
        this.intro = intro;
        this.credit = credit;
        this.day = day;
        this.start = start;
        this.end = end;
        this.type = type;
        this.max_count = max_count;
        this.current_count = current_count;
        this.semester = semester;
        this.grade = grade;
        this.professor_idx = professor_idx;
        this.major_idx = major_idx;
        this.lectureRoom_idx = lectureRoom_idx;
        this.visible = YesOrNo.N;
        this.plan = null;
    }
}
