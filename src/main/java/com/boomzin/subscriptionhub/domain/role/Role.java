package com.boomzin.subscriptionhub.domain.role;

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
public class Role extends UuidEntity {
    private String name;

    public Role(UUID id, String name) {
        super(id);
        this.name = name;
    }
}
