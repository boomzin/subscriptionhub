package com.boomzin.subscriptionhub.rest.subscription;

import com.boomzin.subscriptionhub.db.generated.enums.SubscriptionStatus;
import com.boomzin.subscriptionhub.domain.subscription.Subscription;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private SubscriptionStatus status;

    public SubscriptionDto(Subscription subscription) {
        this(
            subscription.getId(),
            subscription.getUserId(),
            subscription.getTypeId(),
            subscription.getStartDate(),
            subscription.getCreatedAt(),
            subscription.getEndDate(),
            subscription.getStatus()
        );
    }
}

