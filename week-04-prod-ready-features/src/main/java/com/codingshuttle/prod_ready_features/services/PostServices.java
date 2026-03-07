package com.codingshuttle.prod_ready_features.services;

import com.codingshuttle.prod_ready_features.dto.PostDto;
import com.codingshuttle.prod_ready_features.entities.PostEntity;

import java.util.List;

public interface PostServices {

    List<PostDto> getAllPosts();

    PostDto createPostEntity(PostDto inputData);

    PostDto getPostById(Long id);

    PostDto updatePostEntity(PostDto input, Long id);
}
