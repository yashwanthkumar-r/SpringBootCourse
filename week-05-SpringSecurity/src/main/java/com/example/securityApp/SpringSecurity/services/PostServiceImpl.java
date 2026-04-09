package com.example.securityApp.SpringSecurity.services;

import com.example.securityApp.SpringSecurity.Exception.ResourceNotFoundException;
import com.example.securityApp.SpringSecurity.dto.PostDto;
import com.example.securityApp.SpringSecurity.dto.UserDto;
import com.example.securityApp.SpringSecurity.entities.PostEntity;
import com.example.securityApp.SpringSecurity.entities.Users;
import com.example.securityApp.SpringSecurity.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostServices {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDto.class))
                .collect(Collectors.toList());
    }

    public PostDto createPostEntity(PostDto inputData) {
        PostEntity input = modelMapper.map(inputData, PostEntity.class);
        return modelMapper.map(postRepository.save(input), PostDto.class);
    }

    @Override
    public PostDto getPostById(Long id) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("User {}",user);
        PostEntity data = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found with Id: " + id));
        return modelMapper.map(data, PostDto.class);
    }

    @Override
    public PostDto updatePostEntity(PostDto input, Long id) {
        PostEntity data = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found with Id: " + id));
        data.setId(id);
        data.setTitle(input.getTitle());
        data.setDescription(input.getDescription());

        return modelMapper.map(postRepository.save(data), PostDto.class);
    }
}
