package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.dto.ScholarshipDto;
import com.itbank.simpleboard.entity.Scholarship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScholarshipRepositoryCustom {
    Page<ScholarshipDto> findAllScholarship(Pageable pageable, ScholarshipDto dto);

}
