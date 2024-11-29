package com.crud.library.mapper;

import com.crud.library.domain.dto.ReaderDto;
import com.crud.library.domain.entities.Reader;
import org.springframework.stereotype.Service;

@Service
public class ReaderMapper {

    public Reader readerDtoToReader(ReaderDto readerDto) {
        return new Reader(readerDto.getId(), readerDto.getFirstName(), readerDto.getLastName(), readerDto.getJoiningDate());
    }

    public ReaderDto readerToReaderDto(Reader reader) {
        return new ReaderDto(reader.getId(), reader.getFirstName(), reader.getLastName(), reader.getJoiningDate());
    }
}
