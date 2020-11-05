package dev.etimbuk.activities.upload;

import dev.etimbuk.models.UploadResponse;
import dev.etimbuk.models.WorkflowData;

public class FileUploadActivitiesImpl implements FileUploadActivities {
    @Override
    public UploadResponse uploadFile(final WorkflowData workflowData) {
        return UploadResponse.builder().build();
    }
}
