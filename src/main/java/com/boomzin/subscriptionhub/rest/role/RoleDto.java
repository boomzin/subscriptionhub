package com.boomzin.subscriptionhub.rest.role;

import com.boomzin.subscriptionhub.domain.role.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private UUID id;
    @NotBlank
    private String name;

    public RoleDto(Role role) {
        this(
                role.getId(),
                role.getName()
        );
    }
}
