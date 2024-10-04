package com.ingress2.userms.kafka;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendRegistrationEmail(String email, String subject, String body) {
        log.info("Preparing to send email to: {}", email);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("taxiapp.notifications@gmail.com");
            message.setTo(email);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);

            log.info("Email sent successfully to {}", email);
        } catch (Exception e) {
            log.error("Failed to send email to {}. Error: {}", email, e.getMessage());
        }
    }
}

