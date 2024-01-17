package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.ProfessorLectureDto;
import com.itbank.simpleboard.dto.LectureSearchConditionDto;

import java.util.List;

public interface ProfessorRepositoryCustom {
    List<ProfessorLectureDto> getLectureDtoList(LectureSearchConditionDto condition);
}
