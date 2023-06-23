package com.education.service.filePool.impl;

import com.education.service.filePool.FilePoolService;
import com.education.feign.FilePoolFeignClient;
import lombok.AllArgsConstructor;
import model.dto.FilePoolDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Nadezhda Pupina
 * Сервис реализует методы jpa repository и обычные методы
 */
@Service
@AllArgsConstructor
public class FilePoolServiceImpl implements FilePoolService {

    private final FilePoolFeignClient filePoolFeignClient;

    @Override
    public FilePoolDto save(FilePoolDto filePoolDto) {
        return filePoolFeignClient.save(filePoolDto).getBody();
    }

    @Override
    public void delete(Long id) {
        filePoolFeignClient.delete(id);
    }

    @Override
    public FilePoolDto findById(Long id) {
        return filePoolFeignClient.findById(id).getBody();
    }

    @Override
    public FilePoolDto findByUuid(UUID uuid) {
        return filePoolFeignClient.findByUuid(uuid).getBody();
    }

    @Override
    public Collection<FilePoolDto> findAll() {
        return filePoolFeignClient.findAll().getBody();
    }


    @Override
    public void moveToArchive(Long id) {
        filePoolFeignClient.moveToArchive(id);
    }

    @Override
    public FilePoolDto findByIdNotArchived(Long id) {
        return filePoolFeignClient.getFileNotArchived(id).getBody();
    }

    @Override
    public Collection<FilePoolDto> findAllByIdNotArchived(String ids) {
        return filePoolFeignClient.getFilesNotArchived(ids).getBody();
    }

}
