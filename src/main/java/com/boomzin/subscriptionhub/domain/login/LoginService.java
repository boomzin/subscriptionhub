package com.boomzin.subscriptionhub.domain.login;

import com.boomzin.subscriptionhub.common.exception.DomainException;
import com.boomzin.subscriptionhub.common.exception.PlainException;
import com.boomzin.subscriptionhub.config.security.Sha256PasswordEncoder;
import com.boomzin.subscriptionhub.db.generated.enums.SubscriptionStatus;
import com.boomzin.subscriptionhub.domain.session.Session;
import com.boomzin.subscriptionhub.domain.session.SessionRepository;
import com.boomzin.subscriptionhub.domain.subscription.Subscription;
import com.boomzin.subscriptionhub.domain.subscription.SubscriptionRepository;
import com.boomzin.subscriptionhub.domain.user.User;
import com.boomzin.subscriptionhub.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
public class LoginService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final Sha256PasswordEncoder encoder;

    public LoginService(UserRepository userRepository, SessionRepository sessionRepository, SubscriptionRepository subscriptionRepository, Sha256PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.encoder = encoder;
    }

    public String login(String login, String password, String deviceId) {
        if(deviceId == null) {
            throw new DomainException(400, "empty device id");
        }
        Optional<User> user = userRepository.getByEmail(login);
        if (user.isEmpty()) {
            throw new DomainException(401, "invalid login or password");
        }
        User userEntity = user.get();
        if (userEntity.getPasswordHash() == null) {
            userEntity.setPasswordHash(encoder.encodePassword(password, userEntity.getId()));
            userRepository.update(userEntity);
        } else if (!matchPassword(userEntity, password)) {
            throw new DomainException(401, "invalid login or password");
        }

        if(userEntity.getRoleId().equals(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                || userEntity.getRoleId().equals(UUID.fromString("00000000-0000-0000-0000-000000000002"))) {
            return getToken(userEntity, deviceId);
        }

        List<Subscription> subscriptions = subscriptionRepository.getByUserId(userEntity.getId());
        AtomicBoolean hasActiveSubscription = new AtomicBoolean(false);
        subscriptions.forEach(subscription -> {
           if (subscription.getStatus() == SubscriptionStatus.ACTIVE
                   && (subscription.getStartDate().isBefore(LocalDate.now(ZoneOffset.UTC)) || subscription.getStartDate().isEqual(LocalDate.now(ZoneOffset.UTC)))
                   && (subscription.getEndDate().isAfter(LocalDate.now(ZoneOffset.UTC))) || subscription.getEndDate().isEqual(LocalDate.now(ZoneOffset.UTC))) {
               hasActiveSubscription.set(true);
           }
        });

        if (!hasActiveSubscription.get()) {
            throw new PlainException("There is no active or unexpired subscription");
        }
        return getToken(userEntity, deviceId);
    }


    private String getToken(User user, String deviceId) {
        Optional<Session> session = sessionRepository.getByUserId(user.getId());
        String token = UUID.randomUUID().toString();
        if (session.isEmpty()) {
            sessionRepository.create(new Session(UUID.randomUUID(), user.getId(), deviceId, token, LocalDateTime.now(ZoneOffset.UTC)));
            return token;
        }

        Session sessionEntity = session.get();
        if (sessionEntity.getDeviceId().equals(deviceId)) {
            return sessionEntity.getToken();
        }
        sessionRepository.delete(sessionEntity.getId());
        sessionRepository.create(new Session(UUID.randomUUID(), user.getId(), deviceId, token, LocalDateTime.now(ZoneOffset.UTC)));
        return token;
    }

    private boolean matchPassword(User user, String rawPassword) {
        return user.getPasswordHash().equals(encoder.encodePassword(rawPassword, user.getId()));
    }
}
