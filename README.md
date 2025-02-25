### Development Environment

1. JDK 17
2. PostgreSql
3. Docker

* Склонируйте проект. 
* Перед первым запуском создайте папку для файлов бд командой:
````
  mkdir -pv ./docker/pgdata
````
* Запустите контенеры:
````
docker compose up -d --force-recreate
````
После завершения запуска у вас останется три контейнера: контейнер с nginx, контейнер с postgresql и контейнер с приложением.
Бд будет доступна на стандартном порту:
````
jdbc:postgresql://localhost:5432/subscription_system
````
Учетная запись для подключения:
login:    boomzin
password: boomzin

Приложение будет отвечать на запросы напрямую на порту 8090:
````
curl --location '127.0.0.1:8090/api/rest/subscriptions/v1/permissions'
````
а также через контейнер nginx:
````
curl --location 'http://localhost/subscriptions/permissions'
````
Также можно запустить параллельно еще один экземпляр приложения в IDE, оно подключиться к бд в контейнере и будет отвечать на запросы на порту 8080:
````
curl --location '127.0.0.1:8080/api/rest/subscriptions/v1/permissions'
````

Остановить контейнеры:
````
docker compose down -v
````
Если нужно обнулить значения в бд нужно остановить контейнеры и пересоздать каталог с файлами бд:
````
  sudo rm -rf ./docker/pgdata && \
  mkdir -pv ./docker/pgdata
````
