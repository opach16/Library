package com.crud.library.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitleDto {
    private Long id;
    private String title;
    private String author;
    private LocalDate releaseDate;
}
