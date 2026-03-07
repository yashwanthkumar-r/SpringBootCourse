package com.codingshuttle.prod_ready_features.services;

import com.codingshuttle.prod_ready_features.Exception.ResourceNotFoundException;
import com.codingshuttle.prod_ready_features.dto.PostDto;
import com.codingshuttle.prod_ready_features.entities.PostEntity;
import com.codingshuttle.prod_ready_features.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class PostServiceImpl implements PostServices {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public List<PostDto> getAllPosts(){
        return postRepository.findAll().stream()
                .map(postEntity -> modelMapper.map(postEntity,PostDto.class))
                .collect(Collectors.toList());
    }

    public PostDto createPostEntity(PostDto inputData){
        PostEntity input = modelMapper.map(inputData,PostEntity.class);
        return modelMapper.map(postRepository.save(input),PostDto.class);
    }

    @Override
    public PostDto getPostById(Long id) {
        PostEntity data = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Resource not found with Id: "+ id));
        return modelMapper.map(data,PostDto.class);
    }

    @Override
    public PostDto updatePostEntity(PostDto input, Long id) {
            PostEntity data = postRepository.findById(id).orElseThrow(
                    ()-> new ResourceNotFoundException("Resource not found with Id: "+ id));
            data.setId(id);
            data.setTitle(input.getTitle());
            data.setDescription(input.getDescription());

            return modelMapper.map(postRepository.save(data),PostDto.class);
    }
}
