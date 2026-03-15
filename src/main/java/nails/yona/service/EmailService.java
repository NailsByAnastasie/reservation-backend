package nails.yona.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import nails.yona.model.AdminUser;
import nails.yona.model.Meet;
import nails.yona.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final AdminUserRepository adminUserRepository;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Async
    public void sendMeetConfirmation(Meet meet) {
        try {
            Context context = new Context();
            context.setVariable("clientName", meet.getClient().getFullName());
            context.setVariable("prestationTitle", meet.getPrestation().getName());
            context.setVariable("meetDate", meet.getDateStart().format(dateFormatter));
            context.setVariable("meetTime", meet.getDateStart().format(timeFormatter));
            context.setVariable("cancelLink", frontendUrl + "/cancel-meet?id=" + meet.getId());

            String htmlContent = templateEngine.process("emails/meet-confirmation", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("contact@yona-nails.com");
            helper.setTo(meet.getClient().getEmail());
            helper.setSubject("💅 Confirmation de votre rendez-vous - Yona Nails");
            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            System.err.println("Erreur e-mail confirmation : " + e.getMessage());
        }
    }

    @Async
    public void sendMeetCancellationToClient(Meet meet) {
        try {
            Context context = new Context();
            context.setVariable("clientName", meet.getClient().getFullName());
            context.setVariable("prestationName", meet.getPrestation().getName());
            context.setVariable("meetDate", meet.getDateStart().format(dateFormatter));
            context.setVariable("meetTime", meet.getDateStart().format(timeFormatter));

            String htmlContent = templateEngine.process("emails/meet-cancellation-client", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("contact@yona-nails.com");
            helper.setTo(meet.getClient().getEmail());
            helper.setSubject("❌ Annulation de votre rendez-vous - Yona Nails");
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Erreur e-mail cliente (annulation) : " + e.getMessage());
        }
    }

    @Async
    public void sendMeetCancellationToAdmin(Meet meet) {
        try {
            String adminEmail = adminUserRepository.findAll().stream()
                    .findFirst()
                    .map(AdminUser::getEmail)
                    .orElse("no-reply@yona-nails.com");

            Context context = new Context();
            context.setVariable("clientName", meet.getClient().getFullName());
            context.setVariable("prestationName", meet.getPrestation().getName());
            context.setVariable("meetDate", meet.getDateStart().format(dateFormatter));
            context.setVariable("meetTime", meet.getDateStart().format(timeFormatter));
            context.setVariable("contactType", meet.getClient().getContactType());
            context.setVariable("contactValue", meet.getClient().getContactValue());

            String htmlContent = templateEngine.process("emails/meet-cancellation-admin", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("contact@yona-nails.com");
            helper.setTo(adminEmail);
            helper.setSubject("⚠️ Un rendez-vous a été annulé (" + meet.getClient().getFullName() + ")");
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Erreur e-mail admin (annulation) : " + e.getMessage());
        }
    }
}