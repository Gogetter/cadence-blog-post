package dev.etimbuk.workflows;

import dev.etimbuk.activities.notification.NotificationActivities;
import dev.etimbuk.activities.upload.FileUploadActivities;
import dev.etimbuk.models.UploadResponse;
import dev.etimbuk.models.FileUploadInfo;
import lombok.extern.slf4j.Slf4j;

import static com.uber.cadence.workflow.Workflow.newActivityStub;

@Slf4j
public class FileWatcherClientWorkflowImpl implements FileWatcherClientWorkflow {
    private final FileUploadActivities fileUploadActivities;
    private final NotificationActivities notificationActivities;

    public FileWatcherClientWorkflowImpl() {
        this.fileUploadActivities = newActivityStub(FileUploadActivities.class);
        this.notificationActivities = newActivityStub(NotificationActivities.class);
    }

    @Override
    public void processFile(final FileUploadInfo workflowData) {
        UploadResponse uploadResponse;
        if (workflowData.getFilename() != null) {
            uploadResponse = fileUploadActivities.uploadFile(workflowData);
            notificationActivities.sendNotification(uploadResponse);
        }
    }
}
