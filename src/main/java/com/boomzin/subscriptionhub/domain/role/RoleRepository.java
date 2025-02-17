package com.boomzin.subscriptionhub.domain.role;

import com.boomzin.subscriptionhub.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface RoleRepository {
    void create(Role role);

    void update(Role role);

    Role findById(UUID roleUuid);

    PagedResult<Role> search(Map<String, String> apiParams);

    void delete(UUID roleUuid);
}
