package com.yashwanth.simple_library.service;

import com.yashwanth.simple_library.dto.AuthorDto;
import com.yashwanth.simple_library.entities.Author;
import com.yashwanth.simple_library.exceptions.ResourceNotFoundException;
import com.yashwanth.simple_library.repositories.AuthorRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    private final ModelMapper modelMapper;


    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    public AuthorDto mapToDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setAuthor_name(author.getAuthor_name());

        List<String> bookNames = author.getBooks()
                .stream().map(book -> book.getBook_name())
                .toList();
        dto.setBooks_name(bookNames);

        return dto;
    }

    @Transactional
    public AuthorDto getAuthorByName(String name) {
        return mapToDto(authorRepository.getAuthorByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Resource Not found: " + name)
        ));
    }

    @Transactional
    public AuthorDto getAuthorById(Long id) {
        return mapToDto(authorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource Not found: " + id)
        ));
    }


    @Transactional
    public Boolean deleteAuthorById(Long id) {

        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource Not found: " + id);
        }
        authorRepository.deleteById(id);
        return true;
    }
}
