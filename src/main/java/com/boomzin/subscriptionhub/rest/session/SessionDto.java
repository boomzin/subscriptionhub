package com.boomzin.subscriptionhub.rest.session;

import com.boomzin.subscriptionhub.domain.session.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    private UUID id;
    private UUID userId;
    private String deviceId;
    private String token;
    private LocalDateTime lastActive;

    public SessionDto(Session session) {
        this(
                session.getId(),
                session.getUserId(),
                session.getDeviceId(),
                session.getToken(),
                session.getLastActive());
    }
}
