package com.boomzin.subscriptionhub.domain.role;

import com.boomzin.subscriptionhub.common.UuidEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends UuidEntity {
    private UUID uuid;
    private String name;
}
