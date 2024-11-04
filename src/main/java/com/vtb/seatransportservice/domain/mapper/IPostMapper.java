package com.vtb.seatransportservice.domain.mapper;
import com.vtb.seatransportservice.domain.dto.request.PostRequest;
import com.vtb.seatransportservice.domain.dto.response.PostResponse;
import com.vtb.seatransportservice.domain.entity.Post;

public interface IPostMapper {
    Post postRequestToPost(PostRequest postRequest);
    PostResponse postToPostResponse(Post post);
    void updatePostFromRequest(PostRequest request, Post post);
}