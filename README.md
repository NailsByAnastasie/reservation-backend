# reservation-backend

SPRING_PROFILES_ACTIVE=dev

datasource:
url: ${DB_URL}
username: ${DB_USERNAME}
password: ${DB_PASSWORD}


```bash
mvn clean compile liquibase:diff
```