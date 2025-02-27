package com.boomzin.subscriptionhub.domain.user;

import com.boomzin.subscriptionhub.common.data.PagedResult;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void create(User user);

    void update(User user);

    User findById(UUID userUuid);
    Optional<User> getByEmail(String email);

    PagedResult<User> search(Map<String, String> apiParams);

    void delete(UUID userUuid);
}
