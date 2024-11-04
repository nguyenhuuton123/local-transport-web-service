package com.vtb.seatransportservice.service.impl;

import com.vtb.seatransportservice.domain.converter.ITablePriceConverter;
import com.vtb.seatransportservice.domain.dto.request.TablePriceRequestDto;
import com.vtb.seatransportservice.domain.dto.request.UpdateTablePriceRequestDto;
import com.vtb.seatransportservice.domain.entity.TablePrice;
import com.vtb.seatransportservice.payload.ResponsePayload;
import com.vtb.seatransportservice.repository.ITablePriceRepository;
import com.vtb.seatransportservice.service.ITablePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TablePriceService implements ITablePriceService {
    @Autowired
    private ITablePriceRepository tableRepository;
    @Autowired
    private ITablePriceConverter tablePriceConverter;
    @Override
    public ResponsePayload saveTablePrice(TablePriceRequestDto tablePriceRequestDto) {
        try {
            TablePrice tablePrice = tablePriceConverter.convertToEntity(tablePriceRequestDto);
            TablePrice response = tableRepository.save(tablePrice);
            return ResponsePayload.builder()
                    .data(response)
                    .message("Save table success")
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            return ResponsePayload.builder()
                    .message("Save table fail")
                    .status(HttpStatus.BAD_REQUEST)
                    .error(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponsePayload getTablePricebyId(Long id) {
        try {
            TablePrice tablePrice = tableRepository.findById(id).orElse(null);
            if (tablePrice != null) {
                return ResponsePayload.builder()
                        .data(tablePrice)
                        .message("Get table with id: " + id + " success")
                        .status(HttpStatus.OK)
                        .build();
            } else {
                return ResponsePayload.builder()
                        .message("Table not found")
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
        }catch (Exception e) {
            return ResponsePayload.builder()
                    .message("Get table fail")
                    .status(HttpStatus.BAD_REQUEST)
                    .error(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponsePayload updateTablePrice(UpdateTablePriceRequestDto updateTablePriceRequestDto) {
        try {
            TablePrice tablePrice = tableRepository.findById(updateTablePriceRequestDto.getId()).orElse(null);
            if (tablePrice != null) {
                tablePrice = tablePriceConverter.updateEntityFromDto(updateTablePriceRequestDto, tablePrice);
                tableRepository.save(tablePrice);
                return ResponsePayload.builder()
                        .data(tablePrice)
                        .message("Update table success")
                        .status(HttpStatus.OK)
                        .build();
            } else {
                return ResponsePayload.builder()
                        .message("Table not found")
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
        } catch (Exception e) {
            return ResponsePayload.builder()
                    .message("Update table fail")
                    .status(HttpStatus.BAD_REQUEST)
                    .error(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponsePayload deleteTablePriceById(Long id) {
        try {
            tableRepository.deleteById(id);
            return ResponsePayload.builder()
                    .status(HttpStatus.OK)
                    .message("Delete table with id: " + id + "successful")
                    .build();
        } catch (Exception e) {
            return ResponsePayload.builder()
                    .message("Delete table fail")
                    .status(HttpStatus.BAD_REQUEST)
                    .error(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponsePayload getAllTablePrices() {
        List<TablePrice> tablePrices = tableRepository.findAll();
        return ResponsePayload.builder()
                .data(tablePrices)
                .message("Get All Table Prices")
                .status(HttpStatus.OK)
                .build();
    }
}
