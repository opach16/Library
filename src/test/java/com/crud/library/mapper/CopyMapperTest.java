package com.crud.library.mapper;

import com.crud.library.domain.BookState;
import com.crud.library.domain.dto.CopyDto;
import com.crud.library.domain.entities.Copy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CopyMapperTest {

    @Autowired
    private CopyMapper copyMapper;

    @Test
    void shouldMapCopyDtoToCopy() {
        //given
        CopyDto copyDto = new CopyDto(1L, 2L, BookState.AVAILABLE);
        //when
        Copy retrievedCopy = copyMapper.copyDtoToCopy(copyDto);
        //then
        assertEquals(copyDto.getId(), retrievedCopy.getId());
        assertEquals(copyDto.getTitleId(), retrievedCopy.getTitleId());
        assertEquals(copyDto.getState(), retrievedCopy.getState());
    }

    @Test
    void shouldMapCopyToCopyDto() {
        //given
        Copy copy = new Copy(1L, 2L, BookState.AVAILABLE);
        //when
        CopyDto retrievedCopyDto = copyMapper.copyToCopyDto(copy);
        //then
        assertEquals(copy.getId(), retrievedCopyDto.getId());
        assertEquals(copy.getTitleId(), retrievedCopyDto.getTitleId());
        assertEquals(copy.getState(), retrievedCopyDto.getState());
    }
}