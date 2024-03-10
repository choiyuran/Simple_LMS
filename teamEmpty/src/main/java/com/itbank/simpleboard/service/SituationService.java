package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.SituationChageDto;
import com.itbank.simpleboard.dto.SituationStuDto;
import com.itbank.simpleboard.dto.StudentDto;
import com.itbank.simpleboard.entity.Situation;
import com.itbank.simpleboard.entity.SituationRecord;
import com.itbank.simpleboard.entity.Status_type;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.repository.student.SituationRecordRepository;
import com.itbank.simpleboard.repository.student.SituationRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SituationService {
    private final SituationRepository situationRepository;
    private final StudentRepository studentRepository;
    private final SituationRecordRepository situationRecordRepository;


    public Situation selectById(Long studentIdx) {
        Student student = studentRepository.findById(studentIdx).get();
        Situation situation = situationRepository.findByStudent(student).get();

        return situation;
    }

    public List<Situation> selectAll() {
        return situationRepository.findAll();
    }


    public Page<SituationStuDto> selectSituationStu(String status, Pageable pageable) {
        return situationRepository.findAllSituationStu(status, pageable);
    }

    public SituationStuDto selectOneSituation(Long idx) {
        return situationRepository.findOneSituation(idx);
    }

    @Transactional
    public Situation situationUpdate(SituationStuDto param) {
        Situation situation = situationRepository.findById(param.getIdx()).get();
        situation.setStart_date(new Date(param.getStart_date().getTime()));
        if(param.getEnd_date() != null) {
            situation.setEnd_date(new Date(param.getEnd_date().getTime()));
        }
        else {
            situation.setEnd_date(null);
        }
        situation.setStudent_status(param.getStatus());
        return situation;
    }

    @Transactional
    public Situation updateSitu(SituationChageDto dto) {
        Student student = studentRepository.findById(dto.getStudent()).orElse(null);
        Situation situation = situationRepository.findByStudent(student).orElse(null);
        if(situation != null){
            if(dto.getEnd_date() != null)
                situation.setEnd_date(Date.valueOf(dto.getEnd_date()));
            if(dto.getStart_date() != null)
                situation.setStart_date(Date.valueOf(dto.getStart_date()));
            situation.setStudent_status(dto.getStatus());
        }
        return situation;
    }

    public Status_type findByUserIdx(Long stuIdx) {
        Student student  = studentRepository.findById(stuIdx).orElse(null);
        return situationRepository.findByStudent(student).get().getStudent_status();
    }

    public Situation findByStudentIdx(Long studentIdx) {
        Student student = studentRepository.findById(studentIdx).orElseGet(() -> null);
        return situationRepository.findByStudent(student).orElseGet(() -> null);
    }

    @Transactional
    public SituationRecord situationRecordAdd(SituationStuDto param) {
        Student student = studentRepository.findById(param.getStudent_idx()).get();
        SimpleDateFormat sdf = new SimpleDateFormat();
        Date startDate = new Date(param.getStart_date().getTime());
        Date endDate = null;
        if(param.getEnd_date() != null) {
            endDate = new Date(param.getEnd_date().getTime());
        }

        SituationRecord situationRecord = new SituationRecord(
            param.getStatus(),student,startDate,endDate
        );
        return situationRecordRepository.save(situationRecord);
    }

    public List<SituationRecord> situationRecordAllByIdx(Long idx) {
        Student student = studentRepository.findById(idx).get();
        return situationRecordRepository.findAllByStudent(student);

    }
}