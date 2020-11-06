package dev.etimbuk.models;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class FileWatcherConstants {
    public static final String workflowDomain = "file-watcher";
    public static final String fileUploadTask = "file-processing-task-list";

    public static final String fileUploadServiceUrl = "http://localhost:8099/api/file/upload";
    public static final String notificationServiceUrl = "http://localhost:8098/api/notification/email";
}
