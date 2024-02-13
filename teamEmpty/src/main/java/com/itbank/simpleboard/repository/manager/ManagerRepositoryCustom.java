package com.itbank.simpleboard.repository.manager;

import com.itbank.simpleboard.dto.CheckTuitionPaymentDto;
import com.itbank.simpleboard.dto.ManagerDTO;
import com.itbank.simpleboard.entity.Manager;

import java.util.List;

public interface ManagerRepositoryCustom {
    List<ManagerDTO> findBySearchType(String searchType, String searchValue);

}
