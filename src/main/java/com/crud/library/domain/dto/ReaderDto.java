package com.crud.library.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate joiningDate;

    public ReaderDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
