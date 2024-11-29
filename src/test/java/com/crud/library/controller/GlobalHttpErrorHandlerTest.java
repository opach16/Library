package com.crud.library.controller;

import com.crud.library.exception.NotFoundException;
import com.crud.library.mapper.CopyMapper;
import com.crud.library.mapper.LoanMapper;
import com.crud.library.mapper.ReaderMapper;
import com.crud.library.mapper.TitleMapper;
import com.crud.library.service.CopyService;
import com.crud.library.service.LoanService;
import com.crud.library.service.ReaderService;
import com.crud.library.service.TitleService;
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

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringJUnitWebConfig
@WebMvcTest(LibraryController.class)
class GlobalHttpErrorHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @MockBean
    private ReaderService readerService;

    @MockBean
    private TitleService titleService;

    @MockBean
    private CopyService copyService;

    @MockBean
    private ReaderMapper readerMapper;

    @MockBean
    private TitleMapper titleMapper;

    @MockBean
    private CopyMapper copyMapper;

    @MockBean
    private LoanMapper loanMapper;

    @Test
    void shouldHandleNotFoundException() throws Exception {
        //given
        doThrow(new NotFoundException("Loan not found")).when(loanService).returnBook(any(Long.class));
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/v1/library/returnBook")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .queryParam("loanId", "5"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is("Loan not found")));
        verify(loanService).returnBook(any(Long.class));
    }
}