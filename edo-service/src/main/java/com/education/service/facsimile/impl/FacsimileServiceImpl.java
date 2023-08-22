package com.education.service.facsimile.impl;

import com.education.feign.FacsimileFeignClient;
import com.education.service.facsimile.FacsimileService;
import com.education.service.minio.MinioService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FacsimileDto;
import org.apache.logging.log4j.Level;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
@Log4j2
@AllArgsConstructor
public class FacsimileServiceImpl implements FacsimileService {

    private final FacsimileFeignClient facsimileFeignClient;
    private final MinioService minioService;

    @Override
    public FacsimileDto save(FacsimileDto facsimileDto) {
        return facsimileFeignClient.save(facsimileDto);
    }

    @Override
    public FacsimileDto findFacsimileByEmployeeId(Long id) {
        log.info("Отправляем запрос на получение Facsimile по EmployeeId в edo-repository");
        return facsimileFeignClient.findFacsimileByEmployeeId(id);
    }

    @Override
    public Resource getFacsimile(FacsimileDto facsimile) {

        var storageFileId = String.valueOf(facsimile.getFilePool().getStorageFileId());

        try {
            return minioService.downloadOneFile(storageFileId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get facsimile from MiniO Container " + e);
        }
    }

}
