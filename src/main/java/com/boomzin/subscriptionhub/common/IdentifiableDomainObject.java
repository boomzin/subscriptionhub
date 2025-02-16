package com.boomzin.subscriptionhub.common;


import com.boomzin.subscriptionhub.common.data.AssertionConcern;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class IdentifiableDomainObject<T extends Serializable> extends AssertionConcern implements Identifiable<T> {
    private T id;

    public IdentifiableDomainObject(T id) {
        setId(id);
    }

    private void setId(T id) {
        assertNotNull(id, "Id may mot be null");
        this.id = id;
    }

    @Override
    public T getId() {
        return id;
    }
}
