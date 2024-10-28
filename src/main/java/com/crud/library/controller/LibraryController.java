package com.crud.library.controller;

import com.crud.library.domain.dto.CopiesDto;
import com.crud.library.domain.dto.ReadersDto;
import com.crud.library.domain.dto.TitlesDto;
import com.crud.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/library")
public class LibraryController {

    private final LibraryService service;

    @PostMapping(path = "/addReader", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addReader(@RequestBody ReadersDto readerDto) {
        service.addReader(readerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/addTitle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addTitle(@RequestBody TitlesDto titleDto) {
        service.addTitle(titleDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/addCopy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addCopy(@RequestBody CopiesDto copyDto) {
        service.addCopy(copyDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getNumberOfCopies")
    public ResponseEntity<Integer> getNumberOfCopies(@RequestBody TitlesDto titleDto) {
        return ResponseEntity.ok(service.getNumberOfCopies(titleDto));
    }

    @PostMapping("/loanBook")
    public ResponseEntity<Void> loanBook(@RequestParam Long copyId, @RequestParam Long readerId) {
        service.loanBook(copyId, readerId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/returnBook/{loanId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long loanId) {
        service.returnBook(loanId);
        return ResponseEntity.ok().build();
    }
}
