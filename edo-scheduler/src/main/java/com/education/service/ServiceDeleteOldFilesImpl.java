package com.education.service;

import com.education.feign.AppealFeignClient;
import com.education.feign.MinioFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.dto.AppealDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Collection;

/**
 * Class for deleting files whose appeal are older than a certain period
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceDeleteOldFilesImpl implements ServiceDeleteOldFiles {

    private final AppealFeignClient appealFeignClient;

    private final MinioFeignClient minioFeignClient;

    /**
     * Set file lifetime in years
     */
    @Value("${file-lifetime}")
    private int period;

    /**
     * At specified intervals, all appeals will be checked,
     * and if they are older than a certain period, the files associated with them will be deleted
     * appeals_check_interval - frequency of appeals checking (currently the check is set to once a day)
     */
    @Override
    @Scheduled(fixedDelayString = "${appeals_check_interval}")
    public void deleteOldFiles() {
        Collection<AppealDto> appealDtoCollection = appealFeignClient.findAll().getBody();
        log.info("The deleteOldFiles method has started, it starts every day");
        if (appealDtoCollection != null) {
            appealDtoCollection.forEach(appealDto -> {
                //If appeal older than certain period
                if (Period.between(appealDto.getCreationDate().toLocalDate(), LocalDate.from(ZonedDateTime.now()))
                        .getYears() > period) {
                    log.info("Delete storage of appeal id: {} by task ServiceDeleteOldFilesImpl", appealDto.getId());
                    // Receive all files associated with this appeal and delete them
                    appealDto.getFile().forEach(filePoolDto -> {
                        minioFeignClient.delete(filePoolDto.getStorageFileId().toString());
                        log.info("File: {} has been deleted", filePoolDto.getName());
                    });
                }
            });
        }
    }
}
