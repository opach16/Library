package com.crud.library.mapper;

import com.crud.library.domain.dto.LoanDto;
import com.crud.library.domain.entities.Loan;
import org.springframework.stereotype.Service;

@Service
public class LoanMapper {

    public LoanDto loanToLoansDto(Loan loan) {
        return new LoanDto(loan.getId(), loan.getCopyId(), loan.getReaderId(), loan.getLoanDate(), loan.getReturnByDate(), loan.getReturnDate());
    }
}
