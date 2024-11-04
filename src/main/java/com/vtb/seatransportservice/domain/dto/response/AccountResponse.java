package com.vtb.seatransportservice.domain.dto.response;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String role;
    private LocalDate lastLogin;
}
