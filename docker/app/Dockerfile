# Stage 1: Сборка приложения с Gradle и JDK 17
FROM gradle:8-jdk17 AS builder

WORKDIR /app
COPY --chown=gradle:gradle . .

# Запускаем сборку Spring Boot JAR
RUN gradle clean bootJar --no-daemon --stacktrace --info

# Stage 2: Запуск приложения
FROM openjdk:17-jdk-slim

WORKDIR /app
# Копируем собранный JAR-файл
COPY --from=builder /app/build/libs/subscriptionhub.jar subscriptionhub.jar

## Копируем start.sh и даём права на выполнение
#COPY docker/app/start.sh /app/start.sh
RUN #chmod +x /app/start.sh

# Опционально: задаём профиль Spring Boot
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["/app/start.sh"]