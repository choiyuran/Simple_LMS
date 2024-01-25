package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.EvaluateFormDto;
import com.itbank.simpleboard.dto.LectureSearchConditionDto;
import com.itbank.simpleboard.dto.ProfessorLectureDto;
import com.itbank.simpleboard.dto.ProfessorUserDto;

import java.util.List;

public interface ProfessorRepositoryCustom {
    List<ProfessorLectureDto> getLectureDtoList(LectureSearchConditionDto condition);

    ProfessorLectureDto getLectureDto(Long idx);

    List<ProfessorUserDto> getProfessorNamesByMajor(Long majorIdx);

    List<EvaluateFormDto> getMyEvaluation(Long idx);
}
