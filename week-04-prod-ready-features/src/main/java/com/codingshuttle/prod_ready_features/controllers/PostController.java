package com.codingshuttle.prod_ready_features.controllers;

import com.codingshuttle.prod_ready_features.dto.PostDto;
import com.codingshuttle.prod_ready_features.services.PostServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostServices postServices;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postServices.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Long id) {
        return ResponseEntity.ok(postServices.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<PostDto> createPostEntity(@RequestBody PostDto input) {
        return new ResponseEntity<>(postServices.createPostEntity(input), HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePostEntity(@RequestBody PostDto input, @PathVariable("postId") Long id) {
        return ResponseEntity.ok(postServices.updatePostEntity(input, id));
    }
}
