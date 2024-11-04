package com.vtb.seatransportservice.service.impl;

import com.vtb.seatransportservice.constant.PostConstant;
import com.vtb.seatransportservice.domain.converter.IPostConverter;
import com.vtb.seatransportservice.domain.dto.request.PostRequest;
import com.vtb.seatransportservice.domain.dto.response.PostResponse;
import com.vtb.seatransportservice.domain.entity.Post;
import com.vtb.seatransportservice.payload.ResponsePayload;
import com.vtb.seatransportservice.repository.IPostRepository;
import com.vtb.seatransportservice.service.IPostService;
import com.vtb.seatransportservice.util.BuildResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final IPostRepository postRepository;
    private final IPostConverter postConverter;
    private final BuildResponse buildResponse;
    @Override
    public ResponsePayload findAllPost(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostResponse> postResponses = postConverter.mapToPostResponse(posts.getContent());
        return buildResponse.buildResponse(postResponses, PostConstant.POST_RETRIEVED_SUCCESSFULLY, HttpStatus.OK, null);
    }

    @Override
    public ResponsePayload findPostDetailsById(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()){
            PostResponse postResponse = postConverter.mapPostToResponse(postOptional.get());
            return buildResponse.buildResponse(postResponse, PostConstant.POST_RETRIEVED_SUCCESSFULLY, HttpStatus.OK, null);
        } else {
            return buildResponse.buildResponse(null, PostConstant.POST_NOT_FOUND, HttpStatus.NOT_FOUND, PostConstant.POST_NOT_FOUND);
        }
    }

    @Override
    public ResponsePayload searchPostsByName(String name, Pageable pageable) {
        List<Post> posts = postRepository.searchPostsByName(name);
        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());
        List<PostResponse> postResponses = postConverter.mapToPostResponse(postPage.getContent());
        return buildResponse.buildResponse(postResponses, PostConstant.POST_RETRIEVED_SUCCESSFULLY, HttpStatus.OK, null);
    }

    @Override
    public ResponsePayload editPost(PostRequest postRequest, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currenUsername = authentication.getName();
        try {
            Optional<Post> postOptional = postRepository.findById(id);
            if (postOptional.isEmpty()){
                return buildResponse.buildResponse(null, PostConstant.POST_NOT_FOUND, HttpStatus.OK, PostConstant.POST_NOT_FOUND);
            }
            Post post = postOptional.get();
            if (post.getAuthor().equals(currenUsername)){
                return buildResponse.buildResponse(null, "Unauthorized to edit this post", HttpStatus.BAD_REQUEST, "Unauthorized to edit this post");
            }
            postConverter.updatePostFromRequest(postRequest, post);
            postRepository.save(post);
            return buildResponse.buildResponse(postConverter.mapPostToResponse(post), PostConstant.POST_UPDATED_SUCCESSFULLY, HttpStatus.OK, PostConstant.POST_UPDATED_SUCCESSFULLY);
        } catch (SecurityException e) {
            return buildResponse.buildResponse(null, PostConstant.FAILED_TO_UPDATE_POST, HttpStatus.UNAUTHORIZED, PostConstant.FAILED_TO_UPDATE_POST);
        }
    }

    @Override
    public ResponsePayload createPost(PostRequest postRequest) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String currenUsername = authentication.getName();
        try {
            Post post = postConverter.postRequestToPost(postRequest);
            post.setAuthor(currenUsername);
            post.setId(null);
            post.setDatePost(LocalDateTime.now());
            postRepository.save(post);
            PostResponse postResponse = postConverter.mapPostToResponse(post);
            return buildResponse.buildResponse(postResponse, PostConstant.POST_CREATED_SUCCESSFULLY, HttpStatus.CREATED, null);
        } catch (SecurityException e) {
            return buildResponse.buildResponse(null, PostConstant.FAILED_TO_CREATE_POST, HttpStatus.BAD_REQUEST, PostConstant.FAILED_TO_CREATE_POST);
        }
    }

    @Override
    public ResponsePayload deletePost(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return buildResponse.buildResponse(null, "Unauthorized", HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        String currentUsername = authentication.getName();
        try {
            Optional<Post> postOptional = postRepository.findById(id);
            if (postOptional.isEmpty()) {
                return buildResponse.buildResponse(null, PostConstant.POST_NOT_FOUND, HttpStatus.NOT_FOUND, PostConstant.POST_NOT_FOUND);
            }
            Post post = postOptional.get();
            if (post.getAuthor().equals(currentUsername)) {
                return buildResponse.buildResponse(null, "Unauthorized to delete this post", HttpStatus.FORBIDDEN, "Unauthorized to delete this post");
            }
            post.setIsDeleted(true);
            postRepository.save(post);
            return buildResponse.buildResponse(null, PostConstant.POST_DELETED_SUCCESSFULLY, HttpStatus.OK, PostConstant.POST_DELETED_SUCCESSFULLY);
        } catch (SecurityException e) {
            return buildResponse.buildResponse(null, PostConstant.FAILED_TO_DELETE_POST, HttpStatus.BAD_REQUEST, PostConstant.FAILED_TO_DELETE_POST);
        }
    }

}