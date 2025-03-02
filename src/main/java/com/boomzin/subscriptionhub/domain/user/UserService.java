package com.boomzin.subscriptionhub.domain.user;

import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.exception.DomainException;
import com.boomzin.subscriptionhub.common.exception.PlainException;
import com.boomzin.subscriptionhub.config.security.RSASignature;
import com.boomzin.subscriptionhub.config.security.Sha256PasswordEncoder;
import com.boomzin.subscriptionhub.config.security.TokenInfo;
import com.boomzin.subscriptionhub.db.generated.enums.SubscriptionStatus;
import com.boomzin.subscriptionhub.domain.permission.Permission;
import com.boomzin.subscriptionhub.domain.permission.PermissionRepository;
import com.boomzin.subscriptionhub.domain.session.Session;
import com.boomzin.subscriptionhub.domain.session.SessionRepository;
import com.boomzin.subscriptionhub.domain.subscription.Subscription;
import com.boomzin.subscriptionhub.domain.subscription.SubscriptionRepository;
import com.boomzin.subscriptionhub.rest.user.CheckSubscriptionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PermissionRepository permissionRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final Sha256PasswordEncoder encoder;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RSASignature rsaSignature;


    public UserService(UserRepository userRepository,
                       SessionRepository sessionRepository,
                       PermissionRepository permissionRepository,
                       SubscriptionRepository subscriptionRepository,
                       Sha256PasswordEncoder encoder,
                       RSASignature rsaSignature) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.permissionRepository = permissionRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.encoder = encoder;
        this.rsaSignature = rsaSignature;
        objectMapper.registerModule(new JavaTimeModule());
    }

    public User findById(UUID userUuid) {
        return userRepository.findById(userUuid);
    }

    public PagedResult<User> search(Map<String, String> apiParams) {
        return userRepository.search(apiParams);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void create(User user) {
        userRepository.create(user);
    }

    public void delete(UUID userUuid) {
        userRepository.delete(userUuid);
    }

    public Optional<TokenInfo> getTokenInfo(String token) {
        Optional<TokenInfo> tokenInfo = Optional.empty();

        if (token == null || token.isEmpty()) {
            return tokenInfo;
        }

        Optional<Session> session = sessionRepository.findByToken(token);
        if (session.isEmpty()) {
            return tokenInfo;
        }

        Session currentSession = session.get();

        if(currentSession.getUserId() == null) {
            return tokenInfo;
        }
        currentSession.setLastActive(LocalDateTime.now(ZoneOffset.UTC));
        sessionRepository.update(currentSession);

        User user = userRepository.findById(currentSession.getUserId());
        List<Permission> permissions = permissionRepository.findByRoleId(user.getRoleId());
        return Optional.of(new TokenInfo(user.getEmail(), permissions.stream().map(Permission::getName).toList()));
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public CheckSubscriptionDto checkSubscription(UUID userId) {
        List<Subscription> subscriptions = subscriptionRepository.getByUserId(userId);
        List<Subscription> activeSubscriptions = getActiveSubscriptions(subscriptions);
        if (activeSubscriptions.isEmpty()) {
            throw new DomainException(402, "Active subscription not found");
        }
        String signature;
        try {
            signature = rsaSignature.signData(objectMapper.writeValueAsString(activeSubscriptions));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new CheckSubscriptionDto(activeSubscriptions, signature);
    }


    private List<Subscription> getActiveSubscriptions(List<Subscription> subscriptions) {
        List<Subscription> activeSubscriptions = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            if (subscription.getStatus() == SubscriptionStatus.ACTIVE
                    && (subscription.getStartDate().isBefore(LocalDate.now(ZoneOffset.UTC)) || subscription.getStartDate().isEqual(LocalDate.now(ZoneOffset.UTC)))
                    && (subscription.getEndDate().isAfter(LocalDate.now(ZoneOffset.UTC))) || subscription.getEndDate().isEqual(LocalDate.now(ZoneOffset.UTC))) {
                activeSubscriptions.add(subscription);
            }
        }
        return activeSubscriptions;
    }

    public String login(String login, String password, String deviceId) {
        if(!StringUtils.hasText(deviceId)) {
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
        List<Subscription> activeSubscriptions = getActiveSubscriptions(subscriptions);
        if (activeSubscriptions.isEmpty()) {
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
