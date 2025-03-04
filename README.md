## Тестовое задание

### Описание
В качестве open source провайдера авторизации выбран Keycloak и OAuth2 Resource Owner Password Flow. 

`./bom` - gradle java-platform проект, для управления версиями зависимостей 

`./patient-service` - основное REST приложение с CRUD функциональностью по созданию
пациентов и OAuth2 аутентификацией и авторизацией через username and password grant.
Основные технологии: MapStruct, Spring Web MVC, Spring Boot, Spring Data Jpa,
Spring OAuth2 Client, Spring OAuth2 Resource Server, Swagger.
Написаны интеграционные тесты для API эндпоинта `POST /api/v1/patients`, используются
MockMvc и Testcontainers. Tomcat работает через виртуальные потоки.

`./console-app` - консольное приложение для генерации и создания 100 пациентов.
Используются виртуальные потоки.

`./agsr-patients-sakuuj.postman_collection.json` - файл с Postman коллекцией.

### Инструкции по запуску
```text
Credentials:
    Practitioner: 
        username: andrew_practitioner, password: 1
    Patient:
        username: john_patient, password: 1
```
        

Для запуска **patients-service** :
`docker compose -f ./docker/docker-compose.yml up`

Для запуска консольного приложения :
1. `{ ./gradlew | ./gradlew.bat } :console-app:jar`
2. `java -jar console-app/build/libs/console-app-1.0-SNAPSHOT.jar -u <username> -p <password> [-b <base-uri>]` 

`-b` - опциональный флаг

Пример запуска консольного приложения на Linux
```bash
/bin/bash -c "./gradlew :console-app:jar" && /bin/bash -c "java -jar console-app/build/libs/console-app-1.0-SNAPSHOT.jar -u andrew_practitioner -p 1"
```

### Postman
В постман запросах, требующих переменную id, необходимо её задать, подставив нужное значение в pre-request script. 

Access token выставляется автоматически после вызова /login эндпоинта.