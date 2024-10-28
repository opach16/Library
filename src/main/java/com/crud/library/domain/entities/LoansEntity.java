package com.crud.library.domain.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LOANS")
public class LoansEntity {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;
    @NotNull
    private Long copyId;
    @NotNull
    private Long readerId;
    @NotNull
    private LocalDate loanDate = LocalDate.now();
    private LocalDate returnByDate = loanDate.plusDays(30);
    private LocalDate returnDate;

    public LoansEntity(Long copyId, Long readerId) {
        this.copyId = copyId;
        this.readerId = readerId;
    }
}
