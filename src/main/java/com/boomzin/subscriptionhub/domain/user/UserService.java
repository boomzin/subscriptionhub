package com.boomzin.subscriptionhub.domain.user;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findById(UUID userUuid) {
        return repository.findById(userUuid);
    }

    public PagedResult<User> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void create(User user) {
        repository.create(user);
    }

    public void delete(UUID userUuid) {
        repository.delete(userUuid);
    }

}
