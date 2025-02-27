package com.boomzin.subscriptionhub.rest.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginDto {
    @NotBlank
    String username;
    @NotBlank
    String password;

}
