package dev.etimbuk.activities.upload;

import com.uber.cadence.activity.ActivityMethod;
import dev.etimbuk.models.UploadResponse;
import dev.etimbuk.models.FileUploadInfo;

public interface FileUploadActivities {
    @ActivityMethod(scheduleToCloseTimeoutSeconds = 100)
    UploadResponse uploadFile(FileUploadInfo workflowData);
}
