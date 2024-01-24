package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.EnrollmentDto;
import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Student;

import java.util.List;

public interface EnrollmentRepositoryCustom {
    List<EnrollmentDto> getEnrollListByStudent(Student student);
}
