package com.itbank.simpleboard.repository.professor;

import com.itbank.simpleboard.dto.*;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface ProfessorRepositoryCustom {
    Page<ProfessorLectureDto> getLectureDtoList(LectureSearchConditionDto condition, Pageable pageable);

    List<String> getMajorNameList(LectureSearchConditionDto condition);

    List<Integer> getGradeList(LectureSearchConditionDto condition);

    ProfessorLectureDto getLectureDto(Long idx);

    List<ProfessorUserDto> getProfessorNamesByMajor(Long majorIdx);

    List<EvaluateFormDto> viewEvaluation(Long idx);

    List<EnrollmentDto> getEnrollmentList(Long lectureIdx);

    Page<ProfessorListDto> searchByMajorAndProfessorAndLeave(HashMap<String, Object> map, Pageable pageable);

    ProfessorListDto selectOneProfessor(Long idx);

    List<Tuple> findByMajorAndUserUserNameContaining(String majorName, String professorName);
    List<ProfessorUserDto> findByMajorAndUserUserNameContainingDto(String majorName, String professorName);
}
