package com.itbank.simpleboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.time.LocalDate;
import java.util.Date;


@Data
@RequiredArgsConstructor
public class AcademicCalendarDto {
    private Long idx;
    private LocalDate start_date;
    private LocalDate end_date;
    private String title;

    public AcademicCalendarDto(LocalDate start_date, LocalDate end_date, String title) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.title = title;
    }
}
