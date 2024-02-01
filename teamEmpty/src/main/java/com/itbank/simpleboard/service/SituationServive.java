package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.SituationStuDto;
import com.itbank.simpleboard.entity.Situation;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.repository.student.SituationRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SituationServive {
    private final SituationRepository situationRepository;
    private final StudentRepository studentRepository;
    public Situation selectById(Long studentIdx) {
        Student student = studentRepository.findById(studentIdx).get();
        Situation situation = situationRepository.findByStudent(student).get();

        return situation;
    }

    public List<Situation> selectAll() {
        return situationRepository.findAll();
    }


    public List<SituationStuDto> selectSituationStu(String status) {
        return situationRepository.findAllSituationStu(status);
    }

    public SituationStuDto selectOneSituation(Long idx) {
        return situationRepository.findOneSituation(idx);
    }

    @Transactional
    public Situation situationUpdate(SituationStuDto param) {
        Situation situation = situationRepository.findById(param.getIdx()).get();
        situation.setStart_date(param.getStart_date());
        if(situation.getEnd_date() != null) {
            situation.setEnd_date(param.getEnd_date());
        }
        situation.setStudent_status(param.getStatus());
        return situation;
    }
}
