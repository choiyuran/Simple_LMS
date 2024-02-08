package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.StudentDto;

public interface sudentRepositoryCustom {
    StudentDto findDtoById(Long studentIdx);
}
