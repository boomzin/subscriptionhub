package com.boomzin.subscriptionhub.common;


import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public abstract class UuidEntity extends IdentifiableDomainObject<UUID> implements Entity<UUID> {
    public UuidEntity(UUID id) {
        super(id);
    }
}

