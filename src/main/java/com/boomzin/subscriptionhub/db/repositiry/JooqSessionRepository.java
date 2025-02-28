package com.boomzin.subscriptionhub.db.repositiry;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.exception.ObjectNotFoundException;
import com.boomzin.subscriptionhub.common.search.SearchCriteria;
import com.boomzin.subscriptionhub.common.search.SearchCriteriaSettings;
import com.boomzin.subscriptionhub.db.generated.tables.records.SessionsRecord;
import com.boomzin.subscriptionhub.domain.session.Session;
import com.boomzin.subscriptionhub.domain.session.SessionRepository;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.boomzin.subscriptionhub.common.search.JooqSearchUtils.STR_LIKE_IC;
import static com.boomzin.subscriptionhub.common.search.JooqSearchUtils.UUID_EQ;
import static com.boomzin.subscriptionhub.db.generated.Tables.SESSIONS;

@Repository
public class JooqSessionRepository implements SessionRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;

    private final RecordMapper<SessionsRecord, Session> mapper = r -> new Session(
            r.getId(),
            r.getUserId(),
            r.getDeviceId(),
            r.getToken(),
            r.getLastActive()
    );


    public JooqSessionRepository(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()
                .filter("id", SESSIONS.ID, UUID_EQ)
                .filter("userId", SESSIONS.USER_ID, UUID_EQ)
                .filter("deviceId", SESSIONS.DEVICE_ID, STR_LIKE_IC)
                .filter("token", SESSIONS.DEVICE_ID, STR_LIKE_IC)

                .order("lastActive", SESSIONS.LAST_ACTIVE)

                .build();
    }


    @Override
    public void create(Session session) {
        SessionsRecord record = db.newRecord(SESSIONS);
        fillRecord(record, session);
        record.insert();
    }

    @Override
    public void update(Session session) {
        SessionsRecord record = db.fetchOptional(
                SESSIONS,
                SESSIONS.ID.eq(session.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(session.getId(), "Session"));


        fillRecord(record, session);
        record.store();

    }

    @Override
    public Session findById(UUID sessionId) {
        return db
                .selectFrom(SESSIONS)
                .where(SESSIONS.ID.eq(sessionId))
                .fetchOptional(mapper)
                .orElseThrow(() -> new ObjectNotFoundException(sessionId, "Session"));
    }

    @Override
    public Optional<Session> findByToken(String token) {
        return db
                .selectFrom(SESSIONS)
                .where(SESSIONS.TOKEN.eq(token))
                .fetchOptional(mapper);
    }

    @Override
    public Optional<Session> getByUserId(UUID userId) {
        return db
                .selectFrom(SESSIONS)
                .where(SESSIONS.USER_ID.eq(userId))
                .fetchOptional(mapper);
    }

    @Override
    public PagedResult<Session> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(SESSIONS);
        criteria.apply(step);

        List<Session> list = step.fetch().map(record -> mapper.map((SessionsRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(SESSIONS);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID sessionId) {
        db.deleteFrom(SESSIONS)
                .where(SESSIONS.ID.eq(sessionId))
                .execute();
    }

    private void fillRecord(SessionsRecord record, Session session) {
        record.setId(session.getId());
        record.setUserId(session.getUserId());
        record.setDeviceId(session.getDeviceId());
        record.setToken(session.getToken());
        record.setLastActive(session.getLastActive());
    }
}
