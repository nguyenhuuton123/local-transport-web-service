package com.vtb.seatransportservice.controller.admin;

import com.vtb.seatransportservice.domain.dto.request.TopicRequestDTO;
import com.vtb.seatransportservice.payload.ResponsePayload;
import com.vtb.seatransportservice.repository.ICategoryRepository;
import com.vtb.seatransportservice.service.ITopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/topic")
public class TopicController {

    private final ITopicService topicService;

    private final ICategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<ResponsePayload> getAllTopic() {
        ResponsePayload responsePayload = topicService.getAllTopic();
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResponsePayload> getTopicByCategoryId(@PathVariable Long categoryId) {
        ResponsePayload responsePayload = topicService.getTopicByCategoryId(categoryId);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload> getTopicById(@PathVariable Long id) {
        ResponsePayload responsePayload = topicService.getTopicById(id);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @PostMapping
    public ResponseEntity<ResponsePayload> createTopic(@RequestBody TopicRequestDTO topicRequest, BindingResult result) {
        ResponsePayload responsePayload = topicService.createTopic(topicRequest, result);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload> updateTopic(@PathVariable Long id, @Valid @RequestBody TopicRequestDTO topicRequest) {
        ResponsePayload responsePayload = topicService.updateTopic(id, topicRequest);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePayload> deleteTopic(@PathVariable Long id) {
        ResponsePayload responsePayload = topicService.deleteTopic(id);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    List<Long> listTopicId;

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteCategories(@RequestBody List<TopicRequestDTO> deleteTopicRequest) {
        listTopicId = new ArrayList<>();
        for (TopicRequestDTO i : deleteTopicRequest) {
            listTopicId.add(i.getId());
        }
        topicService.deleteTopicByIds(listTopicId);
        return ResponseEntity.ok().build();
    }
}

