package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.*;
import com.querydsl.core.Tuple;

import java.util.HashMap;
import java.util.List;

public interface ProfessorRepositoryCustom {
    List<ProfessorLectureDto> getLectureDtoList(LectureSearchConditionDto condition);

    ProfessorLectureDto getLectureDto(Long idx);

    List<ProfessorUserDto> getProfessorNamesByMajor(Long majorIdx);

    List<EvaluateFormDto> viewEvaluation(Long idx);

    List<EnrollmentDto> getEnrollmentList(Long lectureIdx);

    List<ProfessorListDto> searchByMajorAndProfessorAndLeave(HashMap<String, Object> map);

    ProfessorListDto selectOneProfessor(Long idx);

    List<Tuple> findByMajorAndUserUserNameContaining(String majorName, String professorName);
}
