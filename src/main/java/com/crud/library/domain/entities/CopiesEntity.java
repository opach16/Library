package com.crud.library.domain.entities;

import com.crud.library.domain.BookState;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COPIES")
public class CopiesEntity {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;
    @NotNull
    private Long titleId;
    private BookState state;
}
