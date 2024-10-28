package com.crud.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoansDto {
    private Long id;
    private Long copyId;
    private Long readerId;
    private LocalDate loanDate;
    private LocalDate returnByDate;
    private LocalDate returnDate;
}
