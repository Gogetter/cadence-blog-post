package dev.etimbuk;

import dev.etimbuk.models.WorkflowData;
import dev.etimbuk.workers.FileWatcherWorker;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Optional;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Slf4j
public class DirectoryWatcherClient {
    public static void main(String[] args) throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();

        Path path = Paths.get(System.getProperty("user.dir"));

        log.info("Absolute path for directory being monitored {}", path.toAbsolutePath().toString());

        path.register(watchService, ENTRY_CREATE);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                Path filename = (Path) event.context();

                log.info("Event kind: {}. File affected:, {}.", event.kind(), filename.getFileName().toString());

                FileWatcherWorker.executeWorkflow(buildWorkflowData(filename));
            }

            key.reset();
        }
    }

    private static WorkflowData buildWorkflowData(final Path filename) throws IOException {
        return WorkflowData.builder()
                .filename(filename.toFile().getName())
                .fileContents(Files.readAllBytes(filename))
                .fileExtension(getFileExtension(filename.toFile().getPath()).orElse(""))
                .build();
    }

    public static Optional<String> getFileExtension(final String filename) {
        return Optional.ofNullable(filename)
                .filter(file -> file.contains("."))
                .map(file -> file.substring(filename.lastIndexOf(".") + 1));
    }
}
