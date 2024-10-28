package com.crud.library.domain.dto;

import com.crud.library.domain.BookState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopiesDto {
    private Long id;
    private Long titleId;
    private BookState state;

    public CopiesDto(Long titleId, BookState state) {
        this.titleId = titleId;
        this.state = state;
    }
}
