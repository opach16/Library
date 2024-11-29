package com.crud.library.service;

import com.crud.library.domain.entities.Title;
import com.crud.library.repository.TitleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TitleServiceTest {

    @Autowired
    private TitleService titleService;

    @Autowired
    private TitleRepository titleRepository;

    @BeforeEach
    void setUp() {
        titleRepository.deleteAll();
    }

    @DisplayName("Should add title to the repository")
    @Test
    void createTitle() {
        //given
        Title title1 = new Title(1L, "testTitle1", "testAuthor1", LocalDate.of(2024, 12, 9));
        Title title2 = new Title(2L, "testTitle2", "testAuthor2", LocalDate.of(2023, 11, 8));
        //when
        titleService.addTitle(title1);
        titleService.addTitle(title2);
        List<Title> titleList = titleRepository.findAll();
        //then
        assertEquals(2, titleList.size());
        assertEquals(title1.getId(), titleList.get(0).getId());
        assertEquals(title1.getTitle(), titleList.get(0).getTitle());
        assertEquals(title1.getAuthor(), titleList.get(0).getAuthor());
        assertEquals(title1.getReleaseDate(), titleList.get(0).getReleaseDate());
    }
}