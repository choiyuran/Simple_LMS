package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.Professor;
import com.itbank.simpleboard.repository.professor.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public Page<ProfessorLectureDto> getLectureDtoList(LectureSearchConditionDto condition, Pageable pageable) {
        return professorRepository.getLectureDtoList(condition, pageable);
    }

    public List<String> getMajorNameList(LectureSearchConditionDto condition) {
        return professorRepository.getMajorNameList(condition);
    }

    public ProfessorLectureDto getLectureDto(Long idx) {
        return professorRepository.getLectureDto(idx);
    }

    public List<ProfessorUserDto> getProfessorsByMajor(Long majorIdx) {
        List<ProfessorUserDto> professors = professorRepository.getProfessorNamesByMajor(majorIdx);
        return professors;
    }

    public Professor getProfessorByIdx(Long professorIdx) {
        Optional<Professor> professor = professorRepository.findById(professorIdx);
        return professor.orElse(null);
    }

    public List<EvaluateFormDto> getEvaluation(Long idx) {
        return professorRepository.viewEvaluation(idx);
    }

    public Map<String, Map<String, Long>> countTotalQ1Q2Q3(List<EvaluateFormDto> evaluation) {
        Map<String, Map<String, Long>> result = new HashMap<>();

        Map<String, Long> q1 = evaluation.stream()
                .flatMap(dto -> Stream.of(dto.getQ1()))
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));

        Map<String, Long> q2 = evaluation.stream()
                .flatMap(dto -> Stream.of(dto.getQ2()))
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));

        Map<String, Long> q3 = evaluation.stream()
                .flatMap(dto -> Stream.of(dto.getQ3()))
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));

        result.put("q1", q1);
        result.put("q2", q2);
        result.put("q3", q3);

        return result;
    }

    public List<EnrollmentDto> getEnrollmentList(Long lectureIdx) {
        return professorRepository.getEnrollmentList(lectureIdx);
    }

    public List<ProfessorUserDto> getProfessorsByMajor(String majorName) {
        List<Professor> professorList = professorRepository.findAllByMajorName(majorName);
        List<ProfessorUserDto> professors = new ArrayList<>();
        if (!professorList.isEmpty()) {
            for (Professor p : professorList) {
                ProfessorUserDto dto = new ProfessorUserDto(
                        p.getProfessor_idx(),
                        p.getUser().getUser_name()
                );
                professors.add(dto);
            }
        }
        log.info(professors.toString());
        return professors;
    }


    public List<Integer> getGradeList(LectureSearchConditionDto condition) {
        return professorRepository.getGradeList(condition);
    }
}
