package com.boomzin.subscriptionhub.domain.subscription;

import com.boomzin.subscriptionhub.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface SubscriptionRepository {
    void create(Subscription subscription);

    void update(Subscription subscription);

    Subscription findById(UUID subscriptionUuid);

    PagedResult<Subscription> search(Map<String, String> apiParams);

    void delete(UUID subscriptionUuid);
}
