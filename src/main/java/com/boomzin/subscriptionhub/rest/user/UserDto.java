package com.boomzin.subscriptionhub.rest.user;

import com.boomzin.subscriptionhub.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;
    @NotBlank
    private String email;
    private LocalDateTime createdAt;
    @NotNull
    private UUID roleId;

    public UserDto(User user) {
        this(
                user.getId(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getRoleId());
    }
}
