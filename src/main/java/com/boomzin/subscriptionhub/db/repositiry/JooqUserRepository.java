package com.boomzin.subscriptionhub.db.repositiry;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.exception.ObjectNotFoundException;
import com.boomzin.subscriptionhub.common.search.SearchCriteria;
import com.boomzin.subscriptionhub.common.search.SearchCriteriaSettings;
import com.boomzin.subscriptionhub.db.generated.tables.records.UsersRecord;
import com.boomzin.subscriptionhub.domain.user.User;
import com.boomzin.subscriptionhub.domain.user.UserRepository;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.boomzin.subscriptionhub.common.search.JooqSearchUtils.UUID_EQ;
import static com.boomzin.subscriptionhub.db.generated.Tables.USERS;

@Repository
public class JooqUserRepository implements UserRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;

    private final RecordMapper<UsersRecord, User> mapper = r -> new User(
            r.getId(),
            r.getEmail(),
            r.getPasswordHash(),
            r.getCreatedAt(),
            r.getRoleId()
    );


    public JooqUserRepository(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()
                .filter("id", USERS.ID, UUID_EQ)
                .filter("roleId", USERS.ROLE_ID, UUID_EQ)

                .order("createdAt", USERS.CREATED_AT)

                .build();
    }


    @Override
    public void create(User user) {
        UsersRecord record = new UsersRecord();
        fillRecord(record, user);
        record.insert();
    }

    @Override
    public void update(User user) {
        UsersRecord record = db.fetchOptional(
                USERS,
                USERS.ID.eq(user.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(user.getId(), "User"));


        fillRecord(record, user);
        record.store();

    }

    @Override
    public User findById(UUID userUuid) {
        return db
                .selectFrom(USERS)
                .where(USERS.ID.eq(userUuid))
                .fetchOptional(mapper)
                .orElseThrow(() -> new ObjectNotFoundException(userUuid, "User"));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return db
                .selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOptional(mapper);
    }

    @Override
    public PagedResult<User> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(USERS);
        criteria.apply(step);

        List<User> list = step.fetch().map(record -> mapper.map((UsersRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(USERS);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID userUuid) {
        db.deleteFrom(USERS)
                .where(USERS.ID.eq(userUuid))
                .execute();
    }

    private void fillRecord(UsersRecord record, User user) {
        record.setId(user.getId());
        record.setEmail(user.getEmail());
        record.setPasswordHash(user.getPasswordHash());
        record.setCreatedAt(user.getCreatedAt());
        record.setRoleId(user.getRoleId());
    }
}
