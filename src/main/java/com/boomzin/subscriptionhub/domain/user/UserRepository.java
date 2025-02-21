package com.boomzin.subscriptionhub.domain.user;

import com.boomzin.subscriptionhub.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface UserRepository {
    void create(User user);

    void update(User user);

    User findById(UUID userUuid);

    PagedResult<User> search(Map<String, String> apiParams);

    void delete(UUID userUuid);
}
