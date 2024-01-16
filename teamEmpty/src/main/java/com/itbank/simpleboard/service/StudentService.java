package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.StudentDto;
import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.entity.User;
import com.itbank.simpleboard.repository.UserRepository;
import com.itbank.simpleboard.repository.manager.MajorRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    public StudentDto findByUserIdx(Long userIdx) {
        StudentDto dto = new StudentDto();
        Optional<User> user = userRepository.findById(userIdx);
        User realUser = null;
        if(user.isPresent()){
            realUser = user.get();
        }

        System.err.println("realUser : " + realUser);

        Optional<Student> student = studentRepository.findByUser(realUser);
        Student realStudent = null;

        System.err.println(student.get());

        if(student.isPresent()){
            realStudent = student.get();
            dto.setIdx(realStudent.getIdx());
            dto.setUser(realStudent.getUser());
            dto.setStudent_grade(realStudent.getStudent_grade());
            dto.setStudent_num(realStudent.getStudent_num());
            dto.setMajor(realStudent.getMajor());
            dto.setProfessor(realStudent.getProfessor());
            dto.setEnteranceDate(realStudent.getEnteranceDate());
        }

        System.out.println(dto);
        return dto;
    }

    public UserDTO userUpdate(UserDTO param) {

        return param;
    }
}
