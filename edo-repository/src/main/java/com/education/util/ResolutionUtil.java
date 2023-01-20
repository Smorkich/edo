//package com.education.util;
//
//import com.education.entity.Employee;
//import com.education.entity.Resolution;
//import model.dto.EmployeeDto;
//import model.dto.ResolutionDto;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * Класс конвертации Resolution в ResolutionDTO и обратно
// */
//public class ResolutionUtil {
//
//    /**
//     * Метод конвертации Employee в EmployeeDto
//     */
//    private static EmployeeDto toEmployeeDto(Employee employee) {
//        return new EmployeeDto(
//                employee.getId(),
//                employee.getFirstName(),
//                employee.getLastName(),
//                employee.getMiddleName(),
//                employee.getAddress(),
//                employee.getFioDative(),
//                employee.getFioNominative(),
//                employee.getFioGenitive(),
//                employee.getExternalId(),
//                employee.getPhone(),
//                employee.getWorkPhone(),
//                employee.getBirthDate(),
//                employee.getUsername(),
//                employee.getCreationDate(),
//                employee.getArchivedDate()
//        );
//    }
//
//    /**
//     * Конвертации Dto в Employee
//     */
//    private static Employee toEmployee(EmployeeDto employeeDto) {
//        return new Employee(
//                employeeDto.getFirstName(),
//                employeeDto.getLastName(),
//                employeeDto.getMiddleName(),
//                employeeDto.getAddress(),
//                employeeDto.getFioDative(),
//                employeeDto.getFioNominative(),
//                employeeDto.getFioGenitive(),
//                employeeDto.getExternalId(),
//                employeeDto.getPhone(),
//                employeeDto.getWorkPhone(),
//                employeeDto.getBirthDate(),
//                employeeDto.getUsername(),
//                employeeDto.getCreationDate(),
//                employeeDto.getArchivedDate()
//        );
//    }
//
//    /**
//     * Метод конвертации коллекции сущностей в Dto
//     */
//    private static List<EmployeeDto> toListEmployeeDto(List<Employee> employees) {
//        return employees.stream().
//                map(ResolutionUtil::toEmployeeDto)
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Метод конвертации коллекции из Dto в коллекцию сущностей
//     */
//    private static List<Employee> toListEmployee(List<EmployeeDto> employees) {
//        return employees.stream()
//                .map(ResolutionUtil::toEmployee)
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Метод для конвертации Резолюции в обертку Dto
//     */
//    public static ResolutionDto toResolutionDto(Resolution resolution) {
//        return new ResolutionDto(
//                resolution.getId(),
//                resolution.getCreationDate(),
//                resolution.getArchivedDate(),
//                resolution.getLastActionDate(),
//                resolution.getType(),
//                toEmployeeDto(resolution.getCreator()),
//                toEmployeeDto(resolution.getSigner()),
//                toListEmployeeDto(resolution.getExecutor()),
//                toEmployeeDto(resolution.getCurator()),
//                resolution.getSerialNumber()
//        );
//    }
//
//    /**
//     * Метод для конвертации Dto в Резолюцию
//     */
//    public static Resolution toResolution(ResolutionDto resolutionDto) {
//        return new Resolution(
//                resolutionDto.getCreationDate(),
//                resolutionDto.getArchivedDate(),
//                resolutionDto.getLastActionDate(),
//                resolutionDto.getType(),
//                toEmployee(resolutionDto.getCreator()),
//                toEmployee(resolutionDto.getSigner()),
//                toListEmployee(resolutionDto.getExecutor()),
//                toEmployee(resolutionDto.getCurator()),
//                resolutionDto.getSerialNumber()
//        );
//    }
//
//    public static List<Resolution> toListResolutions(Collection<ResolutionDto> resolutionDtos) {
//        return resolutionDtos.stream()
//                .map(ResolutionUtil::toResolution)
//                .collect(Collectors.toList());
//    }
//
//    public static List<ResolutionDto> toListResolutionsDto(Collection<Resolution> resolutions) {
//        return resolutions.stream()
//                .map(ResolutionUtil::toResolutionDto)
//                .collect(Collectors.toList());
//    }
//
//}
