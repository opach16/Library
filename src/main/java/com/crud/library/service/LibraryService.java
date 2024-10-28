package com.crud.library.service;

import com.crud.library.domain.*;
import com.crud.library.domain.dto.CopiesDto;
import com.crud.library.domain.dto.LoansDto;
import com.crud.library.domain.dto.ReadersDto;
import com.crud.library.domain.dto.TitlesDto;
import com.crud.library.domain.entities.CopiesEntity;
import com.crud.library.domain.entities.LoansEntity;
import com.crud.library.domain.entities.ReadersEntity;
import com.crud.library.domain.entities.TitlesEntity;
import com.crud.library.exception.NotFoundException;
import com.crud.library.mapper.CopiesMapper;
import com.crud.library.mapper.LoansMapper;
import com.crud.library.mapper.ReadersMapper;
import com.crud.library.mapper.TitlesMapper;
import com.crud.library.repository.CopyRepository;
import com.crud.library.repository.LoanRepository;
import com.crud.library.repository.ReaderRepository;
import com.crud.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LibraryService {
    private final ReadersMapper readersMapper;
    private final TitlesMapper titlesMapper;
    private final CopiesMapper copiesMapper;
    private final LoansMapper loansMapper;
    private final CopyRepository copyRepository;
    private final LoanRepository loanRepository;
    private final ReaderRepository readerRepository;
    private final TitleRepository titleRepository;

    public ReadersDto addReader(ReadersDto readerDto) {
        ReadersEntity readerEntity = readersMapper.readerDtoToReadersEntity(readerDto);
        ReadersEntity addedReader = readerRepository.save(readerEntity);
        return readersMapper.readerEntityToReadersDto(addedReader);
    }

    public TitlesDto addTitle(TitlesDto titleDto) {
        TitlesEntity titleEntity = titlesMapper.titlesDtoToTitlesEntity(titleDto);
        TitlesEntity addedTitle = titleRepository.save(titleEntity);
        return titlesMapper.titlesEntityToTitlesDto(addedTitle);
    }

    public CopiesDto addCopy(CopiesDto copyDto) {
        CopiesEntity copyEntity = copiesMapper.copiesDtoToCopiesEntity(copyDto);
        CopiesEntity addedCopy = copyRepository.save(copyEntity);
        return copiesMapper.copiesEntityToCopiesDto(addedCopy);
    }

    public CopiesDto updateCopyState(CopiesDto copyDto) {
        CopiesEntity copyEntity = copiesMapper.copiesDtoToCopiesEntity(copyDto);
        CopiesEntity updatedCopy = copyRepository.save(copyEntity);
        return copiesMapper.copiesEntityToCopiesDto(updatedCopy);
    }

    public int getNumberOfCopies(TitlesDto titleDto) {
        TitlesEntity titleEntity = titlesMapper.titlesDtoToTitlesEntity(titleDto);
        List<CopiesEntity> listOfCopies = copyRepository.findAllByTitleId(titleEntity.getId());
        return listOfCopies.stream()
                .filter(copy -> copy.getState() == BookState.AVAILABLE)
                .mapToInt(copy -> 1)
                .sum();
    }

    @Transactional
    public LoansDto loanBook(Long copyId, Long readerId) throws NotFoundException {
        ReadersEntity reader = readerRepository.findById(readerId).orElseThrow(() -> new NotFoundException("Reader not found"));
        CopiesEntity copy = copyRepository.findById(copyId).orElseThrow(() -> new NotFoundException("Copy not found"));
        if (copy.getState() != BookState.AVAILABLE) {
            throw new NotFoundException("Book is not available");
        }
        LoansEntity loan = loanRepository.save(new LoansEntity(copy.getId(), reader.getId()));
        copy.setState(BookState.LOANED);
        return loansMapper.loansEntityToLoansDto(loan);
    }

    public void returnBook(Long loanId) {
        LoansEntity loan = loanRepository.findById(loanId).orElseThrow(() -> new NotFoundException("Loan not found"));
        CopiesEntity copyEntity = copyRepository.findById(loan.getCopyId()).orElseThrow(() -> new NotFoundException("Copy not found"));
        CopiesDto copyDto = copiesMapper.copiesEntityToCopiesDto(copyEntity);
        if (copyEntity.getState() == BookState.LOANED) {
            loan.setReturnDate(LocalDate.now());
            loanRepository.save(loan);
            copyDto.setState(BookState.AVAILABLE);
            updateCopyState(copyDto);
        }
    }
}
