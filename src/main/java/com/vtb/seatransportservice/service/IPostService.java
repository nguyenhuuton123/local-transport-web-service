package com.vtb.seatransportservice.service;

import com.vtb.seatransportservice.domain.dto.request.PostRequest;
import com.vtb.seatransportservice.payload.ResponsePayload;
import org.springframework.data.domain.Pageable;

public interface IPostService {
    ResponsePayload findAllPost(Pageable pageable);
    ResponsePayload editPost(PostRequest postRequest, Long id);
    ResponsePayload createPost(PostRequest postRequest);
    ResponsePayload deletePost(Long id);
    ResponsePayload findPostDetailsById(Long id);
    ResponsePayload searchPostsByName(String name, Pageable pageable);
}
