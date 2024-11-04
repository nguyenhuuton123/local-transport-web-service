package com.vtb.seatransportservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NonNull
    @NotBlank(message = "Username can not be blank")
    private String username;
    @NonNull
    @NotBlank(message = "Password can not be blank")
    private String password;
}
