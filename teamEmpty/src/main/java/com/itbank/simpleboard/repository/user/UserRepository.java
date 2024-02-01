package com.itbank.simpleboard.repository.user;

import com.itbank.simpleboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    User findByIdx(Long idx);
}
