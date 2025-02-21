package com.boomzin.subscriptionhub.domain.user;

import com.boomzin.subscriptionhub.common.UuidEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends UuidEntity {
    private String email;
    private String passwordHash;
    private LocalDateTime createdAt;
    private UUID roleId;

    public User(UUID id, String email, String passwordHash, LocalDateTime createdAt, UUID roleId) {
        super(id);
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.roleId = roleId;
    }
}
