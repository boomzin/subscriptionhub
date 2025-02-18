package com.boomzin.subscriptionhub.domain.session;

import com.boomzin.subscriptionhub.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface SessionRepository {
    void create(Session session);

    void update(Session session);

    Session findById(UUID sessionUuid);

    PagedResult<Session> search(Map<String, String> apiParams);

    void delete(UUID sessionUuid);
}
