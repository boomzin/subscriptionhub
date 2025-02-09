FROM flyway/flyway:latest-alpine
LABEL authors="Ernest Aidinov"

CMD ["/bin/sh"]

COPY ./docker_for_dev/flyway/entrypoint.sh /
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
