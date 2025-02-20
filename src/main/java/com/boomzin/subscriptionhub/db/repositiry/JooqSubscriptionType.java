package com.boomzin.subscriptionhub.db.repositiry;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.exception.ObjectNotFoundException;
import com.boomzin.subscriptionhub.common.search.SearchCriteria;
import com.boomzin.subscriptionhub.common.search.SearchCriteriaSettings;
import com.boomzin.subscriptionhub.db.generated.tables.records.SubscriptionTypesRecord;
import com.boomzin.subscriptionhub.domain.subscriptiontype.SubscriptionType;
import com.boomzin.subscriptionhub.domain.subscriptiontype.SubscriptionTypeRepository;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.boomzin.subscriptionhub.db.generated.Tables.SUBSCRIPTION_TYPES;

@Repository
public class JooqSubscriptionType implements SubscriptionTypeRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;

    private final RecordMapper<SubscriptionTypesRecord, SubscriptionType> mapper = r -> new SubscriptionType(
            r.getId(),
            r.getName(),
            r.getDurationDays(),
            r.getPrice(),
            r.getFeatures()
    );


    public JooqSubscriptionType(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()

                .order("price", SUBSCRIPTION_TYPES.PRICE)

                .build();
    }


    @Override
    public void create(SubscriptionType subscriptionType) {
        SubscriptionTypesRecord record = new SubscriptionTypesRecord();
        fillRecord(record, subscriptionType);
        record.insert();
    }

    @Override
    public void update(SubscriptionType subscriptionType) {
        SubscriptionTypesRecord record = db.fetchOptional(
                SUBSCRIPTION_TYPES,
                SUBSCRIPTION_TYPES.ID.eq(subscriptionType.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(subscriptionType.getId(), "SubscriptionType"));


        fillRecord(record, subscriptionType);
        record.store();

    }

    @Override
    public SubscriptionType findById(UUID subscriptionTypeUuid) {
        return db
                .selectFrom(SUBSCRIPTION_TYPES)
                .where(SUBSCRIPTION_TYPES.ID.eq(subscriptionTypeUuid))
                .fetchOptional(mapper)
                .orElseThrow(() -> new ObjectNotFoundException(subscriptionTypeUuid, "SubscriptionType"));
    }

    @Override
    public PagedResult<SubscriptionType> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(SUBSCRIPTION_TYPES);
        criteria.apply(step);

        List<SubscriptionType> list = step.fetch().map(record -> mapper.map((SubscriptionTypesRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(SUBSCRIPTION_TYPES);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID subscriptionTypeUuid) {
        db.deleteFrom(SUBSCRIPTION_TYPES)
                .where(SUBSCRIPTION_TYPES.ID.eq(subscriptionTypeUuid))
                .execute();
    }

    private void fillRecord(SubscriptionTypesRecord record, SubscriptionType subscriptionType) {
        record.setId(subscriptionType.getId());
        record.setName(subscriptionType.getName());
        record.setDurationDays(subscriptionType.getDurationDays());
        record.setPrice(subscriptionType.getPrice());
        record.setFeatures(subscriptionType.getFeature());
    }
}
