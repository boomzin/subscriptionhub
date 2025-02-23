## RTK IN

### Development Environment

1. JDK 21
2. PostgreSql
3. Docker

### Tips

#### Build:
```
./gradlew build
```
or
```
make build
```



#### Local run:
1. Install PostgreSql, create user 'boomzin' with password 'boomzin'
2. Create and migrate DB schemas (see above)
3. Run:
```
./gradlew bootRun
```


## DEV окружение
### Все БД PostgreSQL доступны со следующими параметрами:
* login: boomzin
* password: boomzin
* read\write port: 5432


### Для того что бы звпустить контейнеры с базами данных необходимо:
* Создать каталоги:
  ```
  mkdir -pv ./docker_for_dev/data/etcd
  mkdir -pv ./docker_for_dev/data/{postgres1,postgres2}/{etcd,pgsql}
  ```
* Выполнить команду:
  ```
  docker compose -f dev_env.yaml up -d --build --remove-orphans
  ```

### Для того что бы остановить контейнеры необходимо:
* Выполнить команду:
  ```
  docker compose -f dev_env.yaml down -v --rmi local
  ```

### Если необходимо сбросить базы данных до дефолтного состояния:
* Остановить контейнеры:
  ```
  docker compose -f dev_env.yaml down -v --rmi local
  ```
* Удалить данные сохраненые на локальной машине:
  ```
  sudo rm -rf ./docker_for_dev/data/postgres2/etcd/* && \
  sudo rm -rf ./docker_for_dev/data/postgres1/etcd/* && \
  sudo rm -rf ./docker_for_dev/data/postgres2/pgsql && \
  sudo rm -rf ./docker_for_dev/data/postgres1/pgsql && \
  sudo rm -rf ./docker_for_dev/data/etcd/*; \
  mkdir docker_for_dev/data/postgres1/pgsql && \
  mkdir docker_for_dev/data/postgres2/pgsql
  ```
  или
````
  sudo rm -rf ./docker/pgdata && \
  mkdir -pv ./docker/pgdata
````

sudo rm -rf ./docker/pgdata && \
mkdir -pv ./docker/pgdata




  
* Запустить котейнеры:
  ```
  docker compose -f dev_env.yaml up -d --build --remove-orphans
  ```

### Если `read only` базы не доступны дольше 10 минут необходимо:
* Зайти в один из контейнеров postgres:
  ```
  docker exec -it postgres1 bash
  ```
* Посмотреть состояние кластера patroni:
  ```
  patronictl -c /etc/patroni.yml list
  ```
  Пример результата выполнения команды:
  ```
  + Cluster: db-dev ------------+---------+------------------+----+-----------+
  | Member    | Host            | Role    | State            | TL | Lag in MB |
  +-----------+-----------------+---------+------------------+----+-----------+
  | postgres1 | postgres1:25432 | Replica | creating replica |    |   unknown |
  | postgres2 | postgres2:25432 | Leader  | running          |  1 |           |
  +-----------+-----------------+---------+------------------+----+-----------+
  ```
* Выполнить команду для реинициализации узла в состоянии `creating replica`:
  ```
  patronictl -c /etc/patroni.yml reinit db-dev postgres1
  ```
  Пример корректного состояния кластера:
  ```
  + Cluster: db-dev ------------+---------+---------+----+-----------+
  | Member    | Host            | Role    | State   | TL | Lag in MB |
  +-----------+-----------------+---------+---------+----+-----------+
  | postgres1 | postgres1:25432 | Leader  | running |  2 |           |
  | postgres2 | postgres2:25432 | Replica | running |  2 |         0 |
  +-----------+-----------------+---------+---------+----+-----------+
  ```
  