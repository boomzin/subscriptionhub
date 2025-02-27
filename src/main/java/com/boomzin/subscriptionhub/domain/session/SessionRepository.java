package com.boomzin.subscriptionhub.domain.session;

import com.boomzin.subscriptionhub.common.data.PagedResult;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository {
    void create(Session session);

    void update(Session session);

    Session findById(UUID sessionUuid);

    Optional<Session> getByUserId(UUID userId);

    PagedResult<Session> search(Map<String, String> apiParams);

    void delete(UUID sessionUuid);
}
