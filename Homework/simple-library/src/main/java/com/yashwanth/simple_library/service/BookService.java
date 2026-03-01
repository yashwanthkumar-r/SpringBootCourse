package com.yashwanth.simple_library.service;

import com.yashwanth.simple_library.dto.BookDto;
import com.yashwanth.simple_library.entities.Author;
import com.yashwanth.simple_library.entities.Book;
import com.yashwanth.simple_library.exceptions.ResourceNotFoundException;
import com.yashwanth.simple_library.repositories.AuthorRepository;
import com.yashwanth.simple_library.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    @Autowired
    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public BookDto mapToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setBook_name(book.getBook_name());
        dto.setEdition(book.getEdition());
        dto.setReleaseDate(book.getReleaseDate());
        List<String> authorNames = book.getAuthors()
                .stream().map(author -> author.getAuthor_name())
                .toList();
        dto.setAuthorName(authorNames);

        return dto;
    }

    @Transactional
    public BookDto getBookByID(Long id) {
        Book book = bookRepository.getBookByID(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource Not found: " + id)
        );
        return mapToDto(book);
    }

    @Transactional
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.getAllBooks();
        return books.stream()
                .map(book -> mapToDto(book))
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDto createNewBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);

        List<Author> authors = bookDto.getAuthorName().stream()
                .map(name -> authorRepository.getAuthorByName(name)
                        .orElseGet(() -> {
                            Author author = new Author();
                            author.setAuthor_name(name);
                            return authorRepository.save(author);
                        }))
                .toList();
        book.setAuthors(authors);

        return mapToDto(bookRepository.save(book));
    }

    @Transactional
    public BookDto updateBookByID(Long id, BookDto bookDto) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource Not found: " + id);
        }

        Book book = bookRepository.getBookByID(id).orElseThrow();
        book.setId(id);
        book.setBook_name(bookDto.getBook_name());
        book.setEdition(bookDto.getEdition());
        book.setReleaseDate(bookDto.getReleaseDate());

        List<Author> authors = bookDto.getAuthorName().stream()
                .map(name -> authorRepository.getAuthorByName(name)
                        .orElseGet(() -> {
                            Author author = new Author();
                            author.setAuthor_name(name);
                            return authorRepository.save(author);
                        }))
                .collect(Collectors.toList());
        book.setAuthors(authors);

        return mapToDto(bookRepository.save(book));


    }

    @Transactional
    public List<BookDto> getBookByField(String bookName, LocalDate date) {

        List<Book> books = bookRepository.getAllBooks().stream()
                .filter(book -> bookName == null || book.getBook_name().equalsIgnoreCase(bookName))
                .filter(book -> date == null || book.getReleaseDate().equals(date))
                .toList();

        return books.stream().map(book -> mapToDto(book)).toList();
    }

    @Transactional
    public List<BookDto> getBookAfterDate(LocalDate date) {
        List<Book> books = bookRepository.findBookAfterReleaseDate(date);
        return books.stream().map(book -> mapToDto(book)).toList();
    }


    public Boolean deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource Not found: " + id);
        }

        bookRepository.deleteById(id);
        return true;
    }
}
