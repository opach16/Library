package com.crud.library.controller;

import com.crud.library.domain.dto.CopyDto;
import com.crud.library.domain.dto.LoanDto;
import com.crud.library.domain.dto.ReaderDto;
import com.crud.library.domain.dto.TitleDto;
import com.crud.library.domain.entities.Copy;
import com.crud.library.domain.entities.Reader;
import com.crud.library.domain.entities.Title;
import com.crud.library.exception.NotFoundException;
import com.crud.library.mapper.CopyMapper;
import com.crud.library.mapper.LoanMapper;
import com.crud.library.mapper.ReaderMapper;
import com.crud.library.mapper.TitleMapper;
import com.crud.library.service.CopyService;
import com.crud.library.service.LoanService;
import com.crud.library.service.ReaderService;
import com.crud.library.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/library")
public class LibraryController {

    private final ReaderService readerService;
    private final TitleService titleService;
    private final CopyService copyService;
    private final LoanService loanService;
    private final ReaderMapper readerMapper;
    private final TitleMapper titleMapper;
    private final CopyMapper copyMapper;
    private final LoanMapper loanMapper;

    @PostMapping(path = "/addReader", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReaderDto> addReader(@RequestBody ReaderDto readerDto) {
        Reader reader = readerMapper.readerDtoToReader(readerDto);
        return ResponseEntity.ok(readerMapper.readerToReaderDto(readerService.addReader(reader)));
    }

    @PostMapping(path = "/addTitle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TitleDto> addTitle(@RequestBody TitleDto titleDto) {
        Title title = titleMapper.titleDtoToTitle(titleDto);
        return ResponseEntity.ok(titleMapper.titleToTitleDto(titleService.addTitle(title)));
    }

    @PostMapping(path = "/addCopy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CopyDto> addCopy(@RequestBody CopyDto copyDto) {
        Copy copy = copyMapper.copyDtoToCopy(copyDto);
        return ResponseEntity.ok(copyMapper.copyToCopyDto(copyService.addCopy(copy)));
    }

    @GetMapping("/getNumberOfCopies")
    public ResponseEntity<Integer> getNumberOfCopies(@RequestParam Long titleId) throws NotFoundException {
        return ResponseEntity.ok(copyService.getNumberOfAvailableCopies(titleId));
    }

    @PatchMapping("/loanBook")
    public ResponseEntity<LoanDto> loanBook(@RequestParam Long copyId, @RequestParam Long readerId) throws NotFoundException {
        return ResponseEntity.ok(loanMapper.loanToLoansDto(loanService.loanBook(copyId, readerId)));
    }

    @PatchMapping("/returnBook")
    public ResponseEntity<Void> returnBook(@RequestParam Long loanId) throws NotFoundException {
        loanService.returnBook(loanId);
        return ResponseEntity.ok().build();
    }
}
