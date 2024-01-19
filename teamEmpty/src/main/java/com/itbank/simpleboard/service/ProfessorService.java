package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.ProfessorLectureDto;
import com.itbank.simpleboard.dto.LectureSearchConditionDto;
import com.itbank.simpleboard.dto.ProfessorUserDto;
import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.repository.manager.MajorRepository;
import com.itbank.simpleboard.repository.professor.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final MajorRepository majorRepository;

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

}
