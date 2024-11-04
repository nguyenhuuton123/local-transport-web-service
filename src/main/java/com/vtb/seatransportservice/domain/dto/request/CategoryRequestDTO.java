package com.vtb.seatransportservice.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequestDTO {
    private Long id;
    private String name;
    private String url_name;
}
