package com.linsi_backend.linsi_backend.service.impl;

import com.linsi_backend.linsi_backend.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    // Correo fijo
    private static final String FIXED_TO_EMAIL = "correo_fijo@ejemplo.com";

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void registration(String subject, String firstName, String lastName, String id) {
        try {
            Context context = new Context();

            context.setVariable("firstName", firstName);
            context.setVariable("lastName", lastName);

            String contentHTML = templateEngine.process("registration", context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

            helper.setTo("franco_kral@hotmail.com");
            helper.setSubject(subject);
            helper.setText(contentHTML, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al mandar mail: " + e.getMessage(), e);
        }
    }
}

