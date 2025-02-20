package com.boomzin.subscriptionhub.domain.subscriptiontype;

import com.boomzin.subscriptionhub.common.UuidEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jooq.JSONB;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionType extends UuidEntity {
    private UUID id;
    private String name;
    private Integer durationDays;
    private BigDecimal price;
    private JSONB feature;
}
