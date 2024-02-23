package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.*;

import com.itbank.simpleboard.dto.StudentListDto;
import com.itbank.simpleboard.dto.TuitionDto;

import java.util.HashMap;
import java.util.List;

public interface StudentRepositoryCustom {
    List<GradeLectureDto> getLectureDtoList(GradeSearchConditionDto condition);

    List<TuitionDto> getTuitionData(Long idx);

    List<StudentListDto> selectAllStudent(HashMap<String, Object> map);

    StudentListDto selectOndeStudent(Long idx);

    OverallGradeDto findOverallGrade(Long stuIdx);

    List<StudentLectureDto> getStudentLectureDto(LectureSearchConditionDto condition);
}