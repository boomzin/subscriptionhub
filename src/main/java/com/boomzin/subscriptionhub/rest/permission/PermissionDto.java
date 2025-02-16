package com.boomzin.subscriptionhub.rest.permission;

import com.boomzin.subscriptionhub.domain.permissions.Permission;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto {
    private UUID id;

    @NotBlank
    private String name;

    private String description;

    public PermissionDto(Permission permission) {
        this(
                permission.getId(),
                permission.getName(),
                permission.getDescription());
    }

}
