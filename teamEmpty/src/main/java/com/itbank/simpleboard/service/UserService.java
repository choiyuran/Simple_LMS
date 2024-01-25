package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.ProfessorDto;
import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.User;
import com.itbank.simpleboard.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    // 아이디로 사용자 찾기
    public UserDTO getUserByUserId(Long idx) {
        User user = userRepository.findByIdx(idx);
        // 여기서 User 엔터티를 UserDTO로 변환하여 반환
        return convertToDto(user);
    }


    private UserDTO convertToDto(User user) {
        if(user == null){
            return null;
        }
        return new UserDTO(
                user.getIdx(),
                user.getUser_name(),
                user.getUser_id(),
                user.getUser_pw(),
                user.getSalt(),
                user.getEmail(),
                user.getAddress(),
                user.getPnum(),
                user.getRole()
        );
    }

    public UserDTO getUser(String userId, String userPw) {
        // 여기서 UserDTO 불러와서 userPw로 비교하고
        UserDTO user = userRepository.getUser(userId);
        // 해쉬처리 이래저래 해서
        return user;
    }

    public ProfessorDto getProfessor(UserDTO user) {
        ProfessorDto professor = userRepository.getProfessor(user);
        professor.setUser(user);
        return professor;
    }
}