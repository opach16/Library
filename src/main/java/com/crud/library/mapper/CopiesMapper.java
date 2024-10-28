package com.crud.library.mapper;

import com.crud.library.domain.dto.CopiesDto;
import com.crud.library.domain.entities.CopiesEntity;
import org.springframework.stereotype.Service;

@Service
public class CopiesMapper {

    public CopiesEntity copiesDtoToCopiesEntity(CopiesDto dto) {
        return new CopiesEntity(dto.getId(), dto.getTitleId(), dto.getState());
    }

    public CopiesDto copiesEntityToCopiesDto(CopiesEntity entity) {
        return new CopiesDto(entity.getId(), entity.getTitleId(), entity.getState());
    }
}
