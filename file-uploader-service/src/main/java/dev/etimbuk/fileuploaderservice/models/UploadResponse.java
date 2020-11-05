package dev.etimbuk.fileuploaderservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class UploadResponse {
    private boolean uploaded;
    private String fileName;
    private String contentType;
}
