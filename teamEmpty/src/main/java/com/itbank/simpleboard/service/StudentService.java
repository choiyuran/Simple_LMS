package com.itbank.simpleboard.service;

import com.itbank.simpleboard.component.MailComponent;
import com.itbank.simpleboard.dto.*;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.entity.User;
import com.itbank.simpleboard.repository.student.SituationRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import com.itbank.simpleboard.repository.student.StudentRepositoryCustomImpl;
import com.itbank.simpleboard.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        if (user.isPresent()) {
            realUser = user.get();
        }


        Optional<Student> student = studentRepository.findByUser(realUser);
        Student realStudent = null;


        if (student.isPresent()) {
            realStudent = student.get();
            dto.setIdx(realStudent.getIdx());
            dto.setUser(getUserDTO(realStudent.getUser()));
            dto.setStudent_grade(realStudent.getStudent_grade());
            dto.setStudent_num(realStudent.getStudent_num());
            dto.setMajor(getMajorDto(realStudent));
            dto.setProfessor(getProfessorDto(realStudent, dto.getMajor()));
            dto.setEnteranceDate(realStudent.getEnteranceDate());

        }

        return dto;
    }

    // User 엔티티를 UserDTO로 변환하는 메소드
//    private StudentDto convertUserToUserDTO(User user) {
//        // .get()은 단일 조회를 하면 옵션으로 감싸져있는데 그 옵션을 벗겨낸다
//        Optional<Student> studentOptional = studentRepository.findByUser(user);
//        if (studentOptional.isPresent()) {
//            Student studentEntity = studentOptional.get();
//
//            return getStudentDto(user, studentEntity);
//        }
//        return null;
//    }

    private static StudentDto getStudentDto(User user, Student studentEntity) {
        UserDTO studentUser = getUserDTO(user);
        MajorDto majorDto = getMajorDto(studentEntity);
        ProfessorDto professorDto = getProfessorDto(studentEntity, majorDto);
        return getStudentDto(studentEntity, studentUser, professorDto, majorDto);
    }

    private static StudentDto getStudentDto(Student studentEntity, UserDTO studentUser, ProfessorDto professorDto, MajorDto majorDto) {
        StudentDto studentDto = new StudentDto();
        studentDto.setIdx(studentEntity.getIdx());
        studentDto.setStudent_num(studentEntity.getStudent_num());
        studentDto.setStudent_grade(studentEntity.getStudent_grade());
        studentDto.setUser(studentUser);
        studentDto.setProfessor(professorDto);
        studentDto.setMajor(majorDto);
        studentDto.setEnteranceDate(studentEntity.getEnteranceDate());
        return studentDto;
    }

    private static ProfessorDto getProfessorDto(Student studentEntity, MajorDto majorDto) {
        UserDTO professorUser = getUserDTO(studentEntity.getProfessor().getUser());
        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setProfessor_idx(studentEntity.getProfessor().getProfessor_idx());
        professorDto.setImg(studentEntity.getProfessor().getProfessor_img());
        professorDto.setUser(professorUser);
        professorDto.setMajor(majorDto);
        professorDto.setHireDate(studentEntity.getProfessor().getHireDate());
        return professorDto;
    }

    private static MajorDto getMajorDto(Student studentEntity) {
        MajorDto majorDto = new MajorDto();
        majorDto.setIdx(studentEntity.getMajor().getIdx());
        majorDto.setName(studentEntity.getMajor().getName());
        majorDto.setTuition(studentEntity.getMajor().getTuition());
        majorDto.setCollege_idx(studentEntity.getMajor().getCollege().getIdx());
        majorDto.setAbolition(studentEntity.getMajor().getAbolition());
        return majorDto;
    }

    private static UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdx(user.getIdx());
        userDTO.setUser_name(user.getUser_name());
        userDTO.setUser_id(user.getUser_id());
        userDTO.setUser_pw(user.getUser_pw());
        userDTO.setSalt(user.getSalt());
        userDTO.setEmail(user.getEmail());
        userDTO.setUser_address(user.getAddress());
        userDTO.setPnum(user.getPnum());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public List<TuitionDto> getTuitionData(Long studentIdx) {
        List<TuitionDto> tuitionDataList = studentRepository.getTuitionData(studentIdx);
        // 여기서 필요에 따라 TuitionDto를 StudentDto로 변환하는 로직을 추가할 수 있습니다.
        return tuitionDataList;
    }
}
