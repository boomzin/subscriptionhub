package com.boomzin.subscriptionhub.domain.permission;

import com.boomzin.subscriptionhub.common.UuidEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends UuidEntity {
    private String name;
    private String description;

    public Permission(UUID id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }
}
