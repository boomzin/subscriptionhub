package com.boomzin.subscriptionhub.domain.role;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class RoleService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role findById(UUID roleId) {
        return repository.findById(roleId);
    }

    public PagedResult<Role> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void update(Role role) {
        repository.update(role);
    }

    public void create(Role role) {
        repository.create(role);
    }

    public void delete(UUID roleId) {
        repository.delete(roleId);
    }

}
