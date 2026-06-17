package com.yashwanth.simple_library.service;

import com.yashwanth.simple_library.TestContainersConfiguration;
import com.yashwanth.simple_library.dto.BookDto;
import com.yashwanth.simple_library.entities.Author;
import com.yashwanth.simple_library.entities.Book;
import com.yashwanth.simple_library.exceptions.ResourceNotFoundException;
import com.yashwanth.simple_library.repositories.AuthorRepository;
import com.yashwanth.simple_library.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestContainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    private Book book;

    private BookDto bookDto;

    private Author author;

    @BeforeEach
    public void setUp(){
        author = Author.builder()
                .author_name("Yash")
                .build();

        book = Book.builder()
                .id(1L)
                .book_name("TVK")
                .edition(2026L)
                .releaseDate(LocalDate.now())
                .authors(List.of(author))
                .build();

        author.setBooks(List.of(book));

       // bookDto = modelMapper.map(book,BookDto.class);
        bookDto = BookDto.builder()
                .id(1L)
                .book_name("TVK")
                .edition(2026L)
                .releaseDate(LocalDate.now())
                .authorName(List.of("Yash"))
                .build();

    }

    @Test
    public void testGetBookByID_WhenValid(){
        when(bookRepository.getBookByID(anyLong())).thenReturn(Optional.of(book));

         BookDto dto = bookService.getBookByID(book.getId());

         assertThat(dto.getBook_name()).isEqualTo("TVK");
         verify(bookRepository,times(1)).getBookByID(1L);
    }

    @Test
    public void testGetBookByID_WhenInvalid(){
        when(bookRepository.getBookByID(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(()->bookService.getBookByID(book.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Resource Not found: " + book.getId());

        verify(bookRepository,times(1)).getBookByID(1L);
    }

    @Test
    public void testCreateNewBook_WhenValid(){
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(authorRepository.getAuthorByName(anyString())).thenReturn(Optional.of(author));

        BookDto dto = bookService.createNewBook(bookDto);

        assertThat(dto).isNotNull();
        assertThat(dto.getBook_name()).isEqualTo(book.getBook_name());
        verify(authorRepository,times(1)).getAuthorByName(author.getAuthor_name());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    public void testCreateNewBook_WhenValidEdgeCase(){
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(authorRepository.getAuthorByName(anyString())).thenReturn(Optional.empty());
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        BookDto dto = bookService.createNewBook(bookDto);

        assertThat(dto).isNotNull();
        assertThat(dto.getBook_name()).isEqualTo(book.getBook_name());
        verify(authorRepository,times(1)).getAuthorByName(author.getAuthor_name());
        verify(bookRepository).save(any(Book.class));
        verify(authorRepository).save(any(Author.class));
    }

    @Test
    public void testUpdateBookByID_IsValid(){
        when(bookRepository.existsById(book.getId())).thenReturn(true);
        when(bookRepository.getBookByID(book.getId())).thenReturn(Optional.of(book));
        bookDto.setBook_name("AITVK");
        bookDto.setAuthorName(List.of("Thalapathy Vijay"));
        author.setAuthor_name("Thalapathy Vijay");
        when(authorRepository.getAuthorByName(anyString())).thenReturn(Optional.empty());
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto dto = bookService.updateBookByID(book.getId(), bookDto);

        assertThat(dto).isNotNull();
        assertThat(dto.getBook_name()).isEqualTo(book.getBook_name());
        verify(authorRepository,times(1)).getAuthorByName(author.getAuthor_name());
        verify(bookRepository).save(any(Book.class));
        verify(authorRepository).save(any(Author.class));

    }


    @Test
    public void testDeleteBookById(){
        when(bookRepository.existsById(book.getId())).thenReturn(true);

        assertThatCode(()-> bookService.deleteBookById(book.getId()))
                .doesNotThrowAnyException();

        verify(bookRepository,times(1)).deleteById(book.getId());
    }

    @Test
    public void testDeleteBookById_Invalid(){
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(()-> bookService.deleteBookById(222L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Resource Not found: " + 222L);

        verify(bookRepository, never()).deleteById(anyLong());
    }

}