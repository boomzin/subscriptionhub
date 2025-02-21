package com.boomzin.subscriptionhub.domain.session;

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
public class Session extends UuidEntity {
    private UUID userId;
    private String deviceId;
    private String token;
    private LocalDateTime lastActive;

    public Session(UUID id, UUID userId, String deviceId, String token, LocalDateTime lastActive) {
        super(id);
        this.userId = userId;
        this.deviceId = deviceId;
        this.token = token;
        this.lastActive = lastActive;
    }
}
