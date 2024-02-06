package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.*;

import java.util.List;

public interface ProfessorRepositoryCustom {
    List<ProfessorLectureDto> getLectureDtoList(LectureSearchConditionDto condition);

    ProfessorLectureDto getLectureDto(Long idx);

    List<ProfessorUserDto> getProfessorNamesByMajor(Long majorIdx);

    List<EvaluateFormDto> getMyEvaluation(Long idx);

    List<EnrollmentDto> getEnrollmentList(Long lectureIdx);

    List<ProfessorListDto> selectAll();

    List<ProfessorListDto> findAllByMajorAndProfessor(Long majorIdx, String name);
}
