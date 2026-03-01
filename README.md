# reservation-backend

SPRING_PROFILES_ACTIVE=dev

datasource:
url: ${DB_URL}
username: ${DB_USERNAME}
password: ${DB_PASSWORD}  

envoie email, prendre le current email admin et pas le admin set en dur au start de l'app