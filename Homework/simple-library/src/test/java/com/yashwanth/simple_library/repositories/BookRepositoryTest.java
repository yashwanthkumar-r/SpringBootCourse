package com.yashwanth.simple_library.repositories;

import com.yashwanth.simple_library.TestContainersConfiguration;
import com.yashwanth.simple_library.entities.Author;
import com.yashwanth.simple_library.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestContainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    private Author author;

    @BeforeEach
    public void setUp(){
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
    }

    @Test
    void getBookByID_isValid() {
        Book savedBook = bookRepository.save(book);

        Optional<Book> book = bookRepository.getBookByID(savedBook.getId());

        assertThat(book).isNotEmpty();
        assertThat(book.get().getBook_name()).isEqualTo("TVK");
    }

    @Test
    void findBookAfterReleaseDate() {
        Book savedBook = bookRepository.save(book);

        List<Book> books = bookRepository.findBookAfterReleaseDate(LocalDate.of(1970,01,01));

        assertThat(books).isNotNull();
        assertThat(books.contains(savedBook));

    }
}