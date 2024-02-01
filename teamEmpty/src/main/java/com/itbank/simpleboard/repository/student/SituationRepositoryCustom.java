package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.SituationStuDto;

import java.util.List;

public interface SituationRepositoryCustom {
    List<SituationStuDto>  findAllSituationStu(String status);

    SituationStuDto findOneSituation(Long idx);
}
