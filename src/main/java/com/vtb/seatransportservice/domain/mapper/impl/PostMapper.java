package com.vtb.seatransportservice.domain.mapper.impl;
import com.vtb.seatransportservice.domain.entity.Topic;
import com.vtb.seatransportservice.domain.dto.request.PostRequest;
import com.vtb.seatransportservice.domain.dto.response.PostResponse;
import com.vtb.seatransportservice.domain.entity.Post;
import com.vtb.seatransportservice.domain.mapper.IPostMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;
import com.vtb.seatransportservice.domain.entity.Category;
@Component
public class PostMapper implements IPostMapper {
    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    public PostMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Post postRequestToPost(PostRequest postRequest) {
        return modelMapper.map(postRequest, Post.class);
    }

    @Override
    public PostResponse postToPostResponse(Post post) {
        PostResponse postResponse = modelMapper.map(post, PostResponse.class);
        Optional.ofNullable(post.getTopic())
                .map(Topic::getCategory)
                .map(Category::getName)
                .ifPresent(postResponse::setCategoryName);
        Optional.ofNullable(post.getTopic())
                .map(Topic::getName)
                .ifPresent(postResponse::setName);
        Optional.ofNullable(post.getIsHidden())
                .ifPresent(postResponse::setIsHidden);
        Optional.ofNullable(post.getDatePost())
                .ifPresent(postResponse::setDatePost);
        return postResponse;
    }

    @Override
    public void updatePostFromRequest(PostRequest request, Post post) {
        Optional.ofNullable(request.getName()).ifPresent(post::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(post::setDescription);
        Optional.ofNullable(request.getTitleImage()).ifPresent(post::setTitleImage);
        Optional.ofNullable(request.getContent()).ifPresent(post::setContent);
        Optional.ofNullable(request.getDatePost()).ifPresent(post::setDatePost);
        Optional.ofNullable(request.getUrlName()).ifPresent(post::setUrlName);
        Optional.ofNullable(request.getAuthor()).ifPresent(post::setAuthor);
        Optional.ofNullable(request.getIcon()).ifPresent(post::setIcon);
        Optional.ofNullable(request.getKeyword()).ifPresent(post::setKeyword);
        Optional.ofNullable(request.getTableId()).ifPresent(post::setTableId);
    }
}
