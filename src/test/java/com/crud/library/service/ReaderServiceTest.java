package com.crud.library.service;

import com.crud.library.domain.entities.Reader;
import com.crud.library.repository.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReaderServiceTest {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private ReaderRepository readerRepository;

    @BeforeEach
    void setUp() {
        readerRepository.deleteAll();
    }

    @DisplayName("Should add reader to the repository")
    @Test
    void addReader() {
        //given
        Reader reader1 = new Reader(1L, "testName1", "testLastName1", LocalDate.of(2024, 10, 5));
        Reader reader2 = new Reader(2L, "testName2", "testLastName2", LocalDate.of(2023, 9, 3));
        //when
        Reader savedReader1 = readerService.addReader(reader1);
        readerService.addReader(reader2);
        List<Reader> readerList = readerRepository.findAll();
        //then
        assertEquals(2, readerList.size());
        assertEquals(savedReader1.getId(), readerList.get(0).getId());
        assertEquals(savedReader1.getFirstName(), readerList.get(0).getFirstName());
        assertEquals(savedReader1.getLastName(), readerList.get(0).getLastName());
        assertEquals(savedReader1.getJoiningDate(), readerList.get(0).getJoiningDate());
    }
}