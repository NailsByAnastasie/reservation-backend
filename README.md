# reservation-backend

DB_PASSWORD=MOTDEPASSE;  
DB_URL=jdbc:postgresql://localhost:5432/nails;  
DB_USERNAME=postgres_user_dev;  
DEFAULT_EMAIL=email@gmail.com;  
DEFAULT_PASSWORD=password;  
EMAIL_HOST=sandbox.smtp.mailtrap.io;  
EMAIL_LOGIN=login;  
EMAIL_PASSWORD=pasword;  
EMAIL_PORT=2525;  
FRONTEND_URL=http://localhost:4200;  
JWT_EXPIRATION=259200000;  
JWT_SECRET=niceToken

envoie email, prendre le current email admin et pas le admin set en dur au start de l'app  

nouvelle route validate, update le status du meet et send email

connect au vrai serveur smtp ovh  

mettre en place de la migration de database (liquidbase)  
