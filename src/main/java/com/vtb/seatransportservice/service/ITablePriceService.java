package com.vtb.seatransportservice.service;

import com.vtb.seatransportservice.domain.dto.request.TablePriceRequestDto;
import com.vtb.seatransportservice.domain.dto.request.UpdateTablePriceRequestDto;
import com.vtb.seatransportservice.payload.ResponsePayload;

public interface ITablePriceService {

    ResponsePayload saveTablePrice(TablePriceRequestDto tablePriceRequestDto);

    ResponsePayload getTablePricebyId (Long id);

    ResponsePayload updateTablePrice(UpdateTablePriceRequestDto updateTablePriceRequestDto);

    ResponsePayload deleteTablePriceById(Long id);

    ResponsePayload getAllTablePrices();

}
    