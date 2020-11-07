package dev.etimbuk.workflows;

import dev.etimbuk.activities.notification.NotificationActivity;
import dev.etimbuk.activities.upload.FileUploadActivity;
import dev.etimbuk.models.UploadResponse;
import dev.etimbuk.models.FileUploadInfo;
import lombok.extern.slf4j.Slf4j;

import static com.uber.cadence.workflow.Workflow.newActivityStub;

@Slf4j
public class FileWatcherClientWorkflowImpl implements FileWatcherClientWorkflow {
    private final FileUploadActivity fileUploadActivity;
    private final NotificationActivity notificationActivity;

    public FileWatcherClientWorkflowImpl() {
        this.fileUploadActivity = newActivityStub(FileUploadActivity.class);
        this.notificationActivity = newActivityStub(NotificationActivity.class);
    }

    @Override
    public void processFile(final FileUploadInfo workflowData) {
        UploadResponse uploadResponse;
        if (workflowData.getFilename() != null) {
            uploadResponse = fileUploadActivity.uploadFile(workflowData);
            notificationActivity.sendNotification(uploadResponse);
        }
    }
}
