package com.crud.library.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LOANS")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long copyId;
    @NotNull
    private Long readerId;
    @NotNull
    private LocalDate loanDate = LocalDate.now();
    private LocalDate returnByDate = loanDate.plusDays(30);
    private LocalDate returnDate;

    public Loan(Long copyId, Long readerId) {
        this.copyId = copyId;
        this.readerId = readerId;
    }
}
