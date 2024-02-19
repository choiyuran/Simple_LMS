package com.itbank.simpleboard.service;

import com.itbank.simpleboard.component.HashComponent;
import com.itbank.simpleboard.component.MailComponent;
import com.itbank.simpleboard.dto.ManagerLoginDto;
import com.itbank.simpleboard.dto.ProfessorDto;
import com.itbank.simpleboard.dto.StudentDto;
import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.User;
import com.itbank.simpleboard.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final HashComponent hashComponent;
    private final MailComponent mailComponent;

    // 아이디로 사용자 찾기
    public UserDTO getUserByUserIdx(Long idx) {
        Optional<User> userOptional = userRepository.findById(idx);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
        // 여기서 User 엔터티를 UserDTO로 변환하여 반환
            return convertToDto(user);
        } else {
            return null;
        }
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

    @Transactional
    public UserDTO userUpdate(Object login, UserDTO userdto) {
        User user = getUserEntity(login);

        if (user != null) {
            if (userdto.getPnum() != null) {
                user.setPnum(userdto.getPnum());
            }
            if (userdto.getEmail() != null) {
                user.setEmail(userdto.getEmail());
            }
            if (userdto.getUser_address() != null) {
                user.setAddress(userdto.getUser_address());
            }
            // 여기서 User 엔티티를 UserDTO로 변환하여 반환하도록 구현
            return getUserDTO(user);
        } else {
            return null;
        }
    }

    public Integer sendAuthNumber(String email) {
        Random random = new Random();
        String authNumber = (random.nextInt(899999) + 100000) + "";

        HashMap<String, String> param = new HashMap<>();
        param.put("to", email);
        param.put("subject", "[Centum University]인증번호 입니다");
        param.put("authCode", authNumber);

        mailComponent.sendVerificationCode(param);

        return Integer.parseInt(authNumber);
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

    public UserDTO getUser(String userId, String userPw) {
        // 여기서 UserDTO 불러와서 userPw로 비교하고
        UserDTO user = userRepository.getUser(userId);
        // 해쉬처리 이래저래 해서
        String loginPw = hashComponent.getHash(userPw, user.getSalt());
        if (user.getUser_pw().equals(loginPw)) {
            return user;
        } else {
            return null;
        }
    }

    public ProfessorDto getProfessor(UserDTO user) {
        ProfessorDto professor = userRepository.getProfessor(user);
        professor.setUser(user);
        return professor;
    }

    public StudentDto getStudent(UserDTO user) {
        StudentDto student = userRepository.getStudent(user);
        student.setUser(user);
        return student;
    }

    public ManagerLoginDto getManager(UserDTO user) {
        ManagerLoginDto manager = userRepository.getManager(user);
        manager.setUser(user);
        return manager;
    }

    @Transactional
    public int changePassword(Object login, String nowPassword, String newPassword) {
        int result = 0;
        User userEntity = getUserEntity(login);

        if (userEntity != null) {
            result = changePasswordForUser(userEntity, nowPassword, newPassword);
        }

        return result;
    }

    private User getUserEntity(Object login) {
        User userEntity = null;

        if (login instanceof StudentDto) {
            StudentDto user = (StudentDto) login;
            Optional<User> userOptional = userRepository.findById(user.getUser().getIdx());
            if (userOptional.isPresent()) {
                userEntity = userOptional.get();
            }
        } else if (login instanceof ProfessorDto) {
            ProfessorDto user = (ProfessorDto) login;
            Optional<User> userOptional = userRepository.findById(user.getUser().getIdx());
            if (userOptional.isPresent()) {
                userEntity = userOptional.get();
            }
        } else if (login instanceof ManagerLoginDto) {
            ManagerLoginDto user = (ManagerLoginDto) login;
            Optional<User> userOptional = userRepository.findById(user.getUser().getIdx());
            if (userOptional.isPresent()) {
                userEntity = userOptional.get();
            }
        }
        return userEntity;
    }

    private int changePasswordForUser(User userEntity, String nowPassword, String newPassword) {
        int result = 0;
        String nowSalt = userEntity.getSalt();
        String nowPw = hashComponent.getHash(nowPassword, nowSalt);

        if (nowPw.equals(userEntity.getUser_pw())) {
            String newSalt = hashComponent.getRandomSalt();
            String newPw = hashComponent.getHash(newPassword, newSalt);
            userEntity.setSalt(newSalt);
            userEntity.setUser_pw(newPw);
            result = 1;
        }

        return result;
    }

    public Integer checkByUser_idAndEmail(String userId, String email) {
        Optional<User> userOptional = userRepository.findByUser_idAndEmail(userId, email);
        if (userOptional.isPresent()) {
            return sendAuthNumber(email);
        } else {
            return 0;
        }
    }
}