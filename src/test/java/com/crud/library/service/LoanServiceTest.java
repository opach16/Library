package com.crud.library.service;

import com.crud.library.domain.BookState;
import com.crud.library.domain.entities.Copy;
import com.crud.library.domain.entities.Loan;
import com.crud.library.domain.entities.Reader;
import com.crud.library.exception.NotFoundException;
import com.crud.library.repository.CopyRepository;
import com.crud.library.repository.LoanRepository;
import com.crud.library.repository.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class LoanServiceTest {

    @Autowired
    private LoanService loanService;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private LoanRepository loanRepository;

    @BeforeEach
    void setUp() {
        copyRepository.deleteAll();
        readerRepository.deleteAll();
        loanRepository.deleteAll();
    }

    @DisplayName("Should save loan to the repository and change BookState to LOANED")
    @Test
    void loanBook() {
        //given
        Copy copy = new Copy(1L, 1L, BookState.AVAILABLE);
        Copy savedCopy = copyRepository.save(copy);
        Reader reader = new Reader(1L, "testFirstName", "testLastName", LocalDate.of(2024, 10, 5));
        Reader savedReader = readerRepository.save(reader);
        //when
        Loan loan = loanService.loanBook(savedCopy.getId(), savedReader.getId());
        Copy retrievedCopy = copyRepository.findById(savedCopy.getId()).orElseThrow();
        Reader retrievedReader = readerRepository.findById(savedReader.getId()).orElseThrow();
        List<Loan> loanList = loanRepository.findAll();
        //then
        assertEquals(1, loanList.size());
        assertEquals(BookState.LOANED, retrievedCopy.getState());
        assertEquals(loan.getCopyId(), retrievedCopy.getId());
        assertEquals(loan.getReaderId(), retrievedReader.getId());
    }

    @DisplayName("Should throw NotFoundException if the BookState is not equal AVAILABLE")
    @Test
    void loanBookWrongBookState() {
        //given
        Copy copy = new Copy(1L, 1L, BookState.LOST);
        Copy savedCopy = copyRepository.save(copy);
        Reader reader = new Reader(1L, "testFirstName", "testLastName", LocalDate.of(2024, 10, 5));
        Reader savedReader = readerRepository.save(reader);
        //when & then
        assertThrows(NotFoundException.class, () -> loanService.loanBook(savedCopy.getId(), savedReader.getId()));
    }

    @DisplayName("Should throw NotFoundException if passed copyId does not exist in the repository")
    @Test
    void loanBookWrongCopyId() {
        //given
        Copy copy = new Copy(1L, 1L, BookState.AVAILABLE);
        copyRepository.save(copy);
        Reader reader = new Reader(1L, "testFirstName", "testLastName", LocalDate.of(2024, 10, 5));
        Reader savedReader = readerRepository.save(reader);
        //when & then
        assertThrows(NotFoundException.class, () -> loanService.loanBook(55L, savedReader.getId()));
    }

    @DisplayName("Should throw NotFoundException if passed readerId does not exist in the repository")
    @Test
    void loanBookWrongReaderId() {
        //given
        Copy copy = new Copy(1L, 1L, BookState.AVAILABLE);
        Copy savedCopy = copyRepository.save(copy);
        Reader reader = new Reader(1L, "testFirstName", "testLastName", LocalDate.of(2024, 10, 5));
        readerRepository.save(reader);
        //when & then
        assertThrows(NotFoundException.class, () -> loanService.loanBook(savedCopy.getId(), 55L));
    }

    @DisplayName("Should change the BookState to AVAILABLE")
    @Test
    void returnBook() {
        //given
        Copy copy = new Copy(1L, 1L, BookState.AVAILABLE);
        Copy savedCopy = copyRepository.save(copy);
        Reader reader = new Reader(1L, "testFirstName", "testLastName", LocalDate.of(2024, 10, 5));
        Reader savedReader = readerRepository.save(reader);
        Loan loan = loanService.loanBook(savedCopy.getId(), savedReader.getId());
        //when
        loanService.returnBook(loan.getId());
        Copy retrievedCopy = copyRepository.findById(savedCopy.getId()).orElseThrow();
        //then
        assertEquals(BookState.AVAILABLE, retrievedCopy.getState());
    }

    @DisplayName("Should throw NotFoundException if passed loanId does not exist in the repository")
    @Test
    void returnBookWrongLoanId() {
        //given
        Copy copy = new Copy(1L, 1L, BookState.AVAILABLE);
        Copy savedCopy = copyRepository.save(copy);
        Reader reader = new Reader(1L, "testFirstName", "testLastName", LocalDate.of(2024, 10, 5));
        Reader savedReader = readerRepository.save(reader);
        loanService.loanBook(savedCopy.getId(), savedReader.getId());
        //when & then
        assertThrows(NotFoundException.class, () -> loanService.returnBook(55L));
    }
}