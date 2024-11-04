package com.vtb.seatransportservice.domain.converter.impl;

import com.vtb.seatransportservice.domain.converter.ITablePriceConverter;
import com.vtb.seatransportservice.domain.dto.request.TablePriceRequestDto;
import com.vtb.seatransportservice.domain.dto.request.UpdateTablePriceRequestDto;
import com.vtb.seatransportservice.domain.entity.TablePrice;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TablePriceConverter implements ITablePriceConverter {

    @Override
    public TablePrice convertToEntity(TablePriceRequestDto dto) {
        TablePrice tablePrice = new TablePrice();
        BeanUtils.copyProperties(dto, tablePrice);
        return tablePrice;
    }

    @Override
    public TablePrice updateEntityFromDto(UpdateTablePriceRequestDto dto, TablePrice entity) {
        if (entity != null) {
            BeanUtils.copyProperties(dto, entity);
        }
        return entity;
    }


}