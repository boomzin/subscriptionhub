package com.boomzin.subscriptionhub.domain.subscriptiontype;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class SubscriptionTypeService {
    private final SubscriptionTypeRepository repository;

    public SubscriptionTypeService(SubscriptionTypeRepository repository) {
        this.repository = repository;
    }

    public SubscriptionType findById(UUID subscriptionTypeUuid) {
        return repository.findById(subscriptionTypeUuid);
    }

    public PagedResult<SubscriptionType> search(Map<String, String> apiParams) {
        return repository.search(apiParams);
    }

    public void update(SubscriptionType subscriptionType) {
        repository.update(subscriptionType);
    }

    public void create(SubscriptionType subscriptionType) {
        repository.create(subscriptionType);
    }

    public void delete(UUID subscriptionTypeUuid) {
        repository.delete(subscriptionTypeUuid);
    }

}
