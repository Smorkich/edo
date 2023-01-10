package com.education.util;

import com.education.entity.Employee;
import com.education.entity.FilePool;
import com.education.service.filePoll.EmployeeServise;
import jakarta.validation.constraints.NotNull;
import model.dto.EmployeeDto;
import model.dto.FilePoolDto;

import java.util.Collection;

/**
 * Util класс для реализации вспомогательных методов
 */
public class FilePoolUtil {
    /**
     * Конвертация сущности FilePool в FilePoolDto
     */
    public static FilePoolDto toDto(FilePool filePool) {
        return FilePoolDto.builder()
                .id(filePool.getId())
                .storageFileId(filePool.getStorageFileId())
                .name(filePool.getName())
                .extension(filePool.getExtension())
                .size(filePool.getSize())
                .pageCount(filePool.getPageCount())
                .uploadDate(filePool.getUploadDate())
                .archivedDate(filePool.getArchivedDate())
                .creator(new EmployeeDto())
                .build();
    }

    /**
     * Конвертация FilePoolDto в сущность FilePool
     */
    public static FilePool toFilePool(FilePoolDto filePoolDto) {
        return FilePool.builderfilePool()
                .storageFileId(filePoolDto.getStorageFileId())
                .name(filePoolDto.getName())
                .extension(filePoolDto.getExtension())
                .size(filePoolDto.getSize())
                .pageCount(filePoolDto.getPageCount())
                .uploadDate(filePoolDto.getUploadDate())
                .archivedDate(filePoolDto.getArchivedDate())
                .creator(new Employee())
                .build();
    }

    /**
     * Конвертация коллекции <FilePool> в коллекцию <FilePoolDto>
     */
    public static Collection<FilePoolDto> listFilePooDto(Collection<FilePool> filePools) {
        return filePools.stream()
                .map(FilePoolUtil::toDto)
                .toList();
    }

}
