package com.itbank.simpleboard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class ManagerDTO {
    // 직원사진
    private String managerImg;

    // 아이디
    private String mamagerId;

    // 이름
    private String mamagerName;

    // 연락처
    private String mamagerPnum;

    // 이메일
    private String managerEmail;

    // 입사일
    private Date managerHireDate;

    public ManagerDTO(String managerImg, String mamagerId, String mamagerName, String mamagerPnum, String managerEmail, Date managerHireDate) {
        this.managerImg = managerImg;
        this.mamagerId = mamagerId;
        this.mamagerName = mamagerName;
        this.mamagerPnum = mamagerPnum;
        this.managerEmail = managerEmail;
        this.managerHireDate = managerHireDate;
    }
}
