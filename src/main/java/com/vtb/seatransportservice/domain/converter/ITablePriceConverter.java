package com.vtb.seatransportservice.domain.converter;

import com.vtb.seatransportservice.domain.dto.request.TablePriceRequestDto;
import com.vtb.seatransportservice.domain.dto.request.UpdateTablePriceRequestDto;
import com.vtb.seatransportservice.domain.entity.TablePrice;

import java.util.List;

public interface ITablePriceConverter {
    TablePrice convertToEntity(TablePriceRequestDto dto);
    TablePrice updateEntityFromDto(UpdateTablePriceRequestDto dto, TablePrice entity);
}
