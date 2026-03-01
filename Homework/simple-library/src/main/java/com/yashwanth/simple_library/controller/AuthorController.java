package com.yashwanth.simple_library.controller;

import com.yashwanth.simple_library.dto.AuthorDto;
import com.yashwanth.simple_library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping({"/{authorId}"})
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("authorId") Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping({"/name/{authorName}"})
    public ResponseEntity<AuthorDto> getAuthorByName(@PathVariable("authorName") String name) {
        return ResponseEntity.ok(authorService.getAuthorByName(name));
    }

    @DeleteMapping({"/{authorId}"})
    public ResponseEntity<Boolean> deleteAuthorById(@PathVariable("authorId") Long id) {
        return ResponseEntity.ok(authorService.deleteAuthorById(id));
    }

}
