package dev.etimbuk.fileuploaderservice.controller;

import dev.etimbuk.fileuploaderservice.models.UploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/file/")
public class FileUploadController {
    @PostMapping("upload")
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("File {} uploaded", file.getOriginalFilename());
        return ResponseEntity.ok(UploadResponse.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .uploaded(true)
                .build());
    }
}
