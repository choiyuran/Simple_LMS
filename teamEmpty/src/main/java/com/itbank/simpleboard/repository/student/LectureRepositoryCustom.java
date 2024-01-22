package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.dto.LectureListDto;

import java.util.List;

public interface LectureRepositoryCustom {
    List<LectureDto> getLectureListDtos(String searchType, String keyword);
}
