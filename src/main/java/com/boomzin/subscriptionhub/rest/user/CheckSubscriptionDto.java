package com.boomzin.subscriptionhub.rest.user;

import com.boomzin.subscriptionhub.domain.subscription.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckSubscriptionDto {
    private List<Subscription> activeSubscriptions;
    private String signature;
}
