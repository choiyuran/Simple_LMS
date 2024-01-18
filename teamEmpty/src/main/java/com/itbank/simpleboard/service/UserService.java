package com.itbank.simpleboard.service;

import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.repository.UserRepository;
import com.itbank.simpleboard.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


}
