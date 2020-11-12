package dev.etimbuk;

import dev.etimbuk.models.FileUploadInfo;
import dev.etimbuk.workers.FileWatcherWorker;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Optional;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Slf4j
public class FileWatcherClient {
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

                if (filename.toFile().isFile()) {
                    FileWatcherWorker.executeWorkflow(buildFileUploadInfo(filename));
                }
            }

            key.reset();
        }
    }

    private static FileUploadInfo buildFileUploadInfo(final Path filename) {
        return FileUploadInfo.builder()
                .filename(filename.toFile().getName())
                .absoluteFilePath(filename.toAbsolutePath().toString())
                .fileExtension(getFileExtension(filename.toFile().getPath()).orElse(""))
                .build();
    }

    public static Optional<String> getFileExtension(final String filename) {
        return Optional.ofNullable(filename)
                .filter(file -> file.contains("."))
                .map(file -> file.substring(filename.lastIndexOf(".") + 1));
    }
}
