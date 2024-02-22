package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.SituationStuDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SituationRepositoryCustom {
    Page<SituationStuDto> findAllSituationStu(String status, Pageable pageable);

    SituationStuDto findOneSituation(Long idx);
}
