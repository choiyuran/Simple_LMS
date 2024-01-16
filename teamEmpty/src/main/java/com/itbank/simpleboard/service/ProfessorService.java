package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.ProfessorLectureDto;
import com.itbank.simpleboard.dto.LectureSearchConditionDto;
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

    public List<ProfessorLectureDto> getLectureDtoList(LectureSearchConditionDto condition) {
        return professorRepository.getLectureDtoList(condition);
    }

    public List<ProfessorLectureDto> getMyLectureDtoList(LectureSearchConditionDto condition, Long professorIdx) {
        return professorRepository.getMyLectureDtoList(condition, professorIdx);
    }
}
