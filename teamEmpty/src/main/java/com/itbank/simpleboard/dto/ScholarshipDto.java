package com.itbank.simpleboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ScholarshipDto {
    private Long idx;
    private String category;
    private String name;
    private Integer price;
    private Integer year;
    private Integer quarter;

    @QueryProjection
    public ScholarshipDto(Long idx, String category, String name, Integer price, Integer year, Integer quarter) {
        this.idx = idx;
        this.category = category;
        this.name = name;
        this.price = price;
        this.year = year;
        this.quarter = quarter;
    }
}
