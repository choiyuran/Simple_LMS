package com.itbank.simpleboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerLoginDto {
    private Long idx;
    private String img;
    private UserDTO user;
    private Date hire_date;
}
