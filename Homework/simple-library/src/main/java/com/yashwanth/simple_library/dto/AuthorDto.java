package com.yashwanth.simple_library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorDto {

    private Long id;

    private String author_name;

    private List<String> books_name;
}
