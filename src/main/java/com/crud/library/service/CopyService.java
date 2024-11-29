package com.crud.library.service;

import com.crud.library.domain.BookState;
import com.crud.library.domain.entities.Copy;
import com.crud.library.exception.NotFoundException;
import com.crud.library.repository.CopyRepository;
import com.crud.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CopyService {

    private final CopyRepository copyRepository;
    private final TitleRepository titleRepository;

    public Copy addCopy(Copy copy) {
        return copyRepository.save(copy);
    }

    public Copy updateCopyState(Long copyId, BookState state) throws NotFoundException {
        Copy copy = copyRepository.findById(copyId).orElseThrow(() -> new NotFoundException("Copy not found"));
        copy.setState(state);
        return copyRepository.save(copy);
    }

    public int getNumberOfAvailableCopies(Long titleId) throws NotFoundException {
        titleRepository.findById(titleId).orElseThrow(() -> new NotFoundException("Title not found"));
        List<Copy> listOfCopies = copyRepository.findAllByTitleId(titleId);
        return listOfCopies.stream()
                .filter(copy -> copy.getState() == BookState.AVAILABLE)
                .mapToInt(copy -> 1)
                .sum();
    }
}
