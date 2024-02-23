package com.itbank.simpleboard.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class ResponseDto {
    private Page<ProfessorLectureDto> lectureDtoList;
    private int start;
    private int end;
    private int num;
    private int maxPage;
    private LectureSearchConditionDto condition;
}
