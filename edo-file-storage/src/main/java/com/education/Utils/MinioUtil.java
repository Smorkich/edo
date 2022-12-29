package com.education.Utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MinioUtil {

    @Getter
    @Value("${minio.sourcefolder}")
    private String forUploadFolder;

    @Getter
    @Value("${minio.targetfolder}")
    private String forDownloadingFolder;
}
