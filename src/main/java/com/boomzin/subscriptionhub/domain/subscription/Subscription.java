package com.boomzin.subscriptionhub.domain.subscription;

import com.boomzin.subscriptionhub.common.UuidEntity;
import com.boomzin.subscriptionhub.db.generated.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription extends UuidEntity {
    private UUID id;
    private UUID userId;
    private UUID typeId;
    private LocalDate startDateTime;
    private LocalDateTime createdAt;
    private LocalDate endDate;
    private SubscriptionStatus status;
}
