package com.crud.library.mapper;

import com.crud.library.domain.dto.CopyDto;
import com.crud.library.domain.entities.Copy;
import org.springframework.stereotype.Service;

@Service
public class CopyMapper {

    public Copy copyDtoToCopy(CopyDto dto) {
        return new Copy(dto.getId(), dto.getTitleId(), dto.getState());
    }

    public CopyDto copyToCopyDto(Copy copy) {
        return new CopyDto(copy.getId(), copy.getTitleId(), copy.getState());
    }
}
