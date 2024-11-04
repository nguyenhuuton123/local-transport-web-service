package com.vtb.seatransportservice.service;

import com.vtb.seatransportservice.domain.dto.request.TopicRequestDTO;
import com.vtb.seatransportservice.domain.entity.Category;
import com.vtb.seatransportservice.domain.entity.Topic;
import com.vtb.seatransportservice.payload.ResponsePayload;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ITopicService {
    ResponsePayload getAllTopic();

    ResponsePayload getTopicByCategoryId(Long categoryId);

    ResponsePayload getTopicById(Long id);

    ResponsePayload createTopic(@Valid TopicRequestDTO topicRequestDTO, BindingResult result);

    ResponsePayload updateTopic(Long id, TopicRequestDTO topicRequestDTO);

    ResponsePayload deleteTopic(Long id);

    void deleteTopicByIds(List<Long> ids);
}
