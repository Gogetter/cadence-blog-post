package dev.etimbuk.workflows;

import com.uber.cadence.workflow.WorkflowMethod;
import dev.etimbuk.models.WorkflowData;

public interface FileWatcherClientWorkflow {
    @WorkflowMethod
    void processFile(WorkflowData workflowData);
}
