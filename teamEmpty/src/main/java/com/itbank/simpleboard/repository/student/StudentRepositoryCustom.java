package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.TuitionDto;

import java.util.List;

public interface StudentRepositoryCustom {

    public List<TuitionDto> getTuitionData(Long idx);
}
