package com.example.securityApp.SpringSecurity.controllers;

import com.example.securityApp.SpringSecurity.dto.PostDto;
import com.example.securityApp.SpringSecurity.services.PostServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostServices postServices;

    @GetMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postServices.getAllPosts());
    }

    @GetMapping("/{postId}")
    //@PreAuthorize("hasAnyRole('USER','ADMIN') OR hasAnyAuthority('POST_VIEW')")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postID)")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Long postID) {
        return ResponseEntity.ok(postServices.getPostById(postID));
    }

    @PostMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<PostDto> createPostEntity(@RequestBody PostDto input) {
        return new ResponseEntity<>(postServices.createPostEntity(input), HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<PostDto> updatePostEntity(@RequestBody PostDto input, @PathVariable("postId") Long id) {
        return ResponseEntity.ok(postServices.updatePostEntity(input, id));
    }
}
