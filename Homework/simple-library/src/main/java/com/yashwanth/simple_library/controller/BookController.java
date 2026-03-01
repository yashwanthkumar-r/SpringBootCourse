package com.yashwanth.simple_library.controller;

import com.yashwanth.simple_library.dto.BookDto;
import com.yashwanth.simple_library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBookByID(@PathVariable(name = "bookId") Long id) {
        return ResponseEntity.ok(bookService.getBookByID(id));
    }

    @PostMapping("/addBook")
    public ResponseEntity<BookDto> createNewBook(@RequestBody BookDto book, Long id) {
        return new ResponseEntity<>(bookService.createNewBook(book), HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDto> updateBookByID(@PathVariable(name = "bookId") Long id, @RequestBody BookDto book) {
        return new ResponseEntity<>(bookService.updateBookByID(id, book), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Boolean> deleteBookById(@PathVariable(name = "bookId") Long id) {
        return ResponseEntity.ok(bookService.deleteBookById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<BookDto>> getBookByField(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) LocalDate date) {
        return ResponseEntity.ok(bookService.getBookByField(bookName, date));
    }

    @GetMapping("/afterDate/{date}")
    public ResponseEntity<List<BookDto>> getBookAfterDate(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(bookService.getBookAfterDate(date));
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBook() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }


}
