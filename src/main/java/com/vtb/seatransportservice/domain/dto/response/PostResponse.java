package com.vtb.seatransportservice.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vtb.seatransportservice.domain.entity.Category;
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
public class PostResponse {
    private String name;
    private String categoryName;
    private Boolean isHidden;
    private LocalDateTime datePost;
}
