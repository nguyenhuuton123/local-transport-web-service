package com.vtb.seatransportservice.service;

import com.vtb.seatransportservice.domain.dto.request.LoginRequest;
import com.vtb.seatransportservice.payload.ResponsePayload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    ResponsePayload login(LoginRequest loginRequest, HttpServletResponse response);
    ResponsePayload logout(HttpServletRequest request, HttpServletResponse response);
}
