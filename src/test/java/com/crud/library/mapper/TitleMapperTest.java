package com.crud.library.mapper;

import com.crud.library.domain.dto.TitleDto;
import com.crud.library.domain.entities.Title;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TitleMapperTest {

    @Autowired
    private TitleMapper titleMapper;

    @Test
    void shouldMapTitleDtoToTitle() {
        //given
        TitleDto titleDto = new TitleDto(1L, "testTitle1", "testAuthor1", LocalDate.of(2024, 12, 9));
        //when
        Title retrievedTitle = titleMapper.titleDtoToTitle(titleDto);
        //then
        assertEquals(titleDto.getId(), retrievedTitle.getId());
        assertEquals(titleDto.getTitle(), retrievedTitle.getTitle());
        assertEquals(titleDto.getAuthor(), retrievedTitle.getAuthor());
        assertEquals(titleDto.getReleaseDate(), retrievedTitle.getReleaseDate());
    }

    @Test
    void shouldMapTitleToTitleDto() {
        //given
        Title title = new Title(1L, "testTitle1", "testAuthor1", LocalDate.of(2024, 12, 9));
        //when
        TitleDto retrievedTitleDto = titleMapper.titleToTitleDto(title);
        //then
        assertEquals(title.getId(), retrievedTitleDto.getId());
        assertEquals(title.getTitle(), retrievedTitleDto.getTitle());
        assertEquals(title.getAuthor(), retrievedTitleDto.getAuthor());
        assertEquals(title.getReleaseDate(), retrievedTitleDto.getReleaseDate());
    }
}