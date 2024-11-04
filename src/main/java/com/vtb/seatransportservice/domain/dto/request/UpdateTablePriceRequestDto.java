package com.vtb.seatransportservice.domain.dto.request;

import lombok.Data;

@Data
public class UpdateTablePriceRequestDto {
    private Long id;
    private String titleTable;
    private String contentTable;
}
