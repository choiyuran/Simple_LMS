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
            dto.setUser(realStudent.getUser());
            dto.setStudent_grade(realStudent.getStudent_grade());
            dto.setStudent_num(realStudent.getStudent_num());
            dto.setMajor(realStudent.getMajor());
            dto.setProfessor(realStudent.getProfessor());
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
            return convertUserToUserDTO(user);
        } else {
            // 사용자 정보가 없을 경우 null 또는 예외 처리
            return null;
        }
    }

    // User 엔티티를 UserDTO로 변환하는 메소드
    private UserDTO convertUserToUserDTO(User user) {
        // .get()은 단일 조회를 하면 옵션으로 감싸져있는데 그 옵션을 벗겨낸다
        Student student = studentRepository.findByUser(user).get();
        UserDTO users = new UserDTO();
        users.setIdx(user.getIdx());
        users.setUser_name(user.getUser_name());
        users.setUser_id(user.getUser_id());
        users.setUser_pw(user.getUser_pw());
        users.setSalt(user.getSalt());
        users.setEmail(user.getEmail());
        users.setUser_address(user.getAddress());
        users.setPnum(user.getPnum());
        users.setRole(user.getRole());
        users.setStudent(student);

        System.out.println(student);

        return users;
    }
}
