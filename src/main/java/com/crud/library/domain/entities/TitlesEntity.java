package com.crud.library.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TITLES")
public class TitlesEntity {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;
    @NotNull(message = "Tittle is required")
    private String title;
    private String author;
    private LocalDate releaseDate;
}