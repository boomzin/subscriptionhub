package com.boomzin.subscriptionhub.db.repositiry;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.exception.ObjectNotFoundException;
import com.boomzin.subscriptionhub.common.search.SearchCriteria;
import com.boomzin.subscriptionhub.common.search.SearchCriteriaSettings;
import com.boomzin.subscriptionhub.db.generated.enums.SubscriptionStatus;
import com.boomzin.subscriptionhub.db.generated.tables.records.SubscriptionsRecord;
import com.boomzin.subscriptionhub.domain.subscription.Subscription;
import com.boomzin.subscriptionhub.domain.subscription.SubscriptionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.boomzin.subscriptionhub.common.search.JooqSearchUtils.*;
import static com.boomzin.subscriptionhub.db.generated.Tables.SUBSCRIPTIONS;


@Repository
public class JooqSubscriptionRepository implements SubscriptionRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;
    private static final Logger log = LogManager.getLogger(JooqSubscriptionRepository.class);

    private final RecordMapper<SubscriptionsRecord, Subscription> mapper = r -> new Subscription(
            r.getId(),
            r.getUserId(),
            r.getTypeId(),
            r.getStartDate(),
            r.getCreatedAt(),
            r.getEndDate(),
            r.getStatus()
    );


    public JooqSubscriptionRepository(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()
                .filter("id", SUBSCRIPTIONS.ID, UUID_EQ)
                .filter("userId", SUBSCRIPTIONS.USER_ID, UUID_EQ)
                .filter("typeId", SUBSCRIPTIONS.TYPE_ID, UUID_EQ)
                .filter("startDate", SUBSCRIPTIONS.START_DATE, DATE_EQ)
                .filter("startDateFrom", SUBSCRIPTIONS.START_DATE, DATE_FROM)
                .filter("startDateTo", SUBSCRIPTIONS.START_DATE, DATE_TO)
                .filter("endDate", SUBSCRIPTIONS.END_DATE, DATE_EQ)
                .filter("endDateFrom", SUBSCRIPTIONS.END_DATE, DATE_FROM)
                .filter("endDateTo", SUBSCRIPTIONS.END_DATE, DATE_TO)

                .order("endDate", SUBSCRIPTIONS.END_DATE)

                .build();
    }


    @Override
    public void create(Subscription permission) {
        SubscriptionsRecord record = db.newRecord(SUBSCRIPTIONS);
        fillRecord(record, permission);
        record.insert();
    }

    @Override
    public void update(Subscription permission) {
        SubscriptionsRecord record = db.fetchOptional(
                SUBSCRIPTIONS,
                SUBSCRIPTIONS.ID.eq(permission.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(permission.getId(), "Subscription"));

        log.info("Attempting to insert subscription: id={}, userId={}, typeId={}, startDate={}, createdAt={}, endDate={}, status={}",
                record.getId(), record.getUserId(), record.getTypeId(),
                record.getStartDate(), record.getCreatedAt(), record.getEndDate(),
                record.getStatus());
        fillRecord(record, permission);
        record.store();

    }

    @Override
    public Subscription findById(UUID subscriptionId) {
        return db
                .selectFrom(SUBSCRIPTIONS)
                .where(SUBSCRIPTIONS.ID.eq(subscriptionId))
                .fetchOptional(mapper)
                .orElseThrow(() -> new ObjectNotFoundException(subscriptionId, "Subscription"));
    }


    @Override
    public List<Subscription> getByUserId(UUID userId) {
        return db
                .selectFrom(SUBSCRIPTIONS)
                .where(SUBSCRIPTIONS.USER_ID.eq(userId))
                .fetch()
                .map(mapper);
    }

    @Override
    public PagedResult<Subscription> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(SUBSCRIPTIONS);
        criteria.apply(step);

        List<Subscription> list = step.fetch().map(record -> mapper.map((SubscriptionsRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(SUBSCRIPTIONS);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID permissionId) {
        db.deleteFrom(SUBSCRIPTIONS)
                .where(SUBSCRIPTIONS.ID.eq(permissionId))
                .execute();
    }

    private void fillRecord(SubscriptionsRecord record, Subscription permission) {
        record.setId(permission.getId());
        record.setUserId(permission.getUserId());
        record.setTypeId(permission.getTypeId());
        record.setStartDate(permission.getStartDate());
        record.setCreatedAt(permission.getCreatedAt());
        record.setEndDate(permission.getEndDate());
        record.setStatus(SubscriptionStatus.valueOf(permission.getStatus().getLiteral().toUpperCase()));

//        record.setStatus(permission.getStatus());
    }
}
