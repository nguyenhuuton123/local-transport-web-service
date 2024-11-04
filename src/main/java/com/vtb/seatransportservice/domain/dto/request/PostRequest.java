package com.vtb.seatransportservice.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vtb.seatransportservice.domain.entity.Category;
import com.vtb.seatransportservice.domain.entity.Topic;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {
    private String name;
    private String description;
    private String titleImage;
    private String content;
    private LocalDateTime datePost;
    private String urlName;
    private String author;
    private String icon;
    private String keyword;
    private String tableId;
}
