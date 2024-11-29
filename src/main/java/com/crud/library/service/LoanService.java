package com.crud.library.service;

import com.crud.library.domain.BookState;
import com.crud.library.domain.entities.Copy;
import com.crud.library.domain.entities.Loan;
import com.crud.library.domain.entities.Reader;
import com.crud.library.exception.NotFoundException;
import com.crud.library.repository.CopyRepository;
import com.crud.library.repository.LoanRepository;
import com.crud.library.repository.ReaderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanService {

    private final LoanRepository loanRepository;
    private final ReaderRepository readerRepository;
    private final CopyRepository copyRepository;
    private final CopyService copyService;

    public Loan loanBook(Long copyId, Long readerId) throws NotFoundException {
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new NotFoundException("Reader not found"));
        Copy copy = copyRepository.findById(copyId)
                .orElseThrow(() -> new NotFoundException("Copy not found"));
        if (copy.getState() != BookState.AVAILABLE) {
            throw new NotFoundException("Book is not available");
        }
        Loan loan = loanRepository.save(new Loan(copy.getId(), reader.getId()));
        copy.setState(BookState.LOANED);
        return loan;
    }

    public void returnBook(Long loanId) throws NotFoundException {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NotFoundException("Loan not found"));
        Copy copy = copyRepository.findById(loan.getCopyId())
                .orElseThrow(() -> new NotFoundException("Copy not found"));
        if (copy.getState() == BookState.LOANED) {
            loan.setReturnDate(LocalDate.now());
            copy.setState(BookState.AVAILABLE);
            copyService.updateCopyState(copy.getId(), BookState.AVAILABLE);
        }
    }
}
