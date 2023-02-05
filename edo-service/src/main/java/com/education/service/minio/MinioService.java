package com.education.service.minio;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Anna Artemyeva
 * Интерфейс с методами для Minio
 */
public interface MinioService {
    /**
     * uploadOneFile - загружает входящий файл в файловое хранилище
     * @param file
     */
    void uploadOneFile(MultipartFile file);
}
