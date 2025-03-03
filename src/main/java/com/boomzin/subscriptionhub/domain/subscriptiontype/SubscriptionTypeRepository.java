package com.boomzin.subscriptionhub.domain.subscriptiontype;


import com.boomzin.subscriptionhub.common.data.PagedResult;

import java.util.Map;
import java.util.UUID;

public interface SubscriptionTypeRepository {
    void create(SubscriptionType subscriptionType);

    void update(SubscriptionType subscriptionType);

    SubscriptionType findById(UUID subscriptionTypeIid);

    PagedResult<SubscriptionType> search(Map<String, String> apiParams);

    void delete(UUID subscriptionTypeId);
}
