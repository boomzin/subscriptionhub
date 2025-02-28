package com.boomzin.subscriptionhub.db.repositiry;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.exception.ObjectNotFoundException;
import com.boomzin.subscriptionhub.common.search.SearchCriteria;
import com.boomzin.subscriptionhub.common.search.SearchCriteriaSettings;
import com.boomzin.subscriptionhub.db.generated.tables.records.PermissionsRecord;
import com.boomzin.subscriptionhub.domain.permission.Permission;
import com.boomzin.subscriptionhub.domain.permission.PermissionRepository;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.boomzin.subscriptionhub.common.search.JooqSearchUtils.STR_LIKE_IC;
import static com.boomzin.subscriptionhub.common.search.JooqSearchUtils.UUID_EQ;
import static com.boomzin.subscriptionhub.db.generated.Tables.PERMISSIONS;

@Repository
public class JooqPermissionRepository implements PermissionRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;

    private final RecordMapper<PermissionsRecord, Permission> mapper = r -> new Permission(
            r.getId(),
            r.getName(),
            r.getDescription()
    );


    public JooqPermissionRepository(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()
                .filter("id", PERMISSIONS.ID, UUID_EQ)
                .filter("name", PERMISSIONS.NAME, STR_LIKE_IC)

                .order("name", PERMISSIONS.NAME)

                .build();
    }


    @Override
    public void create(Permission permission) {
        PermissionsRecord record =db.newRecord(PERMISSIONS);
        fillRecord(record, permission);
        record.insert();
    }

    @Override
    public void update(Permission permission) {
        PermissionsRecord record = db.fetchOptional(
                PERMISSIONS,
                PERMISSIONS.ID.eq(permission.getId()))
                .orElseThrow(() -> new ObjectNotFoundException(permission.getId(), "Permission"));


        fillRecord(record, permission);
        record.store();

    }

    @Override
    public Permission findById(UUID permissionId) {
        return db
                .selectFrom(PERMISSIONS)
                .where(PERMISSIONS.ID.eq(permissionId))
                .fetchOptional(mapper)
                .orElseThrow(() -> new ObjectNotFoundException(permissionId, "Permission"));
    }

    @Override
    public List<Permission> findByRoleId(UUID roleId) {
        return db
                .selectFrom(PERMISSIONS)
                .where(PERMISSIONS.ID.eq(roleId))
                .fetch()
                .map(mapper);
    }

    @Override
    public PagedResult<Permission> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(PERMISSIONS);
        criteria.apply(step);

        List<Permission> list = step.fetch().map(record -> mapper.map((PermissionsRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(PERMISSIONS);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID permissionId) {
        db.deleteFrom(PERMISSIONS)
                .where(PERMISSIONS.ID.eq(permissionId))
                .execute();
    }

    private void fillRecord(PermissionsRecord record, Permission permission) {
        record.setId(permission.getId());
        record.setName(permission.getName());
        record.setDescription(permission.getDescription());
    }
}
