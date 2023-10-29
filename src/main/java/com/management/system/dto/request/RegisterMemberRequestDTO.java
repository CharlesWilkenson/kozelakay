package com.management.system.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.Instant;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterMemberRequestDTO{

    private Long id;

    @NotEmpty(message = "The username must not be null")
    private String username;

    @NotEmpty(message = "The password must not be null")
    private String password;

    @NotEmpty(message = "The rePassword must not be null")
    private String rePassword;

    @NotEmpty(message = "The email must not be null")
    private String email;

}
