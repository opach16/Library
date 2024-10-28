package com.crud.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitlesDto {
    private Long id;
    private String title;
    private String author;
    private LocalDate releaseDate;

    public TitlesDto(String title, String author, LocalDate releaseDate) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
    }

}
