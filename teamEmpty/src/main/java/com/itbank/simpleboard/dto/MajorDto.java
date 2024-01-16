package com.itbank.simpleboard.dto;

import com.itbank.simpleboard.entity.College;
import com.itbank.simpleboard.entity.Major;
import com.itbank.simpleboard.entity.YesOrNo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class MajorDto {
    private Long idx;
    private String name;
    private Integer tuition;
    private Long college_idx;
    private YesOrNo abolition;

    public MajorDto(Long idx, String name, Integer tuition, Long college_idx) {
        this.idx = idx;
        this.name = name;
        this.tuition = tuition;
        this.college_idx = college_idx;
        this.abolition = YesOrNo.N;
    }
}
