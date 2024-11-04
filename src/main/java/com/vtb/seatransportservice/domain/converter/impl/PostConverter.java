package com.vtb.seatransportservice.domain.converter.impl;

import com.vtb.seatransportservice.domain.converter.IPostConverter;
import com.vtb.seatransportservice.domain.dto.request.PostRequest;
import com.vtb.seatransportservice.domain.dto.response.PostResponse;
import com.vtb.seatransportservice.domain.entity.Post;
import com.vtb.seatransportservice.domain.mapper.IPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostConverter implements IPostConverter {
    @Autowired
    private final IPostMapper postMapper;

    @Autowired
    public PostConverter(IPostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public List<PostResponse> mapToPostResponse(List<Post> posts) {
        return posts.stream()
                .map(postMapper::postToPostResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponse mapPostToResponse(Post post) {
        return postMapper.postToPostResponse(post);
    }

    @Override
    public Post postRequestToPost(PostRequest postRequest) {
        return postMapper.postRequestToPost(postRequest);
    }

    @Override
    public void updatePostFromRequest(PostRequest request, Post post) {
        postMapper.updatePostFromRequest(request, post);
    }
}
