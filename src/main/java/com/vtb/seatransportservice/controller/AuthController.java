package com.vtb.seatransportservice.controller;

import com.vtb.seatransportservice.domain.dto.request.LoginRequest;
import com.vtb.seatransportservice.payload.ResponsePayload;
import com.vtb.seatransportservice.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final IAuthService IAuthService;

    @PostMapping("/login")
    public ResponseEntity<ResponsePayload> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        ResponsePayload responsePayload = IAuthService.login(loginRequest, response);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponsePayload> logout(HttpServletRequest request, HttpServletResponse response) {
        ResponsePayload responsePayload = IAuthService.logout(request, response);
        return ResponseEntity.status(responsePayload.getStatus()).body(responsePayload);
    }
}
