package com.boomzin.subscriptionhub.domain.rolepermission;

import com.boomzin.subscriptionhub.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface RolePermissionRepository {
    void create(RolePermission rolePermission);

    PagedResult<RolePermission> search(Map<String, String> apiParams);

    void delete(UUID roleId, UUID permissionId);
}
