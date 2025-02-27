package com.boomzin.subscriptionhub.rest.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class LoginRespDto {
    @NotBlank
    @JsonProperty("auth_token")
    String authToken;

    public LoginRespDto(String authToken) {
        this.authToken = authToken;
    }
}
