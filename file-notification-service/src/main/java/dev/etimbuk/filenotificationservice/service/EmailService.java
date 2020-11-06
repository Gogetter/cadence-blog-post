package dev.etimbuk.filenotificationservice.service;

import dev.etimbuk.filenotificationservice.models.email.EmailData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(final String from, final EmailData emailData) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

        mimeMessageHelper.setTo(emailData.getRecipient());
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setSubject(emailData.getSubject());
        mimeMessageHelper.setText(emailData.getMessage());

        mailSender.send(message);
    }
}
