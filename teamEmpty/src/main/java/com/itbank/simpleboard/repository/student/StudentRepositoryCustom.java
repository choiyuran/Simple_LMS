package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.GradeLectureDto;
import com.itbank.simpleboard.dto.GradeSearchConditionDto;
import com.itbank.simpleboard.dto.ProfessorLectureDto;

import java.util.List;

public interface StudentRepositoryCustom {
    List<GradeLectureDto> getLectureDtoList(GradeSearchConditionDto condition);
}
