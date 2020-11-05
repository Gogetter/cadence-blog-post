package dev.etimbuk.activities.upload;

import com.uber.cadence.activity.ActivityMethod;
import dev.etimbuk.models.UploadResponse;
import dev.etimbuk.models.WorkflowData;

import java.nio.file.Path;

public interface FileUploadActivities {
    @ActivityMethod(scheduleToCloseTimeoutSeconds = 100)
    UploadResponse uploadFile(WorkflowData workflowData);
}
