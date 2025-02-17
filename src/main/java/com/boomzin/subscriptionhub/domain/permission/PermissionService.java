package com.boomzin.subscriptionhub.domain.permission;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class PermissionService {
    private final PermissionRepository repository;

    public PermissionService(PermissionRepository repository) {
        this.repository = repository;
    }

    public Permission findById(UUID permissionUuid) {
        return repository.findById(permissionUuid);
    }

    public PagedResult<Permission> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void update(Permission permission) {
        repository.update(permission);
    }

    public void create(Permission permission) {
        repository.create(permission);
    }

    public void delete(UUID permissionUuid) {
        repository.delete(permissionUuid);
    }

}
