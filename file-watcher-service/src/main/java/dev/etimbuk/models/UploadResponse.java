package dev.etimbuk.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

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
