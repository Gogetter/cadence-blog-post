package dev.etimbuk.filenotificationservice.controllers;

import dev.etimbuk.filenotificationservice.models.email.EmailData;
import dev.etimbuk.filenotificationservice.models.payload.request.SendEmailRequestData;
import dev.etimbuk.filenotificationservice.models.payload.response.EmailSendResponse;
import dev.etimbuk.filenotificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/notification/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationController {

    private final EmailService emailService;

    @PostMapping("email")
    public ResponseEntity<EmailSendResponse> sendEmail(@RequestBody SendEmailRequestData sendEmailRequestData) {

        try {
            emailService.sendEmail(sendEmailRequestData.getSender(), EmailData.from(sendEmailRequestData));

            return ResponseEntity.status(OK).body(EmailSendResponse.builder()
                    .success(true)
                    .message("Email sent successfully.")
                    .build());

        } catch (Exception exc) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(EmailSendResponse.builder()
                    .success(false)
                    .message(String.format("Failed to send email due to %s", exc.getMessage()))
                    .build());
        }
    }
}
