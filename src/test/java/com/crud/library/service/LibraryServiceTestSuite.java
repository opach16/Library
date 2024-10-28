package com.crud.library.service;

import com.crud.library.domain.BookState;
import com.crud.library.domain.dto.CopiesDto;
import com.crud.library.domain.dto.LoansDto;
import com.crud.library.domain.dto.ReadersDto;
import com.crud.library.domain.dto.TitlesDto;
import com.crud.library.domain.entities.CopiesEntity;
import com.crud.library.domain.entities.LoansEntity;
import com.crud.library.domain.entities.ReadersEntity;
import com.crud.library.domain.entities.TitlesEntity;
import com.crud.library.exception.NotFoundException;
import com.crud.library.repository.CopyRepository;
import com.crud.library.repository.LoanRepository;
import com.crud.library.repository.ReaderRepository;
import com.crud.library.repository.TitleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LibraryServiceTestSuite {

    @Autowired
    private LibraryService libraryService;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private CopyRepository copyRepository;
    @Autowired
    private LoanRepository loanRepository;

    @Test
    void testAddReader() {
        //given
        ReadersDto reader = new ReadersDto("John", "Smith");
        //when
        ReadersDto addedReader = libraryService.addReader(reader);
        Optional<ReadersEntity> retrievedReader = readerRepository.findById(addedReader.getId());
        //then
        assertTrue(retrievedReader.isPresent());
        //cleanUp
        readerRepository.deleteById(addedReader.getId());
    }

    @Test
    void testAddTitle() {
        //given
        TitlesDto title = new TitlesDto("Title", "Author", LocalDate.now());
        //when
        TitlesDto addedTitleDto = libraryService.addTitle(title);
        Optional<TitlesEntity> retrievedTitleEntity = titleRepository.findById(addedTitleDto.getId());
        //then
        assertTrue(retrievedTitleEntity.isPresent());
        //cleanUp
        titleRepository.deleteById(addedTitleDto.getId());
    }

    @Test
    void testAddCopy() {
        //given
        CopiesDto copy = new CopiesDto(1L, BookState.AVAILABLE);
        //when
        CopiesDto addedCopy = libraryService.addCopy(copy);
        Optional<CopiesEntity> retrievedCopy = copyRepository.findById(addedCopy.getId());
        //then
        assertTrue(retrievedCopy.isPresent());
        //cleanUp
        copyRepository.deleteById(addedCopy.getId());
    }

    @Test
    void testUpdateCopyState() {
        //given
        CopiesDto copy = new CopiesDto(1L, BookState.AVAILABLE);
        CopiesDto addedCopy = libraryService.addCopy(copy);
        addedCopy.setState(BookState.DAMAGED);
        //when
        CopiesDto retrievedCopy = libraryService.updateCopyState(addedCopy);
        //then
        assertEquals(retrievedCopy.getState(), BookState.DAMAGED);
        //cleanUp
        copyRepository.deleteById(retrievedCopy.getId());
    }

    @Test
    void testGetNumberOfCopies() {
        //given
        CopiesDto copy1 = libraryService.addCopy(new CopiesDto(1L, BookState.AVAILABLE));
        CopiesDto copy2 = libraryService.addCopy(new CopiesDto(1L, BookState.DAMAGED));
        CopiesDto copy3 = libraryService.addCopy(new CopiesDto(1L, BookState.LOST));
        CopiesDto copy4 = libraryService.addCopy(new CopiesDto(1L, BookState.AVAILABLE));
        //when
        int numberOfCopies = libraryService.getNumberOfCopies(new TitlesDto(1L, "Title", "Author", LocalDate.now()));
        //then
        assertEquals(2, numberOfCopies);
        //cleanUp
        copyRepository.deleteById(copy1.getId());
        copyRepository.deleteById(copy2.getId());
        copyRepository.deleteById(copy3.getId());
        copyRepository.deleteById(copy4.getId());
    }

    @Test
    void testLoanBook() {
        //given
        CopiesDto copy = libraryService.addCopy(new CopiesDto(1L, BookState.AVAILABLE));
        ReadersDto reader = libraryService.addReader(new ReadersDto("John", "Smith"));
        //when
        LoansDto loanedBook = libraryService.loanBook(copy.getId(), reader.getId());
        Optional<LoansEntity> retrievedBook = loanRepository.findById(loanedBook.getId());
        //then
        assertTrue(retrievedBook.isPresent());
        assertThrows(NotFoundException.class, () -> libraryService.loanBook(0L, reader.getId()));
        assertThrows(NotFoundException.class, () -> libraryService.loanBook(copy.getId(), 0L));
        //cleanUp
        loanRepository.deleteById(loanedBook.getId());
        copyRepository.deleteById(copy.getId());
        readerRepository.deleteById(reader.getId());
    }

    @Test
    void testLoanBookNotAvailable() {
        //given
        CopiesDto copy = libraryService.addCopy(new CopiesDto(1L, BookState.LOANED));
        ReadersDto reader = libraryService.addReader(new ReadersDto("John", "Smith"));
        //when & then
        assertThrows(NotFoundException.class, () -> libraryService.loanBook(copy.getId(), reader.getId()));
        //cleanUp
        copyRepository.deleteById(copy.getId());
        readerRepository.deleteById(reader.getId());

    }

    @Test
    void testReturnBook() {
        //given
        CopiesDto copy = libraryService.addCopy(new CopiesDto(1L, BookState.AVAILABLE));
        ReadersDto reader = libraryService.addReader(new ReadersDto("John", "Smith"));
        LoansDto loanedBook = libraryService.loanBook(copy.getId(), reader.getId());
        //when
        libraryService.returnBook(loanedBook.getId());
        //then
        CopiesEntity retrievedCopy = copyRepository.findById(copy.getId()).get();
        assertEquals(BookState.AVAILABLE, retrievedCopy.getState());
        //cleanUp
        loanRepository.deleteById(loanedBook.getId());
        copyRepository.deleteById(copy.getId());
        readerRepository.deleteById(reader.getId());
    }
}
