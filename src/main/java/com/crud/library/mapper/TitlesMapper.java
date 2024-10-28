package com.crud.library.mapper;

import com.crud.library.domain.dto.TitlesDto;
import com.crud.library.domain.entities.TitlesEntity;
import org.springframework.stereotype.Service;

@Service
public class TitlesMapper {

    public TitlesEntity titlesDtoToTitlesEntity(TitlesDto dto) {
        return new TitlesEntity(dto.getId(), dto.getTitle(), dto.getAuthor(), dto.getReleaseDate());
    }

    public TitlesDto titlesEntityToTitlesDto(TitlesEntity entity) {
        return new TitlesDto(entity.getId(), entity.getTitle(), entity.getAuthor(), entity.getReleaseDate());
    }
}
