package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.Professor;
import com.itbank.simpleboard.repository.professor.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public List<ProfessorLectureDto> getLectureDtoList(LectureSearchConditionDto condition) {
        return professorRepository.getLectureDtoList(condition);
    }

    public ProfessorLectureDto getLectureDto(Long idx) {
        return professorRepository.getLectureDto(idx);
    }

    public List<ProfessorUserDto> getProfessorsByMajor(Long majorIdx) {
        List<ProfessorUserDto> professors = professorRepository.getProfessorNamesByMajor(majorIdx);
        return professors;
    }

    public Professor getProfessorByIdx(Long professorIdx) {
        Optional<Professor> professor = professorRepository.findById(professorIdx);
        return professor.orElse(null);
    }

    public List<EvaluateFormDto> getEvaluation(Long idx) {
        return professorRepository.getMyEvaluation(idx);
    }

    public List<EnrollmentDto> getEnrollmentList(Long lectureIdx) {
        return professorRepository.getEnrollmentList(lectureIdx);
    }

}
