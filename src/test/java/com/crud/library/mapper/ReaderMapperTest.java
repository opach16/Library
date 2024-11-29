package com.crud.library.mapper;

import com.crud.library.domain.dto.ReaderDto;
import com.crud.library.domain.entities.Reader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReaderMapperTest {

    @Autowired
    private ReaderMapper readerMapper;

    @Test
    void shouldMapReaderDtoToReader() {
        //given
        ReaderDto readerDto = new ReaderDto(1L, "testName1", "testLastName1", LocalDate.of(2024, 10, 5));
        //when
        Reader retrievedReader = readerMapper.readerDtoToReader(readerDto);
        //then
        assertEquals(readerDto.getId(), retrievedReader.getId());
        assertEquals(readerDto.getFirstName(), retrievedReader.getFirstName());
        assertEquals(readerDto.getLastName(), retrievedReader.getLastName());
        assertEquals(readerDto.getJoiningDate(), retrievedReader.getJoiningDate());
    }

    @Test
    void shouldMapReaderToReaderDto() {
        //given
        Reader reader = new Reader(1L, "testName1", "testLastName1", LocalDate.of(2024, 10, 5));
        //when
        ReaderDto retrievedReaderDto = readerMapper.readerToReaderDto(reader);
        //then
        assertEquals(reader.getId(), retrievedReaderDto.getId());
        assertEquals(reader.getFirstName(), retrievedReaderDto.getFirstName());
        assertEquals(reader.getLastName(), retrievedReaderDto.getLastName());
        assertEquals(reader.getJoiningDate(), retrievedReaderDto.getJoiningDate());
    }
}