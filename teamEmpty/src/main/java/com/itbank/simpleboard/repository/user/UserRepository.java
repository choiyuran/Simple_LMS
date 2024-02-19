package com.itbank.simpleboard.repository.user;

import com.itbank.simpleboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    @Query("SELECT u FROM User u WHERE u.user_id = :user_id AND u.email = :email")
    Optional<User> findByUser_idAndEmail(@Param("user_id") String user_id, @Param("email") String email);
}
