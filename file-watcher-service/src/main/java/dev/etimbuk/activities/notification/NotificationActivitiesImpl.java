package dev.etimbuk.activities.notification;

import dev.etimbuk.models.UploadResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationActivitiesImpl implements NotificationActivities {
    @Override
    public void sendNotification(final UploadResponse uploadResponse) {
        log.info("Notification sent for {} uploaded", uploadResponse.getFileName());
    }
}
