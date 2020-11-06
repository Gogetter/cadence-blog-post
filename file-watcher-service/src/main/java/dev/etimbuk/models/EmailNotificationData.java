package dev.etimbuk.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailNotificationData {
    private String subject;
    private String recipient;
    private String sender;
    private String fileLocation;
}
