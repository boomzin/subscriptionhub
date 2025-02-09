FROM zookeeper:3.4.13
LABEL authors="Ernest Aidinov"

COPY ./zoo.cfg /conf/
RUN mkdir -p /var/zookeeper
RUN chmod 777 /var/zookeeper