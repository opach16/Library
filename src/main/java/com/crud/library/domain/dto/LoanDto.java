package com.crud.library.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {
    private Long id;
    private Long copyId;
    private Long readerId;
    private LocalDate loanDate;
    private LocalDate returnByDate;
    private LocalDate returnDate;
}
