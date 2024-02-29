package com.itbank.simpleboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@NoArgsConstructor
public class ManagerDTO {
    private Long idx;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    // 입사일
    private Date managerHireDate;

    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leaveDate;

    @QueryProjection
    public ManagerDTO(Long idx, String managerImg, String managerId, String managerName, String managerPnum, String managerEmail, Date managerHireDate, String address, Date leaveDate) {
        this.idx = idx;
        this.managerImg = managerImg;
        this.managerId = managerId;
        this.managerName = managerName;
        this.managerPnum = managerPnum;
        this.managerEmail = managerEmail;
        this.managerHireDate = managerHireDate;
        this.address = address;
        this.leaveDate = leaveDate;
    }
}
