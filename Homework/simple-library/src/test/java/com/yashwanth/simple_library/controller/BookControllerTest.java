package com.yashwanth.simple_library.controller;

import com.yashwanth.simple_library.TestContainersConfiguration;
import com.yashwanth.simple_library.dto.BookDto;
import com.yashwanth.simple_library.entities.Author;
import com.yashwanth.simple_library.entities.Book;
import com.yashwanth.simple_library.repositories.AuthorRepository;
import com.yashwanth.simple_library.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebTestClient(timeout = "100000")
@Import(TestContainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Book book;

    private BookDto bookDto;

    private Author author;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        author = Author.builder()
                .author_name("Yash")
                .build();

        book = Book.builder()
                .book_name("TVK")
                .edition(2026L)
                .releaseDate(LocalDate.now())
                .authors(List.of(author))
                .build();

        author.setBooks(List.of(book));

        // bookDto = modelMapper.map(book,BookDto.class);
        bookDto = BookDto.builder()
                .book_name("TVK")
                .edition(2026L)
                .releaseDate(LocalDate.now())
                .authorName(List.of("Yash"))
                .build();

    }

    @Test
    public void testGetBookByID_isValid() {
        Book savedBook = bookRepository.save(book);

        webTestClient.get()
                .uri("/books/{id}", savedBook.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.book_name").isEqualTo(book.getBook_name())
                .jsonPath("$.data.edition").isEqualTo(book.getEdition());
    }

    @Test
    public void testGetBookByID_isInvalid() {
        webTestClient.get()
                .uri("/books/{id}", 111L)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testCreateNewBook_isValid(){
        webTestClient.post()
                .uri("/books/addBook")
                .bodyValue(bookDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.data.book_name").isEqualTo(book.getBook_name())
                .jsonPath("$.data.edition").isEqualTo(book.getEdition());
    }

    @Test
    public void testUpdateBookByID_isValid(){
        Book savedBook = bookRepository.save(book);
        bookDto.setBook_name("Owala");
        bookDto.setEdition(2025L);

        webTestClient.put()
                .uri("/books/{id}", savedBook.getId())
                .bodyValue(bookDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.book_name").isEqualTo(bookDto.getBook_name())
                .jsonPath("$.data.edition").isEqualTo(bookDto.getEdition());
    }

    @Test
    public void testDeleteBookById(){
        Book savedBook = bookRepository.save(book);

        webTestClient.delete()
                .uri("/books/{id}", savedBook.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data").isBoolean();
    }


}