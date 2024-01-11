package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.lectureListDto;

import java.util.List;

public interface ProfessorRepositoryCustom {
    List<lectureListDto> getLectureListDto();
}
