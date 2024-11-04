package com.vtb.seatransportservice.util;

import com.vtb.seatransportservice.payload.ResponsePayload;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BuildResponse {
    public ResponsePayload buildResponse(Object data, String message, HttpStatus status, String error) {
        return ResponsePayload.builder()
                .data(data)
                .message(message)
                .status(status)
                .error(error)
                .build();
    }
}
