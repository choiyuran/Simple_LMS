package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.LectureListDto;
import com.itbank.simpleboard.dto.LectureSearchCondition;

import java.util.List;

public interface ProfessorRepositoryCustom {
    List<LectureListDto> getLectureListDto(LectureSearchCondition condition);
}
