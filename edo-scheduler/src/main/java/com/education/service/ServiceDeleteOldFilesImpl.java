package com.education.service;

import com.education.feign.FilePoolFeignClient;
import com.education.feign.MinioFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Class for deleting files whose are older than a certain period
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceDeleteOldFilesImpl implements ServiceDeleteOldFiles {

    private final FilePoolFeignClient filePoolFeignClient;

    private final MinioFeignClient minioFeignClient;

    /**
     * Set file lifetime in years
     */
    @Value("${file-lifetime}")
    private int filePeriod;

    /**
     * At specified intervals, all files will be checked,
     * and if they are older than a certain period, they will be deleted
     * files_check_interval - frequency of files checking (currently the check is set to once a day)
     */
    @Override
    @Scheduled(fixedDelayString = "${files_check_interval}")
    public void deleteOldFiles() {
        log.info("The deleteOldFiles method has started, it starts every day");
        filePoolFeignClient.findAllOldFiles(filePeriod).forEach(storageFileId -> {
            minioFeignClient.delete(storageFileId.toString());
            log.info("File: {} has been deleted", storageFileId);
        });
    }
}
