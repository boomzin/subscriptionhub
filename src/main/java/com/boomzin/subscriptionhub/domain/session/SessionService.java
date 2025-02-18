package com.boomzin.subscriptionhub.domain.session;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class SessionService {
    private final SessionRepository repository;

    public SessionService(SessionRepository repository) {
        this.repository = repository;
    }

    public Session findById(UUID sessionUuid) {
        return repository.findById(sessionUuid);
    }

    public PagedResult<Session> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void update(Session session) {
        repository.update(session);
    }

    public void create(Session session) {
        repository.create(session);
    }

    public void delete(UUID sessionUuid) {
        repository.delete(sessionUuid);
    }

}
