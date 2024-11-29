package com.crud.library.service;

import com.crud.library.domain.BookState;
import com.crud.library.domain.entities.Copy;
import com.crud.library.domain.entities.Title;
import com.crud.library.exception.NotFoundException;
import com.crud.library.repository.CopyRepository;
import com.crud.library.repository.TitleRepository;
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
class CopyServiceTest {

    @Autowired
    private CopyService copyService;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private TitleRepository titleRepository;

    @BeforeEach
    void setUp() {
        titleRepository.deleteAll();
    }

    @DisplayName("Should add copy to the repository")
    @Test
    void addCopy() {
        //given
        Copy copy1 = new Copy(1L, 2L, BookState.AVAILABLE);
        Copy copy2 = new Copy(2L, 3L, BookState.AVAILABLE);
        Copy copy3 = new Copy(3L, 4L, BookState.AVAILABLE);
        //when
        Copy savedCopy1 = copyService.addCopy(copy1);
        copyService.addCopy(copy2);
        copyService.addCopy(copy3);
        List<Copy> copyList = copyRepository.findAll();
        //then
        assertEquals(3, copyList.size());
        assertEquals(savedCopy1.getId(), copyList.get(0).getId());
        assertEquals(savedCopy1.getTitleId(), copyList.get(0).getTitleId());
        assertEquals(savedCopy1.getState(), copyList.get(0).getState());
    }

    @DisplayName("Should change the status of the copy")
    @Test
    void updateCopyState() {
        //given
        Copy copy = new Copy(1L, 2L, BookState.AVAILABLE);
        Copy savedCopy = copyService.addCopy(copy);
        //when
        Copy updatedCopyState = copyService.updateCopyState(savedCopy.getId(), BookState.LOANED);
        //then
        assertEquals(BookState.LOANED, updatedCopyState.getState());
        assertEquals(1L, updatedCopyState.getId());
        assertEquals(2L, updatedCopyState.getTitleId());
    }

    @DisplayName("Should throw NotFoundException if passed copyId does not exist in the repository")
    @Test
    void updateCopyStateWrongId() {
        //given
        Copy copy = new Copy(1L, 2L, BookState.AVAILABLE);
        Copy savedCopy = copyService.addCopy(copy);
        //when & then
        assertThrows(NotFoundException.class, () -> copyService.updateCopyState(55L, BookState.LOANED));
    }

    @DisplayName("Should return the number of copies with the given titleId and AVAILABLE status")
    @Test
    void getNumberOfAvailableCopies() {
        //given
        Title title1 = new Title(1L, "testTitle1", "testAuthor1", LocalDate.of(2024, 10, 15));
        Title title2 = new Title(2L, "testTitle2", "testAuthor2", LocalDate.of(2024, 10, 15));
        Title savedTitle1 = titleRepository.save(title1);
        Title savedTitle2 = titleRepository.save(title2);
        Copy copy1 = new Copy(1L, savedTitle1.getId(), BookState.AVAILABLE);
        Copy copy2 = new Copy(2L, savedTitle1.getId(), BookState.AVAILABLE);
        Copy copy3 = new Copy(3L, savedTitle1.getId(), BookState.LOANED);
        Copy copy4 = new Copy(4L, savedTitle1.getId(), BookState.LOST);
        Copy copy5 = new Copy(5L, savedTitle2.getId(), BookState.AVAILABLE);
        copyService.addCopy(copy1);
        copyService.addCopy(copy2);
        copyService.addCopy(copy3);
        copyService.addCopy(copy4);
        copyService.addCopy(copy5);
        //when
        int numberOfCopies = copyService.getNumberOfAvailableCopies(savedTitle1.getId());
        //then
        assertEquals(2, numberOfCopies);
    }

    @DisplayName("Should throw NotFoundException if passed titleId does not exist in the repository")
    @Test
    void getNumberOfAvailableCopiesWrongTitleId() {
        //given
        Copy copy1 = new Copy(1L, 1L, BookState.AVAILABLE);
        Copy copy2 = new Copy(2L, 1L, BookState.AVAILABLE);
        Copy copy3 = new Copy(3L, 1L, BookState.LOANED);
        copyService.addCopy(copy1);
        copyService.addCopy(copy2);
        copyService.addCopy(copy3);
        //when & then
        assertThrows(NotFoundException.class, () -> copyService.getNumberOfAvailableCopies(55L));
    }
}