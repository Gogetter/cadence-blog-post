package dev.etimbuk.activities.notification;

import com.uber.cadence.activity.ActivityMethod;
import dev.etimbuk.models.UploadResponse;

public interface NotificationActivity {
    @ActivityMethod(scheduleToCloseTimeoutSeconds = 100)
    void sendNotification(UploadResponse uploadResponse);
}
