package com.crud.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadersDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate joiningDate;

    public ReadersDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
