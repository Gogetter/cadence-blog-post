package dev.etimbuk.workflows;

import com.uber.cadence.workflow.WorkflowMethod;
import dev.etimbuk.models.FileUploadInfo;

public interface FileWatcherClientWorkflow {
    @WorkflowMethod
    void processFile(FileUploadInfo workflowData);
}
