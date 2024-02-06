package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.Professor;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepositoryCustom {
    List<ProfessorLectureDto> getLectureDtoList(LectureSearchConditionDto condition);

    ProfessorLectureDto getLectureDto(Long idx);

    List<ProfessorUserDto> getProfessorNamesByMajor(Long majorIdx);

    List<EvaluateFormDto> getMyEvaluation(Long idx);

    List<EnrollmentDto> getEnrollmentList(Long lectureIdx);


    List<Professor> findByUserUser_nameContaining(String professorName);

    Optional<List<Professor>> findByMajorAndUserUserNameContaining(Long major, String professorName);
}
