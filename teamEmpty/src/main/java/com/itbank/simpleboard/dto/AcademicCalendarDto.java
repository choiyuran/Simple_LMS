package com.itbank.simpleboard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
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
