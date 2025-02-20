package com.boomzin.subscriptionhub.rest.subscriptiontype;

import com.boomzin.subscriptionhub.common.UuidEntity;
import com.boomzin.subscriptionhub.domain.subscriptiontype.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jooq.JSONB;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionTypeDto {
    private UUID id;
    private String name;
    private Integer durationDays;
    private BigDecimal price;
    private JSONB feature;

    public SubscriptionTypeDto(SubscriptionType subscriptionType) {
        this(
             subscriptionType.getId(),
             subscriptionType.getName(),
             subscriptionType.getDurationDays(),
             subscriptionType.getPrice(),
             subscriptionType.getFeature()
        );
    }
}
