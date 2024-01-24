package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.EvaluateFormDto;
import com.itbank.simpleboard.dto.EvaluationDto;
import com.itbank.simpleboard.entity.Enrollment;
import com.itbank.simpleboard.entity.Evaluation;
import com.itbank.simpleboard.repository.EnrollmentRepository;
import com.itbank.simpleboard.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EvaluationDto findByIdx(Long idx) {
        Enrollment enrollment = enrollmentRepository.findById(idx).orElse(null);
        if(enrollment == null){
            return null;
        }
        EvaluationDto dto = new EvaluationDto();
        dto.setEnrollment_idx(enrollment.getIdx());
        dto.setLectureIdx(enrollment.getLecture().getIdx());
        dto.setStudentName(enrollment.getStudent().getUser().getUser_name());
        dto.setStudentNum(enrollment.getStudent().getStudent_num());
        dto.setLectureName(enrollment.getLecture().getName());
        dto.setProfessorIdx(enrollment.getLecture().getProfessor().getProfessor_idx());
        dto.setProfessorName(enrollment.getLecture().getProfessor().getUser().getUser_name());
        dto.setStudentIdx(enrollment.getStudent().getIdx());
        dto.setMajorName(enrollment.getStudent().getMajor().getName());
        return dto;
    }

    @Transactional
    public Evaluation save(EvaluateFormDto evaluateFormDto) {
        return evaluationRepository.save(
                new Evaluation(evaluateFormDto.getQ1(),
                        evaluateFormDto.getQ2(),
                        evaluateFormDto.getQ3(),
                        evaluateFormDto.getQ4(),
                        evaluateFormDto.getQ5(),
                        enrollmentRepository.findById(evaluateFormDto.getEnrollment_idx()).orElse(null)));
    }

    public List<Evaluation> findAll() {
        return evaluationRepository.findAll();
    }

    public List<Evaluation> findByStudent(Long studnetIdx) {
        return null;
    }
}
