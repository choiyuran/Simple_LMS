package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.MajorDto;
import com.itbank.simpleboard.dto.ProfessorDto;
import com.itbank.simpleboard.dto.StudentDto;
import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.entity.User;
import com.itbank.simpleboard.repository.student.SituationRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import com.itbank.simpleboard.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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


        Optional<Student> student = studentRepository.findByUser(realUser);
        Student realStudent = null;


        if(student.isPresent()){
            realStudent = student.get();
            dto.setIdx(realStudent.getIdx());
            dto.setUser(getUserDTO(realStudent.getUser()));
            dto.setStudent_grade(realStudent.getStudent_grade());
            dto.setStudent_num(realStudent.getStudent_num());
            dto.setMajor(getMajorDto(realStudent));
            dto.setProfessor(getProfessorDto(realStudent,dto.getMajor()));
            dto.setEnteranceDate(realStudent.getEnteranceDate());

        }

        return dto;
    }



    @Transactional  // 업데이트 반영 (테이블에 내용이 바꿀때 사용한다)
    public UserDTO userUpdate(Long userIdx, UserDTO userdto) {   // param에서 idx를 long로 받는다
        Optional<User> userOptional = userRepository.findById(userIdx);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUser_pw(userdto.getUser_pw());
            user.setPnum(userdto.getPnum());
            user.setEmail(userdto.getEmail());
            user.setAddress(userdto.getUser_address());
            // 여기서 User 엔티티를 UserDTO로 변환하여 반환하도록 구현
            return getUserDTO(user);
        } else {
            // 사용자 정보가 없을 경우 null 또는 예외 처리
            return null;
        }
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
        UserDTO studentUser = new UserDTO();
        studentUser.setIdx(user.getIdx());
        studentUser.setUser_name(user.getUser_name());
        studentUser.setUser_id(user.getUser_id());
        studentUser.setUser_pw(user.getUser_pw());
        studentUser.setSalt(user.getSalt());
        studentUser.setEmail(user.getEmail());
        studentUser.setUser_address(user.getAddress());
        studentUser.setPnum(user.getPnum());
        studentUser.setRole(user.getRole());
        return studentUser;
    }



}
