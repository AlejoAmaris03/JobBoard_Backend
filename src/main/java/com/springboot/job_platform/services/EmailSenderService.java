package com.springboot.job_platform.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.springboot.job_platform.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service

public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendWelcomeEmail(User user, String subject) throws MessagingException, IOException  {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            Path templatePath = Paths.get("config/email-templates/welcome-email-template.html");
            String temporaryPassword = user.getName().toLowerCase() + "_" + user.getSurname().toLowerCase() + "_" + LocalDate.now().getYear();

            message.setFrom("pixel.tornado.pt@gmail.com");
            message.setRecipients(MimeMessage.RecipientType.TO, user.getEmail());
            message.setSubject(subject);

            String htmlTemplate = readFile(templatePath)
                .replace("{{userName}}", user.getName())
                .replace("{{userPassword}}", temporaryPassword);

            message.setContent(htmlTemplate, "text/html; charset=utf-8");

            mailSender.send(message);
        } 
        catch (MessagingException  e) {
            throw new MessagingException("Error sending email: " + e.getMessage());
        }
    }

    @Async
    public void sendEmailToRestorePassword(User user, String temporaryPassword, String subject) throws MessagingException, IOException  {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            Path templatePath = Paths.get("config/email-templates/restore-password.html");

            message.setFrom("pixel.tornado.pt@gmail.com");
            message.setRecipients(MimeMessage.RecipientType.TO, user.getEmail());
            message.setSubject(subject);

            String htmlTemplate = readFile(templatePath)
                .replace("{{name}}", user.getName())
                .replace("{{newPassword}}", temporaryPassword)
                .replace("{{loginUrl}}", "http://localhost:4200/login");

            message.setContent(htmlTemplate, "text/html; charset=utf-8");

            mailSender.send(message);
        } 
        catch (MessagingException  e) {
            throw new MessagingException("Error sending email: " + e.getMessage());
        }
    }

    private String readFile(Path path) throws IOException {
        try {
            return Files.readString(path, StandardCharsets.UTF_8);   
        } 
        catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
