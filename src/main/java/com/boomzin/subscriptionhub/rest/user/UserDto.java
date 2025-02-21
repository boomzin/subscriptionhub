package com.boomzin.subscriptionhub.rest.user;

import com.boomzin.subscriptionhub.domain.user.User;
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
    private String email;
    private LocalDateTime createdAt;
    private UUID roleId;

    public UserDto(User user) {
        this(
                user.getId(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getRoleId());
    }
}
