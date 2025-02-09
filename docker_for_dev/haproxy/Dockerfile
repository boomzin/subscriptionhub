FROM haproxy:2.8.0
LABEL authors="Ernest Aidinov"

USER root
RUN apt-get update -y && apt-get upgrade -y && apt-get autoremove -y
RUN apt-get install python3 python3-pip etcd -y
RUN pip3 install --upgrade setuptools
RUN pip3 install supervisor
RUN mkdir -p /etc/supervisor/conf.d
COPY ./supervisord.conf /etc/supervisor/conf.d
RUN mkdir -p /var/log/supervisor
RUN chmod a+w /var/log/supervisor
COPY ./entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
