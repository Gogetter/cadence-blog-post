package dev.etimbuk.fileuploaderservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadInfo {
    private String filename;
    private String absoluteFilePath;
    private String fileExtension;
}
