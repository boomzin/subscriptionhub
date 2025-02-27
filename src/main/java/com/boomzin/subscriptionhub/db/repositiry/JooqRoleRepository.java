package com.boomzin.subscriptionhub.db.repositiry;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.exception.ObjectNotFoundException;
import com.boomzin.subscriptionhub.common.search.SearchCriteria;
import com.boomzin.subscriptionhub.common.search.SearchCriteriaSettings;
import com.boomzin.subscriptionhub.db.generated.tables.records.RolesRecord;
import com.boomzin.subscriptionhub.domain.role.Role;
import com.boomzin.subscriptionhub.domain.role.RoleRepository;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.boomzin.subscriptionhub.common.search.JooqSearchUtils.STR_LIKE_IC;
import static com.boomzin.subscriptionhub.common.search.JooqSearchUtils.UUID_EQ;
import static com.boomzin.subscriptionhub.db.generated.Tables.ROLES;

@Repository
public class JooqRoleRepository implements RoleRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;

    private final RecordMapper<RolesRecord, Role> mapper = r -> new Role(
            r.getId(),
            r.getName()
    );


    public JooqRoleRepository(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()
                .filter("id", ROLES.ID, UUID_EQ)
                .filter("name", ROLES.NAME, STR_LIKE_IC)

                .order("name", ROLES.NAME)

                .build();
    }


    @Override
    public void create(Role permission) {
        RolesRecord record = db.newRecord(ROLES);
        fillRecord(record, permission);
        record.insert();
    }

    @Override
    public void update(Role permission) {
        RolesRecord record = db.fetchOptional(
                ROLES,
                ROLES.ID.eq(permission.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(permission.getId(), "Role"));


        fillRecord(record, permission);
        record.store();

    }

    @Override
    public Role findById(UUID permissionId) {
        return db
                .selectFrom(ROLES)
                .where(ROLES.ID.eq(permissionId))
                .fetchOptional(mapper)
                .orElseThrow(() -> new ObjectNotFoundException(permissionId, "Role"));
    }

    @Override
    public PagedResult<Role> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(ROLES);
        criteria.apply(step);

        List<Role> list = step.fetch().map(record -> mapper.map((RolesRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(ROLES);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID permissionId) {
        db.deleteFrom(ROLES)
                .where(ROLES.ID.eq(permissionId))
                .execute();
    }

    private void fillRecord(RolesRecord record, Role permission) {
        record.setId(permission.getId());
        record.setName(permission.getName());
    }
}
