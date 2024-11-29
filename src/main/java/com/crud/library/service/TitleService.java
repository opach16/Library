package com.crud.library.service;

import com.crud.library.domain.entities.Title;
import com.crud.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;

    public Title addTitle(Title title) {
        return titleRepository.save(title);
    }
}
