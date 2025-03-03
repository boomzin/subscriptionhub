package com.boomzin.subscriptionhub.domain.rolepermission;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class RolePermissionService {
    private final RolePermissionRepository repository;

    public RolePermissionService(RolePermissionRepository repository) {
        this.repository = repository;
    }

    public PagedResult<RolePermission> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void create(RolePermission role) {
        repository.create(role);
    }

    public void delete(UUID roleId, UUID permissionId) {
        repository.delete(roleId, permissionId);
    }

}
