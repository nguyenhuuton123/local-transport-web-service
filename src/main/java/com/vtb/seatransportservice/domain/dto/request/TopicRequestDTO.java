package com.vtb.seatransportservice.domain.dto.request;

import lombok.Getter;

@Getter
public class TopicRequestDTO {
    private Long id;
    private String name;
    private Long categoryId;
    private String url_name;
    private String description;
    private String icon;
}
