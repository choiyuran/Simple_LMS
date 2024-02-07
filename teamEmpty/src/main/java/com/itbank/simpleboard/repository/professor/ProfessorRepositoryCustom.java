package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.*;
import com.querydsl.core.Tuple;

import java.util.List;

public interface ProfessorRepositoryCustom {
    List<ProfessorLectureDto> getLectureDtoList(LectureSearchConditionDto condition);

    ProfessorLectureDto getLectureDto(Long idx);

    List<ProfessorUserDto> getProfessorNamesByMajor(Long majorIdx);

    List<EvaluateFormDto> getMyEvaluation(Long idx);

    List<EnrollmentDto> getEnrollmentList(Long lectureIdx);


    List<Tuple> findByMajorAndUserUserNameContaining(String majorName, String professorName);
}
