package dev.etimbuk.filenotificationservice.models.email;

import dev.etimbuk.filenotificationservice.models.payload.request.SendEmailRequestData;
import lombok.Builder;
import lombok.Value;

import static java.lang.String.format;

@Value
@Builder(toBuilder = true)
public class EmailData {
    String recipient;
    String subject;
    String message;

    public static EmailData from(final SendEmailRequestData request) {
        return EmailData.builder()
                .message(format("File from '%s' successfully uploaded", request.getFileLocation()))
                .subject(request.getSubject())
                .recipient(request.getRecipient())
                .build();
    }
}
