package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.PaymentsDto;
import com.itbank.simpleboard.dto.PaymentsListDto;
import com.itbank.simpleboard.dto.StudentDto;
import com.itbank.simpleboard.entity.Payments;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.entity.YesOrNo;
import com.itbank.simpleboard.repository.PaymentsRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentsService {
    private final PaymentsRepository paymentsRepository;
    private final StudentRepository studentRepository;
    private final MajorService majorService;
    private final ScholarShipAwardService scholarShipAwardService;
    @Transactional
    public Payments save(PaymentsDto dto) {
        Student student = studentRepository.findById(dto.getStudent()).orElse(null);
        Payments payments = paymentsRepository.findByStudentAndSemester(student,dto.getSemester());
        payments.setFlag(YesOrNo.Y);
        return paymentsRepository.save(payments);
    }

    public List<PaymentsListDto> getList(Long stuIdx) {
        Student student = studentRepository.findById(stuIdx).orElse(null);
        List<PaymentsListDto> dtos = new ArrayList<>();
        if(student != null){
            List<Payments> paymentsList = paymentsRepository.findByStudentOrderByIdxDesc(student);
            for(Payments p : paymentsList){
                Integer tuition = majorService.getTuition(stuIdx);
                Integer totalScholarship = scholarShipAwardService.getTotalByPayments(p.getIdx(), stuIdx);
                PaymentsListDto dto = new PaymentsListDto(p.getIdx(),p.getSemester(), tuition,totalScholarship,p.getStudent().getUser().getUser_name(),p.getStudent().getMajor().getCollege().getName(),p.getStudent().getMajor().getName(),p.getDate(),p.getFlag());
                dtos.add(dto);
            }
        }

        return dtos;
    }

    public Payments findById(Long idx) {
        return paymentsRepository.findById(idx).orElse(null);
    }
}
