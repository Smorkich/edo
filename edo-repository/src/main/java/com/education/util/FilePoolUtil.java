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
                .creator(toDto2(new Employee()))
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

    /**
     * Конвертация из Employee в Dto (Для полей FilePoolDto, содержащих EmployeeDto)
     */
    public static EmployeeDto toDto2(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .middleName(employee.getMiddleName())
                .address(employee.getAddress())
                .fioDative(employee.getFioDative())
                .fioNominative(employee.getFioNominative())
                .fioGenitive(employee.getFioGenitive())
                .externalId(employee.getExternalId())
                .phone(employee.getPhone())
                .workPhone(employee.getWorkPhone())
                .birthDate(employee.getBirthDate())
                .username(employee.getUsername())
                .creationDate(employee.getCreationDate())
                .archivedDate(employee.getArchivedDate())
                .build();
    }

    /**
     * Конвертация Collection<Employee> в Dto (Для полей FilePoolDto, содержащих Collection<EmployeeDto>)
     */
    public Collection<EmployeeDto> toDto(Collection<Employee> employees) {
        return employees.stream()
                .map(FilePoolUtil::toDto2)
                .toList();
    }

    /**
     * Конвертация Collection<EmployeeDto> в Collection<Employee>
     */
    public Collection<Employee> dtoEmployeeToEntity(Collection<EmployeeDto> employees) {
        return employees.stream()
                .map(this::toEntity)
                .toList();
    }

    /**
     * Конвертация EmployeeDto в Employee, содержащий id
     * Id позволит назначать Employee из тех, что уже существуют
     */
    EmployeeServise employeeService;

    public Employee toEntity(EmployeeDto employeeDto) {
        return employeeService.findById(employeeDto.getId());
    }

}
