package dev.etimbuk.activities.upload;

import com.google.gson.Gson;
import dev.etimbuk.models.FileUploadInfo;
import dev.etimbuk.models.UploadResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static dev.etimbuk.models.FileWatcherConstants.fileUploadServiceUrl;

@Slf4j
public class FileUploadActivityImpl implements FileUploadActivity {
    @Override
    public UploadResponse uploadFile(final FileUploadInfo workflowData) {
        final Gson gson = new Gson();

        HttpClient client = HttpClient.newHttpClient();

        String jsonPayload = gson.toJson(workflowData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fileUploadServiceUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), UploadResponse.class);
            }
        } catch (IOException |InterruptedException e) {
            e.printStackTrace();
        }

        return UploadResponse.builder().build();
    }
}
