package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Payments;
import com.itbank.simpleboard.entity.Scholarship;
import com.itbank.simpleboard.entity.Scholarship_Award;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.repository.PaymentsRepository;
import com.itbank.simpleboard.repository.ScholarshipAwardRepository;
import com.itbank.simpleboard.repository.ScholarshipRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScholarShipAwardService {
    private final ScholarshipAwardRepository scholarshipAwardRepository;
    private final ScholarshipRepository scholarshipRepository;
    private final StudentRepository studentRepository;
    private final PaymentsRepository paymentsRepository;

    public Integer getTotalByPayments(Long payIdx, Long stuIdx) {
        Student student = studentRepository.findById(stuIdx).orElse(null);
        Payments payments = paymentsRepository.findById(payIdx).orElse(null);
        List<Scholarship_Award> scholarshipAward = scholarshipAwardRepository.findByStudent(student);
        Integer total1 = 0;
        Integer total2 = 0;
        for(Scholarship_Award e : scholarshipAward){
            if(payments.getSemester().contains(e.getScholarship().getYear().toString())){ // 년도 체크
                if(payments.getSemester().split(" ")[1].equals("1학기") && (e.getScholarship().getQuarter()==1 || e.getScholarship().getQuarter()==2)){
                    total1 += e.getScholarship().getPrice();
                }else if(payments.getSemester().split(" ")[1].equals("2학기") && (e.getScholarship().getQuarter()==3 || e.getScholarship().getQuarter()==4)){
                    total2 += e.getScholarship().getPrice();
                }else{
                    total1 += 0;
                    total2 += 0;
                }
            }
            System.err.println("Service : total1" + total1 + "total2 : "+ total2);
        }
        return payments.getSemester().contains("2학기") ? total2 : total1;
    }

    public Integer getTotal(Long idx) {
        Student student = studentRepository.findById(idx).orElse(null);
        Payments payments = paymentsRepository.findTopByOrderByIdxDesc();
        List<Scholarship_Award> scholarshipAward = scholarshipAwardRepository.findByStudent(student);

        Integer total1 = 0;
        Integer total2 = 0;
        for(Scholarship_Award e : scholarshipAward){
            if(payments.getSemester().contains(e.getScholarship().getYear().toString())){ // 년도 체크
                if(payments.getSemester().split(" ")[1].equals("1학기") && (e.getScholarship().getQuarter()==1 || e.getScholarship().getQuarter()==2)){
                    total1 += e.getScholarship().getPrice();
                }else if(payments.getSemester().split(" ")[1].equals("2학기") && (e.getScholarship().getQuarter()==3 || e.getScholarship().getQuarter()==4)){
                    total2 += e.getScholarship().getPrice();
                }else{
                    total1 += 0;
                    total2 += 0;
                }
            }
        }
        return payments.getSemester().contains("2학기") ? total2 : total1;
    }
}
