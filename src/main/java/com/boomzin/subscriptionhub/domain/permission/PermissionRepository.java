package com.boomzin.subscriptionhub.domain.permission;

import com.boomzin.subscriptionhub.common.data.PagedResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PermissionRepository {
    void create(Permission permission);

    void update(Permission permission);

    Permission findById(UUID permissionId);

    List<Permission> findByRoleId(UUID roleId);

    PagedResult<Permission> search(Map<String, String> apiParams);

    void delete(UUID permissionId);
}
