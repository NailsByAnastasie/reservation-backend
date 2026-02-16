package nails.yona.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import nails.yona.model.Meet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${app.admin.default.email}")
    private String adminEmail;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Async
    public void sendMeetConfirmation(Meet meet) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("contact@yona-nails.com");
            helper.setTo(meet.getClient().getEmail());
            helper.setSubject("💅 Confirmation de votre rendez-vous - Yona Nails");

            String date = meet.getDateStart().format(dateFormatter);
            String time = meet.getDateStart().format(timeFormatter);

            String cancelLink = frontendUrl + "/cancel-meet?id=" + meet.getId();

            String htmlContent = "<h3>Bonjour " + meet.getClient().getFullName() + ",</h3>"
                    + "<p>Votre réservation a bien été confirmée ! 🎉</p>"
                    + "<p><b>Prestation :</b> " + meet.getPrestation().getName()+ "<br>"
                    + "<b>Date :</b> Le " + date + " à " + time + "</p>"
                    + "<hr>"
                    + "<p>En cas d'imprévu, vous pouvez annuler votre rendez-vous en cliquant sur ce lien :</p>"
                    + "<a href='" + cancelLink + "' style='padding: 10px 15px; background-color: #ff4757; color: white; text-decoration: none; border-radius: 5px;'>Annuler mon rendez-vous</a>"
                    + "<br><br><p>À très vite !<br><b>L'équipe Yona Nails</b></p>";

            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            System.err.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }

    @Async
    public void sendMeetCancellationToClient(Meet meet) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("contact@yona-nails.com");
            helper.setTo(meet.getClient().getEmail());
            helper.setSubject("❌ Annulation de votre rendez-vous - Yona Nails");

            String date = meet.getDateStart().format(dateFormatter);
            String time = meet.getDateStart().format(timeFormatter);

            String htmlContent = "<h3>Bonjour " + meet.getClient().getFullName()+ ",</h3>"
                    + "<p>Nous vous confirmons l'annulation de votre rendez-vous du <b>" + date + " à " + time + "</b> "
                    + "pour la prestation : <i>" + meet.getPrestation().getName()+ "</i>.</p>"
                    + "<p>Nous espérons vous revoir très bientôt !</p>"
                    + "<br><p><b>L'équipe Yona Nails</b></p>";

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Erreur e-mail cliente (annulation) : " + e.getMessage());
        }
    }

    @Async
    public void sendMeetCancellationToAdmin(Meet meet) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("contact@yona-nails.com");
            helper.setTo(adminEmail); // Envoi à TON adresse !
            helper.setSubject("⚠️ Un rendez-vous a été annulé (" + meet.getClient().getFullName()+ ")");

            String date = meet.getDateStart().format(dateFormatter);
            String time = meet.getDateStart().format(timeFormatter);

            String htmlContent = "<h3>Hello Yona,</h3>"
                    + "<p>La cliente <b>" + meet.getClient().getFullName() + "</b> "
                    + "vient d'annuler son rendez-vous.</p>"
                    + "<ul>"
                    + "<li><b>Date :</b> " + date + " à " + time + "</li>"
                    + "<li><b>Prestation :</b> " + meet.getPrestation().getName() + "</li>"
                    + "<li><b>Contact :</b> " + meet.getClient().getContactType() + " " + meet.getClient().getContactValue() + "</li>"
                    + "</ul>"
                    + "<p><i>Ce créneau est de nouveau libre dans ton calendrier !</i></p>";

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Erreur e-mail admin (annulation) : " + e.getMessage());
        }
    }
}