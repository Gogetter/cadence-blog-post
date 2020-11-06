package dev.etimbuk.models;

import lombok.Data;

@Data
public class EmailNotificationData {
    private String subject;
    private String recipient;
    private String sender;
    private String fileLocation;
}
