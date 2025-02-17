package com.boomzin.subscriptionhub.domain.permission;

import com.boomzin.subscriptionhub.common.UuidEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends UuidEntity {
    private UUID uuid;
    private String name;
    private String description;
}
