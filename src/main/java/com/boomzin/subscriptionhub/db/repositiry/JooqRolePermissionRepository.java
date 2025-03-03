package com.boomzin.subscriptionhub.db.repositiry;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.search.SearchCriteria;
import com.boomzin.subscriptionhub.common.search.SearchCriteriaSettings;
import com.boomzin.subscriptionhub.db.generated.tables.records.RolePermissionsRecord;
import com.boomzin.subscriptionhub.domain.rolepermission.RolePermission;
import com.boomzin.subscriptionhub.domain.rolepermission.RolePermissionRepository;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.boomzin.subscriptionhub.common.search.JooqSearchUtils.UUID_EQ;
import static com.boomzin.subscriptionhub.db.generated.Tables.ROLE_PERMISSIONS;

@Repository
public class JooqRolePermissionRepository implements RolePermissionRepository {
    private final DSLContext db;
    private final SearchCriteriaSettings criteriaSettings;

    private final RecordMapper<RolePermissionsRecord, RolePermission> mapper = r -> new RolePermission(
            r.getRoleId(),
            r.getPermissionId()
    );


    public JooqRolePermissionRepository(DSLContext db) {
        this.db = db;
        this.criteriaSettings = SearchCriteriaSettings.builder()
                .filter("roleId", ROLE_PERMISSIONS.ROLE_ID, UUID_EQ)
                .filter("permissionId", ROLE_PERMISSIONS.PERMISSION_ID, UUID_EQ)

                .build();
    }


    @Override
    public void create(RolePermission rolePermission) {
        RolePermissionsRecord record = db.newRecord(ROLE_PERMISSIONS);
        record.setRoleId(rolePermission.getRoleId());
        record.setPermissionId(rolePermission.getPermissionId());
        record.insert();
    }



    @Override
    public PagedResult<RolePermission> search(Map<String, String> apiParams) {
        SearchCriteria criteria = SearchCriteria.getInstance(criteriaSettings, apiParams);
        SelectWhereStep<?> step = db.selectFrom(ROLE_PERMISSIONS);
        criteria.apply(step);

        List<RolePermission> list = step.fetch().map(record -> mapper.map((RolePermissionsRecord) record));

        SelectWhereStep<?> countStep = db.selectCount().from(ROLE_PERMISSIONS);

        criteria.applyForCount(countStep);
        int itemsCount = countStep.fetchOptionalInto(Integer.class).orElse(0);

        return new PagedResult<>(list, itemsCount, criteria.getOffset(), criteria.getLimit());
    }

    @Override
    public void delete(UUID roleId, UUID permissionId) {
        db.deleteFrom(ROLE_PERMISSIONS)
                .where(ROLE_PERMISSIONS.PERMISSION_ID.eq(permissionId)
                        .and(ROLE_PERMISSIONS.ROLE_ID.eq(roleId)))
                .execute();
    }
}
