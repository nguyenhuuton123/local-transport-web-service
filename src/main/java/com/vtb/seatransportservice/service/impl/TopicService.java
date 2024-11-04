package com.vtb.seatransportservice.service.impl;

import com.vtb.seatransportservice.domain.dto.request.TopicRequestDTO;
import com.vtb.seatransportservice.domain.dto.response.TopicResponseDTO;
import com.vtb.seatransportservice.domain.entity.Category;
import com.vtb.seatransportservice.domain.entity.Topic;
import com.vtb.seatransportservice.payload.ResponsePayload;
import com.vtb.seatransportservice.repository.ICategoryRepository;
import com.vtb.seatransportservice.repository.ITopicRepository;
import com.vtb.seatransportservice.service.ITopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Validated
public class TopicService implements ITopicService {

    private final ITopicRepository topicRepository;

    private final ModelMapper modelMapper;

    private final ICategoryRepository categoryRepository;

    @Override
    public ResponsePayload getAllTopic() {
        List<Topic> topics = topicRepository.findAll();
        List<TopicResponseDTO> topicResponseDTOs = topics.stream()
                .map(topic -> modelMapper.map(topic, TopicResponseDTO.class))
                .collect(Collectors.toList());
        return ResponsePayload.builder()
                .data(topicResponseDTOs)
                .message("Fetch all subcategories successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponsePayload getTopicByCategoryId(Long categoryId) {
        List<Topic> topics = topicRepository.findAllByDeletedFalseAndCategoryId(categoryId);
        List<TopicResponseDTO> topicResponseDTOs = topics.stream()
                .map(topic -> modelMapper.map(topic, TopicResponseDTO.class))
                .collect(Collectors.toList());
        return ResponsePayload.builder()
                .data(topicResponseDTOs)
                .message("Fetch subcategories by category ID successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponsePayload getTopicById(Long id) {
        Topic topic = topicRepository.findById(id).orElse(null);
        if (topic != null) {
            TopicResponseDTO topicResponseDTO = modelMapper.map(topic, TopicResponseDTO.class);
            return ResponsePayload.builder()
                    .data(topicResponseDTO)
                    .message("Fetch topic by ID successfully")
                    .status(HttpStatus.OK)
                    .build();
        }
        return ResponsePayload.builder()
                .data(null)
                .message("Topic not found")
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @Override
    public ResponsePayload createTopic(@Valid TopicRequestDTO topicRequestDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponsePayload.builder()
                    .data(errors)
                    .message(null)
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        Category category = categoryRepository.findById(topicRequestDTO.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponsePayload.builder()
                    .data(null)
                    .message("Category not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        if (Objects.equals(category.getName(), topicRequestDTO.getName())) {
            return ResponsePayload.builder()
                    .data(null)
                    .message("Topic name cannot be the same as category name")
                    .status(HttpStatus.CONFLICT)
                    .build();
        }
        if (topicRepository.existsByDeletedFalseAndNameAndCategoryId(topicRequestDTO.getName(), topicRequestDTO.getCategoryId())) {
            return ResponsePayload.builder()
                    .data(null)
                    .message("Duplicate topic name")
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        if (topicRepository.existsByDeletedFalseAndUrlNameAndCategoryId(topicRequestDTO.getUrl_name(), topicRequestDTO.getCategoryId())) {
            return ResponsePayload.builder()
                    .data(null)
                    .message("Duplicate URL name")
                    .status(HttpStatus.CONFLICT)
                    .build();
        }
        Topic topic = new Topic();
        topic.setName(topicRequestDTO.getName());
        topic.setCategory(category);
        topic.setUrl_name(topicRequestDTO.getUrl_name());
        topic.setDescription(topicRequestDTO.getDescription());
        topic.setIcon(topicRequestDTO.getIcon());
        Topic savedTopic = topicRepository.save(topic);
        TopicResponseDTO topicResponseDTO = modelMapper.map(savedTopic, TopicResponseDTO.class);
        return ResponsePayload.builder()
                .data(topicResponseDTO)
                .message("Create topic successfully")
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ResponsePayload updateTopic(Long id, TopicRequestDTO topicRequestDTO) {
        Category category = categoryRepository.findById(topicRequestDTO.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponsePayload.builder()
                    .data(null)
                    .message("Category not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        Topic existingTopic = topicRepository.findById(id).orElse(null);
        if (existingTopic == null) {
            return ResponsePayload.builder()
                    .data(null)
                    .message("Topic not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        // Check if the name is being changed
        if (!existingTopic.getName().equals(topicRequestDTO.getName()) &&
                topicRepository.existsByDeletedFalseAndNameExceptIdAndCategoryId(topicRequestDTO.getName(), id, topicRequestDTO.getCategoryId())) {
            return ResponsePayload.builder()
                    .data(null)
                    .message("Duplicate topic name")
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        // Check if the url_name is being changed
        if (!existingTopic.getUrl_name().equals(topicRequestDTO.getUrl_name()) &&
                topicRepository.existsByDeletedFalseAndUrlNameExceptIdAndCategoryId(topicRequestDTO.getUrl_name(), id, topicRequestDTO.getCategoryId())) {
            return ResponsePayload.builder()
                    .data(null)
                    .message("Duplicate URL name")
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        existingTopic.setName(topicRequestDTO.getName());
        existingTopic.setCategory(category);
        existingTopic.setUrl_name(topicRequestDTO.getUrl_name());
        existingTopic.setIcon(topicRequestDTO.getIcon());
        existingTopic.setDescription(topicRequestDTO.getDescription());

        Topic updatedTopic = topicRepository.save(existingTopic);
        TopicResponseDTO topicResponseDTO = modelMapper.map(updatedTopic, TopicResponseDTO.class);

        return ResponsePayload.builder()
                .data(topicResponseDTO)
                .message("Update topic successfully")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponsePayload deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id).orElse(null);
        if (topic != null && !topic.getDeleted()) {
            topic.setDeleted(true);
            topicRepository.save(topic);
            return ResponsePayload.builder()
                    .data(null)
                    .message("topic deleted successfully")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            return ResponsePayload.builder()
                    .data(null)
                    .message("topic not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    public void deleteTopicByIds(List<Long> ids) {
        List<Topic> topics = topicRepository.findAllById(ids);
        for (Topic topic : topics) {
            topic.setDeleted(true);
        }
        topicRepository.saveAll(topics);
    }
}


