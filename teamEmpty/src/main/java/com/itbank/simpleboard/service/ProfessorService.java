package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.LectureListDto;
import com.itbank.simpleboard.dto.LectureSearchCondition;
import com.itbank.simpleboard.repository.professor.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public List<LectureListDto> getLectureListDto(LectureSearchCondition condition) {
        return professorRepository.getLectureListDto(condition);
    }
}
