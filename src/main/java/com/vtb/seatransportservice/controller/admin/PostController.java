package com.vtb.seatransportservice.controller.admin;

import com.vtb.seatransportservice.domain.dto.request.PostRequest;
import com.vtb.seatransportservice.payload.ResponsePayload;
import com.vtb.seatransportservice.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final IPostService postService;

    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponsePayload> updatePost(@RequestBody PostRequest postRequest, @PathVariable Long id) {
        ResponsePayload responsePayload = postService.editPost(postRequest,id);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponsePayload> deletePost(@PathVariable Long id) {
        ResponsePayload responsePayload = postService.deletePost(id);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponsePayload> createPost(@RequestBody PostRequest postRequest) {
        ResponsePayload responsePayload = postService.createPost(postRequest);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ResponsePayload> getPost(@PathVariable Long id) {
        ResponsePayload responsePayload = postService.findPostDetailsById(id);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @GetMapping
    public ResponseEntity<ResponsePayload> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        ResponsePayload responsePayload = postService.findAllPost(PageRequest.of(page, size));
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponsePayload> searchPostsByName(@RequestParam String name, @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        ResponsePayload responsePayload = postService.searchPostsByName(name, PageRequest.of(page, size));
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

}
