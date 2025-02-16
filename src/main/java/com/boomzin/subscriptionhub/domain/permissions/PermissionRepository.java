package com.boomzin.subscriptionhub.domain.permissions;

import com.boomzin.subscriptionhub.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface PermissionRepository {
    void create(Permission permission);

    void update(Permission permission);

    Permission findById(UUID permissionUuid);

    PagedResult<Permission> search(Map<String, String> apiParams);

    void delete(UUID permissionUuid);
}
