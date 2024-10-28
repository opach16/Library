package com.crud.library.mapper;

import com.crud.library.domain.dto.ReadersDto;
import com.crud.library.domain.entities.ReadersEntity;
import org.springframework.stereotype.Service;

@Service
public class ReadersMapper {

    public ReadersEntity readerDtoToReadersEntity(ReadersDto dto) {
        return new ReadersEntity(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getJoiningDate());
    }

    public ReadersDto readerEntityToReadersDto(ReadersEntity entity) {
        return new ReadersDto(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getJoiningDate());
    }
}
