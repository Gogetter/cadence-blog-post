package dev.etimbuk.workers;

import com.google.gson.GsonBuilder;
import com.uber.cadence.WorkflowExecution;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.client.WorkflowOptions;
import com.uber.cadence.converter.DataConverter;
import com.uber.cadence.converter.JsonDataConverter;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerOptions;
import dev.etimbuk.activities.notification.NotificationActivitiesImpl;
import dev.etimbuk.activities.upload.FileUploadActivities;
import dev.etimbuk.activities.upload.FileUploadActivitiesImpl;
import dev.etimbuk.models.WorkflowData;
import dev.etimbuk.workflows.FileWatcherClientWorkflow;
import dev.etimbuk.workflows.FileWatcherClientWorkflowImpl;
import lombok.extern.slf4j.Slf4j;

import static dev.etimbuk.models.FileWatcherConstants.fileUploadTask;
import static dev.etimbuk.models.FileWatcherConstants.workflowDomain;
import static java.time.Duration.ofMinutes;

@Slf4j
public class FileWatcherWorker {
    public static void executeWorkflow(final WorkflowData workflowData) {
        startWorkflowWorker();

        WorkflowClient workflowClient = WorkflowClient.newInstance(workflowDomain,
                new WorkflowClientOptions.Builder().setDataConverter(getDataConverter()).build());

        WorkflowOptions workflowOptions = new WorkflowOptions.Builder()
                .setTaskList(fileUploadTask)
                .setExecutionStartToCloseTimeout(ofMinutes(3))
                .build();
        FileWatcherClientWorkflow workflow = workflowClient.newWorkflowStub(FileWatcherClientWorkflow.class,
                workflowOptions);

        WorkflowExecution workflowExecution = WorkflowClient.start(workflow::processFile, workflowData);

        log.info(String.format("Started process file workflow with workflowId %s and runId %s",
                workflowExecution.getWorkflowId(), workflowExecution.getRunId()));
    }

    private static void startWorkflowWorker() {
        Worker.Factory workerFactory = new Worker.Factory(workflowDomain);
        WorkerOptions workerOptions = new WorkerOptions.Builder().setDataConverter(getDataConverter()).build();

        Worker worker = workerFactory.newWorker(fileUploadTask, workerOptions);

        //register workflow
        worker.registerWorkflowImplementationTypes(FileWatcherClientWorkflowImpl.class);

        //register workflow activities
        worker.registerActivitiesImplementations(new FileUploadActivitiesImpl(), new NotificationActivitiesImpl());
        workerFactory.start();
    }

    private static DataConverter getDataConverter() {
        return new JsonDataConverter(GsonBuilder::enableComplexMapKeySerialization);
    }
}
