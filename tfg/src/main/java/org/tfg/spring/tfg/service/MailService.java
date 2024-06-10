package org.tfg.spring.tfg.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.tfg.spring.tfg.domain.Usuario;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    private static final String USER = "usuario";

    @Value("${spring.mail.username}")
    private String mailFrom; 

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(String to, String subject, String content, boolean isHtml) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(mailFrom);
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
            System.out.println("ERROR AL ENVIAR EL MAIL");
        }
    }

    @Async
    public void sendEmailFromTemplate(Usuario usuario, String templateName, String subject) {
        if (usuario.getMail() == null) {
            return;
        }

        Context context = new Context();
        context.setVariable(USER, usuario);
        String content = templateEngine.process(templateName, context);
        sendEmail(usuario.getMail(), subject, content, true);
    }

    @Async
    public void sendActivationEmail(Usuario usuario) {
        sendEmailFromTemplate(usuario, "mail/mailRegistro", "IBERTRADE: Bienvenido a la familia");
    }

}