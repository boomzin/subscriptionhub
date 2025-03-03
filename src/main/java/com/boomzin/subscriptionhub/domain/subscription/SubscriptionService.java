package com.boomzin.subscriptionhub.domain.subscription;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class SubscriptionService {
    private final SubscriptionRepository repository;

    public SubscriptionService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public Subscription findById(UUID subscriptionId) {
        return repository.findById(subscriptionId);
    }

    public PagedResult<Subscription> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void update(Subscription subscription) {
        repository.update(subscription);
    }

    public void create(Subscription subscription) {
        repository.create(subscription);
    }

    public void delete(UUID subscriptionId) {
        repository.delete(subscriptionId);
    }

}
