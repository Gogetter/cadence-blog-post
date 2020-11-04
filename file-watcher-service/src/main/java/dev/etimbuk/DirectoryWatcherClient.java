package dev.etimbuk;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

public class DirectoryWatcherClient {
    public static void main(String[] args) throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();

        Path path = Paths.get(System.getProperty("user.dir"));
        System.out.println(path.toAbsolutePath().toString());

        path.register(watchService, ENTRY_CREATE);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                Path filename = (Path) event.context();

                System.out.println(new String(Files.readAllBytes(filename)));;

                System.out.println("Event kind:" + event.kind() + ". File affected: " + filename.getFileName().toString() + ".");
            }

            key.reset();
        }
    }
}
