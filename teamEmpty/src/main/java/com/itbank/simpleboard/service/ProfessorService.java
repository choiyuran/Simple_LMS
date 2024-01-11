package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.lectureListDto;
import com.itbank.simpleboard.repository.professor.ProfessorRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public List<lectureListDto> getLectureListDto() {
        return professorRepository.getLectureListDto();
    }
}
