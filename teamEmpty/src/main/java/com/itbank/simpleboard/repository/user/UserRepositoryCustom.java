package com.itbank.simpleboard.repository.user;

import com.itbank.simpleboard.dto.ProfessorDto;
import com.itbank.simpleboard.dto.UserDTO;

public interface UserRepositoryCustom {

    UserDTO getUser(String userId);

    ProfessorDto getProfessor(UserDTO user);
}
