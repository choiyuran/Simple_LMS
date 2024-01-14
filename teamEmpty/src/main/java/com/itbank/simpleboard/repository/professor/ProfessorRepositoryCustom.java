package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.dto.LectureSearchCondition;

import java.util.List;

public interface ProfessorRepositoryCustom {
    List<LectureDto> getLectureDtoList(LectureSearchCondition condition);
}
