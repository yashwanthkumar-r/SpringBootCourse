package com.example.securityApp.SpringSecurity.services;

import com.example.securityApp.SpringSecurity.dto.PostDto;

import java.util.List;

public interface PostServices {

    List<PostDto> getAllPosts();

    PostDto createPostEntity(PostDto inputData);

    PostDto getPostById(Long id);

    PostDto updatePostEntity(PostDto input, Long id);
}
