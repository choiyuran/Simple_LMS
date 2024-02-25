package com.itbank.simpleboard.repository.manager;

import com.itbank.simpleboard.dto.CheckTuitionPaymentDto;
import com.itbank.simpleboard.dto.CheckTutionPaymentConditionDto;
import com.itbank.simpleboard.dto.EvaluateFormDto;
import com.itbank.simpleboard.dto.ManagerDTO;
import com.itbank.simpleboard.entity.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface ManagerRepositoryCustom {
    Page<ManagerDTO> findBySearchType(HashMap<String, Object> map, Pageable pageable);

    ManagerDTO selectOneManager(Long idx);

    List<CheckTuitionPaymentDto> findAllCheckTuitionPayments(CheckTutionPaymentConditionDto conditions);

    List<EvaluateFormDto> viewEvaluation(Long idx);
}
