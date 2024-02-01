package com.itbank.simpleboard.repository.student;

import com.itbank.simpleboard.dto.EnrollmentDto;
import com.itbank.simpleboard.dto.StudentDto;
import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface EnrollmentRepositoryCustom {
    List<EnrollmentDto> findByStudentAll(Student student);
}
