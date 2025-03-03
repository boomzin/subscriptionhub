package com.boomzin.subscriptionhub.rest.rolepermission;

import com.boomzin.subscriptionhub.domain.rolepermission.RolePermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionDto {
    private UUID roleId;
    private UUID permissionId;

    public RolePermissionDto(RolePermission rolePermission) {
        this(rolePermission.getRoleId(), rolePermission.getPermissionId());
    }
}
