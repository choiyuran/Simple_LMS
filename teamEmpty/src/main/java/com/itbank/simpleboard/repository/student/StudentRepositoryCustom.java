package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.*;

import com.itbank.simpleboard.dto.StudentListDto;
import com.itbank.simpleboard.dto.TuitionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface StudentRepositoryCustom {
    List<GradeLectureDto> getLectureDtoList(GradeSearchConditionDto condition);

    List<TuitionDto> getTuitionData(Long idx);

    Page<StudentListDto> selectAllStudent(HashMap<String, Object> map, Pageable pageable);

    StudentListDto selectOndeStudent(Long idx);

    OverallGradeDto findOverallGrade(Long stuIdx);

    List<StudentLectureDto> getStudentLectureDto(LectureSearchConditionDto condition);

    Page<StudentLectureDto> getStudentLectureDtoPage(LectureSearchConditionDto condition,Pageable pageable);

    Integer findByEntranceDateAndMajorIdx(int year,Long majorIdx);
}