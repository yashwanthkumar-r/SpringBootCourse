package com.example.securityApp.SpringSecurity.util;

import com.example.securityApp.SpringSecurity.dto.PostDto;
import com.example.securityApp.SpringSecurity.entities.Users;
import com.example.securityApp.SpringSecurity.services.PostServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostServices postServices;

    public boolean isOwnerOfPost(Long postId){
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDto post = postServices.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }

}
