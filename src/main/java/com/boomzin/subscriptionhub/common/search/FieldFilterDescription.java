package com.boomzin.subscriptionhub.common.search;

import org.jooq.Condition;

public interface FieldFilterDescription {
    Condition getCondition(String value);
}
