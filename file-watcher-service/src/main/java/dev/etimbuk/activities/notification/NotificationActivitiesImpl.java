package dev.etimbuk.activities.notification;

import com.google.gson.Gson;
import dev.etimbuk.models.EmailNotificationData;
import dev.etimbuk.models.UploadResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static dev.etimbuk.models.FileWatcherConstants.fileUploadServiceUrl;
import static dev.etimbuk.models.FileWatcherConstants.notificationServiceUrl;

@Slf4j
public class NotificationActivitiesImpl implements NotificationActivities {
    @Override
    public void sendNotification(final UploadResponse uploadResponse) {
        final Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();

        EmailNotificationData emailNotificationData = EmailNotificationData.builder()
                .fileLocation(uploadResponse.getFileLocation())
                .recipient("abc@test.com")
                .sender("noreply@uploader.com")
                .subject("Notification workflow activity successful")
                .build();

        String jsonPayload = gson.toJson(emailNotificationData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(notificationServiceUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Map<String, Object> responseData = gson.fromJson(response.body(), Map.class);
                log.info("{}", responseData);
            }
        } catch (IOException |InterruptedException e) {
            e.printStackTrace();
        }

    }
}
