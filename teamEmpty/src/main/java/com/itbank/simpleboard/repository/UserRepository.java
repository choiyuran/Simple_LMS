package com.itbank.simpleboard.repository;

import com.itbank.simpleboard.dto.UserDTO;
import com.itbank.simpleboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
