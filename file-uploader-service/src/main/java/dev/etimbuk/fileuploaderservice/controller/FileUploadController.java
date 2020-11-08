package dev.etimbuk.fileuploaderservice.controller;

import dev.etimbuk.fileuploaderservice.models.FileUploadInfo;
import dev.etimbuk.fileuploaderservice.models.UploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/file/")
public class FileUploadController {
    @PostMapping("upload")
    public ResponseEntity<UploadResponse> uploadFile(@RequestBody FileUploadInfo fileUploadInfo) {
        log.info("File {} uploaded for processing and uploading", fileUploadInfo.getFilename());
        return ResponseEntity.ok(UploadResponse.builder()
                .fileName(fileUploadInfo.getFilename())
                .fileLocation(fileUploadInfo.getAbsoluteFilePath())
                .contentType(fileUploadInfo.getFileExtension())
                .uploaded(true)
                .build());
    }
}
