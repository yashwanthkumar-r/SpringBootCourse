package com.yashwanth.simple_library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDto {

    private Long id;

    private String book_name;

    private Long edition;

    private LocalDate releaseDate;

    private List<String> authorName;
}
