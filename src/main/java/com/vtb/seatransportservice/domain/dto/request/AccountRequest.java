package com.vtb.seatransportservice.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AccountRequest {
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String phone;
    private String role;
    private Boolean isDelete;
    private Boolean isActive;
}
