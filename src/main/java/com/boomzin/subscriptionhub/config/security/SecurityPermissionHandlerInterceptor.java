package com.boomzin.subscriptionhub.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.boomzin.subscriptionhub.common.Constants.BASIC_PATH_V1;

public class SecurityPermissionHandlerInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(SecurityPermissionHandlerInterceptor.class);
    private static final List<String> PUBLIC_PATHS = List.of(BASIC_PATH_V1 + "/login");

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        if (handler instanceof HandlerMethod) {
            log.debug("Start checking permissions for method {}", request.getRequestURI());
            return checkPermissions(request, response, (HandlerMethod) handler);
        }
        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                           @NotNull Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                @NotNull Object handler, Exception ex) {

    }

    private boolean checkPermissions(HttpServletRequest request, HttpServletResponse response,
                                     HandlerMethod handler) {

        String requestPath = request.getRequestURI();

        if (PUBLIC_PATHS.contains(requestPath)) {
            return true;
        }
        return getAnnotation(handler)
                .map(annotation -> processAnnotation(annotation, request, response))
                .orElseGet(() -> {
                    log.debug("No @SecurityPermission annotation found, access denied");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    return false;
                });
    }

    private Optional<SecurityPermission> getAnnotation(HandlerMethod handler) {
        SecurityPermission securityPermission = AnnotationUtils.findAnnotation(handler.getMethod(), SecurityPermission.class);
        if (securityPermission == null) {
            securityPermission = AnnotationUtils.findAnnotation(handler.getBeanType(), SecurityPermission.class);
        }
        return Optional.ofNullable(securityPermission);
    }

    private boolean processAnnotation(SecurityPermission annotation, HttpServletRequest request,
                                      HttpServletResponse response) {
        log.debug("Annotation {} found, start checking user permission", String.join(",", Arrays.toString(annotation.value())));
        Authentication auth = (Authentication) request.getUserPrincipal();
        if (auth == null || !auth.isAuthenticated()) {
            log.debug("User is not authorized, return 401 response");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        log.debug("User authorized. Check {} in user authorities list {}", String.join(",", Arrays.toString(annotation.value())), auth.getAuthorities());
        if (isPermissionGranted(annotation.value(), auth)) {
            log.debug("User has permission. Request allowed");
            return true;
        } else {
            log.debug("User has not permission. Return 403 response");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
    }

    private boolean isPermissionGranted(String[] permission, Authentication auth) {
        Set<String> permissionSet = Arrays.stream(permission).map(String::toLowerCase).collect(Collectors.toSet());
        return auth.getAuthorities()
                .stream()
                .map(e -> e.toString().toLowerCase())
                .anyMatch(permissionSet::contains);
    }
}
