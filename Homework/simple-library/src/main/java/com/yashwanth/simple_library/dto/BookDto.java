package com.yashwanth.simple_library.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookDto {

    private Long id;

    private String book_name;

    private Long edition;

    private LocalDate releaseDate;

    private List<String> authorName;
}
