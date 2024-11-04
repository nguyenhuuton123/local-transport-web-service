package com.vtb.seatransportservice.service.impl;

import com.vtb.seatransportservice.constant.AccountConstant;
import com.vtb.seatransportservice.domain.dto.request.LoginRequest;
import com.vtb.seatransportservice.domain.dto.response.LoginResponse;
import com.vtb.seatransportservice.domain.entity.Role;
import com.vtb.seatransportservice.domain.entity.User;
import com.vtb.seatransportservice.payload.ResponsePayload;
import com.vtb.seatransportservice.repository.RoleRepository;
import com.vtb.seatransportservice.repository.UserRepository;
import com.vtb.seatransportservice.security.JwtService;
import com.vtb.seatransportservice.service.IAuthService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    @Override
    public ResponsePayload login(LoginRequest loginRequest, HttpServletResponse response) {
        User user = modelMapper.map(loginRequest, User.class);
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponsePayload.builder()
                    .data(null)
                    .message(AccountConstant.LOGIN_FAIL_AUTHORIZATION + loginRequest.getUsername())
                    .status(HttpStatus.NOT_FOUND)
                    .build();

        }

        if (authentication.isAuthenticated() && isUserDeleteAndActive(authentication.getName())) {
            Role role = roleRepository.findRolesByUsername(authentication.getName());
            String accessToken = jwtService.generateToken(loginRequest.getUsername());
            LoginResponse loginResponse = modelMapper.map(user, LoginResponse.class);
            loginResponse.setAccessToken(accessToken);
            loginResponse.setRole(role.getName());
            return ResponsePayload.builder()
                    .data(loginResponse)
                    .message(AccountConstant.LOGIN_SUCCESS)
                    .status(HttpStatus.OK)
                    .build();
        }
        return ResponsePayload.builder()
                .data(null)
                .message(AccountConstant.LOGIN_FAIL)
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @Override
    public ResponsePayload logout(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                jwtService.invalidateToken(token);
                return ResponsePayload.builder()
                        .data(null)
                        .message(AccountConstant.LOGOUT_SUCCESS)
                        .status(HttpStatus.OK)
                        .build();
            } catch (JwtException e) {
                return ResponsePayload.builder()
                        .data(null)
                        .message(AccountConstant.LOGOUT_FAIL_INVALID_TOKEN)
                        .status(HttpStatus.UNAUTHORIZED)
                        .build();
            }
        }
        return ResponsePayload.builder()
                .data(null)
                .message(AccountConstant.LOGOUT_FAIL_NO_TOKEN)
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }


    public boolean isUserDeleteAndActive(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        boolean isActive = user.get().getIsActive();
        boolean isDelete = user.get().getIsDelete();
        if (isActive && !isDelete) {
            return true;
        }
        return false;
    }
}
