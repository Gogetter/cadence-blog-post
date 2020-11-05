package dev.etimbuk.workflows;

import com.uber.cadence.activity.ActivityOptions;
import dev.etimbuk.activities.notification.NotificationActivities;
import dev.etimbuk.activities.upload.FileUploadActivities;
import dev.etimbuk.models.FileWatcherConstants;
import dev.etimbuk.models.UploadResponse;
import dev.etimbuk.models.WorkflowData;
import lombok.extern.slf4j.Slf4j;

import static com.uber.cadence.workflow.Workflow.newActivityStub;
import static dev.etimbuk.models.FileWatcherConstants.fileUploadTask;
import static dev.etimbuk.models.FileWatcherConstants.notificationTask;

@Slf4j
public class FileWatcherClientWorkflowImpl implements FileWatcherClientWorkflow {
    private final FileUploadActivities fileUploadActivities;
    private final NotificationActivities notificationActivities;

    public FileWatcherClientWorkflowImpl() {
        this.fileUploadActivities = newActivityStub(FileUploadActivities.class,
                new ActivityOptions.Builder().setTaskList(fileUploadTask).build());
        this.notificationActivities = newActivityStub(NotificationActivities.class,
                new ActivityOptions.Builder().setTaskList(notificationTask).build());
    }

    @Override
    public void processFile(final WorkflowData workflowData) {
        UploadResponse uploadResponse;
        if (workflowData.getFilename() != null) {
            uploadResponse = fileUploadActivities.uploadFile(workflowData);
            notificationActivities.sendNotification(uploadResponse);
        }
    }
}
