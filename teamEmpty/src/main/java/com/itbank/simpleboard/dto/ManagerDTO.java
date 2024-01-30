package com.itbank.simpleboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class ManagerDTO {
    // 직원사진
    private String managerImg;

    // 아이디
    private String managerId;

    // 이름
    private String managerName;

    // 연락처
    private String managerPnum;

    // 이메일
    private String managerEmail;

    // 입사일
    private Date managerHireDate;

    @QueryProjection
    public ManagerDTO(String managerImg, String managerId, String managerName, String managerPnum, String managerEmail, Date managerHireDate) {
        this.managerImg = managerImg;
        this.managerId = managerId;
        this.managerName = managerName;
        this.managerPnum = managerPnum;
        this.managerEmail = managerEmail;
        this.managerHireDate = managerHireDate;
    }
}
