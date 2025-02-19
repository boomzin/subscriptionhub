package com.boomzin.subscriptionhub.rest.subscription;

import com.boomzin.subscriptionhub.db.generated.enums.SubscriptionStatus;
import com.boomzin.subscriptionhub.domain.subscription.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private UUID id;
    private UUID userId;
    private UUID typeId;
    private LocalDate startDateTime;
    private LocalDateTime createdAt;
    private LocalDate endDate;
    private SubscriptionStatus status;

    public SubscriptionDto(Subscription subscription) {
        this(
            subscription.getId(),
            subscription.getUserId(),
            subscription.getTypeId(),
            subscription.getStartDateTime(),
            subscription.getCreatedAt(),
            subscription.getEndDate(),
            subscription.getStatus()
        );
    }
}

