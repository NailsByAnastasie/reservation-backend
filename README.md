# reservation-backend

SPRING_PROFILES_ACTIVE=dev

datasource:
url: ${DB_URL}
username: ${DB_USERNAME}
password: ${DB_PASSWORD}  

envoie email, prendre le current email admin et pas le admin set en dur au start de l'app  
nouvelle route validate, update le status du meet et send email  
cancel meet, pour securiser la route, demande à l'utilisateur de rentré son email avant de pouvoir annuler  
connect au vrai serveur smtp ovh  