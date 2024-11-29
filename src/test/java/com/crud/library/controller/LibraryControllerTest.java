package com.crud.library.controller;

import com.crud.library.domain.BookState;
import com.crud.library.domain.dto.CopyDto;
import com.crud.library.domain.dto.LoanDto;
import com.crud.library.domain.dto.ReaderDto;
import com.crud.library.domain.dto.TitleDto;
import com.crud.library.domain.entities.Copy;
import com.crud.library.domain.entities.Loan;
import com.crud.library.domain.entities.Reader;
import com.crud.library.domain.entities.Title;
import com.crud.library.mapper.CopyMapper;
import com.crud.library.mapper.LoanMapper;
import com.crud.library.mapper.ReaderMapper;
import com.crud.library.mapper.TitleMapper;
import com.crud.library.service.CopyService;
import com.crud.library.service.LoanService;
import com.crud.library.service.ReaderService;
import com.crud.library.service.TitleService;
import com.google.gson.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CopyService copyService;

    @MockBean
    private CopyMapper copyMapper;

    @MockBean
    private LoanService loanService;

    @MockBean
    private LoanMapper loanMapper;

    @MockBean
    private ReaderService readerService;

    @MockBean
    private ReaderMapper readerMapper;

    @MockBean
    private TitleService titleService;

    @MockBean
    private TitleMapper titleMapper;

    private final GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter());

    @Test
    void shouldAddReader() throws Exception {
        //given
        ReaderDto readerDto = new ReaderDto(1L, "testFirstName", "testLastName", LocalDate.of(2024, 10, 5));
        Reader reader = new Reader(1L, "testFirstName", "testLastName", LocalDate.of(2024, 10, 5));
        when(readerService.addReader(reader)).thenReturn(reader);
        when(readerMapper.readerDtoToReader(readerDto)).thenReturn(reader);
        when(readerMapper.readerToReaderDto(reader)).thenReturn(readerDto);
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(readerDto);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/library/addReader")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("testFirstName")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("testLastName")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.joiningDate", Matchers.is("2024-10-05")));
    }

    @Test
    void shouldAddTitle() throws Exception {
        //given
        TitleDto titleDto = new TitleDto(1L, "testTitle", "testAuthor", LocalDate.of(2024, 10, 5));
        Title title = new Title(1L, "testTitle", "testAuthor", LocalDate.of(2024, 10, 5));
        when(titleService.addTitle(title)).thenReturn(title);
        when(titleMapper.titleDtoToTitle(titleDto)).thenReturn(title);
        when(titleMapper.titleToTitleDto(title)).thenReturn(titleDto);
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(titleDto);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/library/addTitle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("testTitle")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", Matchers.is("testAuthor")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate", Matchers.is("2024-10-05")));
    }

    @Test
    void shouldAddCopy() throws Exception {
        //given
        CopyDto copyDto = new CopyDto(1L, 42L, BookState.AVAILABLE);
        Copy copy = new Copy(1L, 42L, BookState.AVAILABLE);
        when(copyService.addCopy(copy)).thenReturn(copy);
        when(copyMapper.copyDtoToCopy(copyDto)).thenReturn(copy);
        when(copyMapper.copyToCopyDto(copy)).thenReturn(copyDto);
        Gson gson = gsonBuilder.create();
        String jsonContent = gson.toJson(copyDto);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/library/addCopy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titleId", Matchers.is(42)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state", Matchers.is(BookState.AVAILABLE.toString())));
    }

    @Test
    void shouldGetNumberOfAvailableCopies() throws Exception {
        //given
        when(copyService.getNumberOfAvailableCopies(any(Long.class))).thenReturn(5);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/library/getNumberOfCopies")
                        .queryParam("titleId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(5)));
    }

    @Test
    void shouldLoanBook() throws Exception {
        //given
        Loan loan = new Loan(1L, 2L, 3L, LocalDate.of(2024, 10, 5), LocalDate.of(2024, 11, 5), LocalDate.of(2024, 11, 1));
        LoanDto loanDto = new LoanDto(1L, 2L, 3L, LocalDate.of(2024, 10, 5), LocalDate.of(2024, 11, 5), LocalDate.of(2024, 11, 1));
        when(loanMapper.loanToLoansDto(loan)).thenReturn(loanDto);
        when(loanService.loanBook(any(Long.class), any(Long.class))).thenReturn(loan);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/v1/library/loanBook")
                        .queryParam("copyId", "5")
                        .queryParam("readerId", "13"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.copyId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.readerId", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.loanDate", Matchers.is("2024-10-05")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnByDate", Matchers.is("2024-11-05")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnDate", Matchers.is("2024-11-01")));
    }

    @Test
    void shouldReturnBook() throws Exception {
        //given
        doNothing().when(loanService).returnBook(any(Long.class));
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/v1/library/returnBook")
                        .queryParam("loanId", "4"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(loanService, times(1)).returnBook(any(Long.class));
    }

    private static final class LocalDateAdapter implements JsonSerializer<LocalDate> {

        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }
}