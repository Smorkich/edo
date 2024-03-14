package com.education.service.filePool.impl;

import com.education.feign.FilePoolFeignClient;
import com.education.service.filePool.FilePoolService;
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
        return filePoolFeignClient.save(filePoolDto);
    }

    /**
     * Сохраняет коллекцию FilePoolDto, которая передаётся в параметр - отправляет запрос в контроллер edo-repository
     *
     * @param filePoolDtos коллекция добавляемых FilePoolDto
     * @return Collection<FilePoolDto> - коллекция DTO сущности FilePool (информация о файлах)
     */
    @Override
    public Collection<FilePoolDto> saveAll(Collection<FilePoolDto> filePoolDtos) {
        return filePoolFeignClient.saveAll(filePoolDtos);
    }

    @Override
    public void delete(Long id) {
        filePoolFeignClient.delete(id);
    }

    @Override
    public FilePoolDto findById(Long id) {
        return filePoolFeignClient.findById(id);
    }

    @Override
    public FilePoolDto findByUuid(UUID uuid) {
        return filePoolFeignClient.findByUuid(uuid);
    }

    @Override
    public Collection<FilePoolDto> findAll() {
        return filePoolFeignClient.findAll();
    }


    @Override
    public void moveToArchive(Long id) {
        filePoolFeignClient.moveToArchive(id);
    }

    @Override
    public FilePoolDto findByIdNotArchived(Long id) {
        return filePoolFeignClient.getFileNotArchived(id);
    }

    @Override
    public Collection<FilePoolDto> findAllByIdNotArchived(String ids) {
        return filePoolFeignClient.getFilesNotArchived(ids);
    }

    @Override
    public Collection<UUID> findAllOldFiles(int filePeriod) {
        return filePoolFeignClient.getOldFilesForDelete(filePeriod);
    }

}
