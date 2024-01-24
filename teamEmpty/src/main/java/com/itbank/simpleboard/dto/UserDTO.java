package com.itbank.simpleboard.dto;

import com.itbank.simpleboard.entity.Student;
import com.itbank.simpleboard.entity.User_role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long idx;
    private String user_name;
    private String user_id;
    private String user_pw;
    private String salt;
    private String email;
    private String user_address;
    private String pnum;
    private User_role role;


    public UserDTO(Long idx, String userName, String userId, String userPw, String salt, String email, String address, String pnum, User_role role) {
    }
}
