package com.crud.library.mapper;

import com.crud.library.domain.dto.LoanDto;
import com.crud.library.domain.entities.Loan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LoanMapperTest {

    @Autowired
    private LoanMapper loanMapper;

    @Test
    void shouldMapLoanToLoansDto() {
        //given
        Loan loan = new Loan(1L, 2L, 3L, LocalDate.of(2024, 10, 15), LocalDate.of(2024, 11, 5), LocalDate.of(2024, 11, 1));
        //when
        LoanDto retrievedLoanDto = loanMapper.loanToLoansDto(loan);
        //then
        assertEquals(loan.getId(), retrievedLoanDto.getId());
        assertEquals(loan.getCopyId(), retrievedLoanDto.getCopyId());
        assertEquals(loan.getReaderId(), retrievedLoanDto.getReaderId());
        assertEquals(loan.getLoanDate(), retrievedLoanDto.getLoanDate());
        assertEquals(loan.getReturnByDate(), retrievedLoanDto.getReturnByDate());
        assertEquals(loan.getReturnDate(), retrievedLoanDto.getReturnDate());
    }
}