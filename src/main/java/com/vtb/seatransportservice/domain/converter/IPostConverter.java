package com.vtb.seatransportservice.domain.converter;

import com.vtb.seatransportservice.domain.dto.request.PostRequest;
import com.vtb.seatransportservice.domain.dto.response.PostResponse;
import com.vtb.seatransportservice.domain.entity.Post;

import java.util.List;

public interface IPostConverter {
    List<PostResponse> mapToPostResponse(List<Post> posts);
    PostResponse mapPostToResponse(Post post);
    Post postRequestToPost(PostRequest postRequest);
    void updatePostFromRequest(PostRequest request, Post post);
}
