package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.ProfessorLectureDto;
import com.itbank.simpleboard.dto.LectureSearchCondition;
import com.itbank.simpleboard.dto.ProfessorUserDto;
import com.itbank.simpleboard.entity.Professor;

import java.util.List;
import java.util.function.Supplier;

public interface ProfessorRepositoryCustom {
    List<ProfessorLectureDto> getLectureDtoList(LectureSearchCondition condition);

    List<ProfessorUserDto> getProfessorNamesByMajor(Long majorIdx);
}
