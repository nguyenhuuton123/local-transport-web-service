package com.vtb.seatransportservice.controller;

import com.vtb.seatransportservice.domain.dto.request.TablePriceRequestDto;
import com.vtb.seatransportservice.domain.dto.request.UpdateTablePriceRequestDto;
import com.vtb.seatransportservice.payload.ResponsePayload;
import com.vtb.seatransportservice.service.impl.TablePriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequiredArgsConstructor
@RequestMapping("/api/tables")
public class TablePriceController {
    private final TablePriceService tablePriceService;

    @PostMapping
    private ResponseEntity<ResponsePayload> saveNewTablePrice(@RequestBody TablePriceRequestDto tablePriceRequestDto) {
        ResponsePayload responsePayload = tablePriceService.saveTablePrice(tablePriceRequestDto);
        return new ResponseEntity<>(responsePayload, responsePayload.getStatus());
    }
    @GetMapping("{id}")
    private ResponseEntity<ResponsePayload> getTablePriceById(@PathVariable Long id) {
        ResponsePayload responsePayload = tablePriceService.getTablePricebyId(id);
        return new ResponseEntity<>(responsePayload, responsePayload.getStatus());
    }
    @PutMapping
    private ResponseEntity<ResponsePayload> updateTablePrice(@RequestBody UpdateTablePriceRequestDto updateTablePriceRequestDto) {
        ResponsePayload responsePayload = tablePriceService.updateTablePrice(updateTablePriceRequestDto);
        return new ResponseEntity<>(responsePayload, responsePayload.getStatus());
    }

    @DeleteMapping("{id}")
    private ResponseEntity<ResponsePayload> deleteTablePriceById(@PathVariable Long id) {
        ResponsePayload responsePayload = tablePriceService.deleteTablePriceById(id);
        return new ResponseEntity<>(responsePayload, responsePayload.getStatus());
    }
    @GetMapping
    private ResponseEntity<ResponsePayload> getAllTablePrices() {
        ResponsePayload responsePayload = tablePriceService.getAllTablePrices();
        return new ResponseEntity<>(responsePayload, responsePayload.getStatus());
    }
}
