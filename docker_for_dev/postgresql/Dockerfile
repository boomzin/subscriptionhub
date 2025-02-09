FROM postgres:15.2
LABEL authors="Ernest Aidinov"

#USER root
RUN apt-get update -y && apt-get upgrade -y && apt-get autoremove -y --fix-missing
ENV LANG=en_US.UTF-8 \
    LANGUAGE=en_US:en \
    LC_ALL=en_US.UTF-8
RUN apt-get install -y locales && \
    sed -i '/en_US.UTF-8/s/^# //g' /etc/locale.gen && \
    locale-gen en_US.UTF-8 && \
    update-locale LANG=en_US.UTF-8
RUN apt-get install python3 python3-pip etcd -y
RUN pip3 install --upgrade setuptools
RUN pip3 install psycopg2-binary
RUN pip3 install patroni[etcd]
RUN pip3 install supervisor
RUN mkdir -p /etc/supervisor/conf.d
COPY ./supervisord.conf /etc/supervisor/conf.d
RUN chown -R postgres:postgres /etc/supervisor
RUN mkdir -p /var/log/supervisor
RUN chown -R postgres:postgres /var/log/supervisor

RUN mkdir /data/patroni -p
RUN chmod 700 /data/patroni
COPY ./post_init.sh /data/patroni
RUN chmod +x /data/patroni/post_init.sh
RUN chown -R postgres:postgres /data/patroni
COPY ./entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
RUN chown -R postgres:postgres /entrypoint.sh
RUN chmod 777 /root

EXPOSE 25432/tcp

ENTRYPOINT ["/entrypoint.sh"]
