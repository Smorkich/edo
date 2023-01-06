package com.education.Utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Util class to get target and source folder`s directions */
@Component
public class MinioUtil {

    /** Folder for uploading file from it to MINIO-server */
    @Getter
    @Value("${minio.sourcefolder}")
    private String forUploadFolder;

    /** Folder for downloading file from MINIO-server  */
    @Getter
    @Value("${minio.targetfolder}")
    private String forDownloadingFolder;
}
