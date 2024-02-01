package com.itbank.simpleboard.repository.user;

import com.itbank.simpleboard.dto.ManagerLoginDto;
import com.itbank.simpleboard.dto.ProfessorDto;
import com.itbank.simpleboard.dto.StudentDto;
import com.itbank.simpleboard.dto.UserDTO;

public interface UserRepositoryCustom {

    UserDTO getUser(String userId);

    ProfessorDto getProfessor(UserDTO user);

    StudentDto getStudent(UserDTO user);

    ManagerLoginDto getManager(UserDTO user);
}
