package com.vtb.seatransportservice.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicResponseDTO {
    private Long id;
    private String name;
    private String url_name;
    private String description;
    private String icon;
    private Long categoryId;
}
