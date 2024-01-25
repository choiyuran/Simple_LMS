package com.itbank.simpleboard.dto;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import com.itbank.simpleboard.entity.*;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LectureDto {
    private Long idx;
    private String name;
    private String intro;
    private Integer credit;
    private String day;
    private String start;
    private String end;
    private String type;
    private Integer maxCount;
    private Integer currentCount;
    private String semester;
    private Integer grade;

    private Long professor;
    private String plan;
    private String visible;
    private String professor_name;
    private String abolition;

    @QueryProjection
    public LectureDto(Long idx, String name, String intro, Integer credit, String day, String start, String end, String type, Integer maxCount, Integer currentCount, String semester, Integer grade, Long professor, String professor_name) {
        this(idx,name, intro, credit, day, start, end, type, maxCount, currentCount, semester, grade, professor, null, professor_name);
    }

    @QueryProjection
    public LectureDto(Long idx, String name, String intro, Integer credit, String day, String start, String end, String type, Integer maxCount, Integer currentCount, String semester, Integer grade, Long professor, String plan, String professor_name) {
        this.idx = idx;
        this.name = name;
        this.intro = intro;
        this.credit = credit;
        this.day = day;
        this.start = start;
        this.end = end;
        this.type = type;
        this.maxCount = maxCount;
        this.currentCount = currentCount;
        this.semester = semester;
        this.grade = grade;
        this.professor = professor;
        this.plan = plan;
        this.visible = YesOrNo.N.toString();
        this.abolition = YesOrNo.N.toString();
        this.professor_name = professor_name;
    }
}
