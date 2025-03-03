package com.boomzin.subscriptionhub.domain.subscription;

import com.boomzin.subscriptionhub.common.data.PagedResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface SubscriptionRepository {
    void create(Subscription subscription);

    void update(Subscription subscription);

    Subscription findById(UUID subscriptionId);

    List<Subscription> getByUserId(UUID userId);

    PagedResult<Subscription> search(Map<String, String> apiParams);

    void delete(UUID subscriptionId);
}
