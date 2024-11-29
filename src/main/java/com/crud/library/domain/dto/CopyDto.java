package com.crud.library.domain.dto;

import com.crud.library.domain.BookState;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyDto {
    private Long id;
    private Long titleId;
    private BookState state;
}
