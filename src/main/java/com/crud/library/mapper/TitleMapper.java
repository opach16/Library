package com.crud.library.mapper;

import com.crud.library.domain.dto.TitleDto;
import com.crud.library.domain.entities.Title;
import org.springframework.stereotype.Service;

@Service
public class TitleMapper {

    public Title titleDtoToTitle(TitleDto titleDto) {
        return new Title(titleDto.getId(), titleDto.getTitle(), titleDto.getAuthor(), titleDto.getReleaseDate());
    }

    public TitleDto titleToTitleDto(Title title) {
        return new TitleDto(title.getId(), title.getTitle(), title.getAuthor(), title.getReleaseDate());
    }
}
