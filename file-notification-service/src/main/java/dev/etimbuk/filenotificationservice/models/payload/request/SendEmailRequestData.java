package dev.etimbuk.filenotificationservice.models.payload.request;

import lombok.Data;

@Data
public class SendEmailRequestData {
    private String subject;
    private String recipient;
    private String sender;
    private String fileLocation;
}
