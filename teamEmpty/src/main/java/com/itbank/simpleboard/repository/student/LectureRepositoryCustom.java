package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.LectureDto;
import com.itbank.simpleboard.dto.LectureListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LectureRepositoryCustom {
    Page<LectureDto> getLectureListDtos(String searchType, String keyword, Pageable pageable);

    Page<LectureDto> getLectureDtos(Pageable pageable);
}
