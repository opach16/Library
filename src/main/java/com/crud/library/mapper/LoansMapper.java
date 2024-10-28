package com.crud.library.mapper;

import com.crud.library.domain.dto.LoansDto;
import com.crud.library.domain.entities.LoansEntity;
import org.springframework.stereotype.Service;

@Service
public class LoansMapper {

    public LoansDto loansEntityToLoansDto(LoansEntity loansEntity) {
        return new LoansDto(loansEntity.getId(),loansEntity.getCopyId(), loansEntity.getReaderId(),loansEntity.getLoanDate(), loansEntity.getReturnByDate(), loansEntity.getReturnDate());
    }
}
